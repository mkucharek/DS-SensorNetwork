package dk.dtu.imm.distributedsystems.projects.sensornetwork.admin.components
;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.admin.Admin;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.admin.listener.AdminUdpPortListener;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.admin.sender.AdminUdpPortSender;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.channels.Channel;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.sender.udp.AbstractUdpPortSender;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.transceiver.AbstractOneChannelTransceiver;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.Packet;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.PacketGroup;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.PacketType;


public class TransceiverComponent extends AbstractOneChannelTransceiver {
	
	protected DatagramSocket rightSocket;
	
	protected Admin admin;
			
	public TransceiverComponent(String nodeId, int rightPortNumber,
			Channel[] rightChannels, int ackTimeout) {
		super(nodeId, null, null);

		try {
			this.rightSocket = new DatagramSocket(rightPortNumber);
		} catch (SocketException e) {
			throw new RuntimeException(e);
		}
		
		// manually set listeners
		this.getAllListeners()[0] = new AdminUdpPortListener(nodeId, this,
				this.rightSocket, rightChannels);
		
		this.setSender(new AdminUdpPortSender(nodeId, this.rightSocket,
				new LinkedList<Packet>(), rightChannels,
				ackTimeout));
	}
	
	@Override
	public synchronized void handlePacket(Packet packet) {
		
		logger.debug("Handling " + packet);

		if (packet.getGroup().equals(PacketGroup.COMMAND)) {
			this.getPortSender().addToBuffer(packet);
			
		} else if (packet.getType().equals(PacketType.ALM)) {
			logger.info("Alarm Data received");
			
			// TODO Handle ALM packet - print it to user in UI
			
		} else if (packet.getGroup().equals(PacketGroup.QUERY)) {
			
			if (packet.getValue().equals("")) {
				
				// TODO Handle sending of QUERY
				
				this.getPortSender().addToBuffer(packet);
				
			} else {
				
				// TODO Handle responses to QUERY packets - print it to user in UI

				logger.info("Response to Query received"); 
			
			}
						
		} else if (packet.getGroup().equals(PacketGroup.ACKNOWLEDGEMENT)) {
			logger.debug("Passing ACK to sender");
			
			((AbstractUdpPortSender) this.getPortSender()).passAck();
		} else {
			logger.info("Wrong type group of Packet Received");
		}
	}

	@Override
	public void close() {
		
		super.close();
		
		this.rightSocket.close();
		
	}

}
