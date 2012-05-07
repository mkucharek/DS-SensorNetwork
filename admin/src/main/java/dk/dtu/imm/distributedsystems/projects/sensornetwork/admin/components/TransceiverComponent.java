package dk.dtu.imm.distributedsystems.projects.sensornetwork.admin.components
;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.LinkedList;

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
				new LinkedList<Packet>(), admin, rightChannels,
				ackTimeout));
	}
	
	@Override
	public synchronized void handlePacket(Packet packet) {
		
		logger.debug("Handling " + packet);

		if (packet.getGroup().equals(PacketGroup.COMMAND)) {
			
			this.getPortSender().addToBuffer(packet);
			
		} else if (packet.getType().equals(PacketType.ALM)) {
			
			logger.debug("Alarm Data received - sending it to UI");
			
			admin.getUserInterface().showAlarmData(packet);
		
		} else if (packet.getGroup().equals(PacketGroup.QUERY)) {
			
			if (packet.getValue().equals("")) {
				
				logger.debug("Sending Query packet - adding to buffer"); 
				
				this.getPortSender().addToBuffer(packet);
				
			} else {

				logger.debug("Response to Query packet received - sending it to UI"); 
				
				admin.getUserInterface().showReport(packet);
			
			}
						
		} else if (packet.getGroup().equals(PacketGroup.ACKNOWLEDGEMENT)) {
			logger.debug("Passing ACK to sender");
			
			((AbstractUdpPortSender) this.getPortSender()).passAck();
		} else {
			logger.warn("Wrong type group of Packet Received");
		}
	}

	@Override
	public void close() {
		
		super.close();
		
		this.rightSocket.close();
		
	}

}
