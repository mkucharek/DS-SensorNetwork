package dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.components;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.Packet;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.PacketGroup;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.PacketType;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.Sensor;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.listener.LeftUdpPortListener;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.listener.RightUdpPortListener;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.sender.Sender;


public class TransceiverComponent implements Transceiver {

	private Sensor sensor;
	
	private LeftUdpPortListener leftListner;
	private RightUdpPortListener rightListner;
	
	private Sender sender;
	
	int ackTimeout;
			
	public TransceiverComponent(Sensor sensor) {
		this.sensor = sensor;
		this.ackTimeout = sensor.getAckTimeout();
		
		this.leftListner = new LeftUdpPortListener(this, sensor.getLeftPort());
		this.leftListner.start();
		
		this.rightListner = new RightUdpPortListener(this, sensor.getRightPort());
		this.rightListner.start();
	}
	
	@Override
	public synchronized void handlePacket(Packet packet) {
		
		if (packet.getGroup() == PacketGroup.COMMAND) {
			if (packet.getType() == PacketType.PRD) {
				sensor.getSensorComponent().setPeriod(Integer.parseInt(packet.getValue()));
			} else if (packet.getType() == PacketType.THR) {
				sensor.getSensorComponent().setThreshold(Integer.parseInt(packet.getValue()));
			} else {
				System.err.println("Wrong type of Command Packet Received");
			}
		} else if (packet.getGroup() == PacketGroup.SENSOR_DATA) {
//			if (packetFromSensor.getType() == PacketType.DAT) {
			sender.addToBuffer(packet);
//			} else if (packetFromSensor.getType() == PacketType.ALM) {
//				sender.addToBuffer(p);
//			} else {
//				System.err.println("Wrong type of Sensor Data Package Received");
//			}
		} else if (packet.getGroup() == PacketGroup.ACKNOWLEDGEMENT) {
			sender.passAck();
		} else {
			System.err.println("Wrong type group of Packet Received");
		}
	}
}
