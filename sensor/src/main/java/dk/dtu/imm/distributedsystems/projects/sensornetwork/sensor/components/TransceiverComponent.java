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
import dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.sender.UdpPortSender;


public class TransceiverComponent extends AbstractTwoChannelTransceiver {

	private Sensor sensor;
			
	public TransceiverComponent(String nodeId, int leftPortNumber, int rightPortNumber,
			int senderPortNumber, Channel[] leftChannels,
			Channel[] rightChannels, int ackTimeout) {
		super(null, null, new UdpPortSender(nodeId, senderPortNumber,
				new LinkedList<Packet>(), leftChannels, rightChannels,
				ackTimeout));

		// manually set listeners
		this.getAllListeners()[0] = new LeftUdpPortListener(nodeId, this,
				leftPortNumber);
		this.getAllListeners()[1] = new RightUdpPortListener(nodeId, this,
				rightPortNumber);
	}
	
	@Override
	public synchronized void handlePacket(Packet packet) {
		
		logger.debug("Handling " + packet);
		
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
			logger.debug("Adding " + packet + " to sender buffer");
			this.getPortSender().addToBuffer(packet);
			
//			if (packet.getType() == PacketType.DAT) {
//				// TODO Log generating DAT packet
//			} else if (packet.getType() == PacketType.ALM) {
//				// TODO Log generating ALM packet
//			} else {
//				System.err.println("Wrong type of Sensor Data Package Received");
//			}
		} else if (packet.getGroup() == PacketGroup.ACKNOWLEDGEMENT) {
			logger.debug("Passing ACK to sender");
			((AbstractUdpPortSender) this.getPortSender()).passAck();
		} else {
			System.err.println("Wrong type group of Packet Received");
		}
	}
}
