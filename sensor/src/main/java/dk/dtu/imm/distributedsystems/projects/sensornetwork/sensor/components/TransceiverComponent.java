package dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.components;

import java.util.LinkedList;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.channels.Channel;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.sender.udp.AbstractUdpPortSender;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.transceiver.AbstractTwoChannelTransceiver;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.Packet;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.PacketGroup;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.PacketType;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.Sensor;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.listener.LeftUdpPortListener;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.listener.RightUdpPortListener;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.sender.SensorNodeUdpPortSender;


public class TransceiverComponent extends AbstractTwoChannelTransceiver {

	private Sensor sensor;
			
	public TransceiverComponent(int leftPortNumber, int rightPortNumber,
			int senderPortNumber, Channel[] leftChannels,
			Channel[] rightChannels, int ackTimeout) {
		super(null, null, new SensorNodeUdpPortSender(senderPortNumber,
				new LinkedList<Packet>(), leftChannels, rightChannels,
				ackTimeout));

		// manually set listeners
		this.getAllListeners()[0] = new LeftUdpPortListener(this,
				leftPortNumber);
		this.getAllListeners()[1] = new RightUdpPortListener(this,
				rightPortNumber);
	}
	
	@Override
	public synchronized void handlePacket(Packet packet) {
		
		if (packet.getGroup() == PacketGroup.COMMAND) {
			if (packet.getType() == PacketType.PRD) {
				sensor.getSensorComponent().setPeriod(Integer.parseInt(packet.getValue()));
				
				// TODO Log setting Period - PRD
			} else if (packet.getType() == PacketType.THR) {
				sensor.getSensorComponent().setThreshold(Integer.parseInt(packet.getValue()));
				
				// TODO Log setting Period - PRD
			} else {
				System.err.println("Wrong type of Command Packet Received");
			}
		} else if (packet.getGroup() == PacketGroup.SENSOR_DATA) {
			this.getPortSender().addToBuffer(packet);
			
//			if (packet.getType() == PacketType.DAT) {
//				// TODO Log generating DAT packet
//			} else if (packet.getType() == PacketType.ALM) {
//				// TODO Log generating ALM packet
//			} else {
//				System.err.println("Wrong type of Sensor Data Package Received");
//			}
		} else if (packet.getGroup() == PacketGroup.ACKNOWLEDGEMENT) {
			((AbstractUdpPortSender) this.getPortSender()).passAck();
		} else {
			System.err.println("Wrong type group of Packet Received");
		}
	}
}
