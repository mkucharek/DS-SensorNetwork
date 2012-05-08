package dk.dtu.imm.distributedsystems.projects.sensornetwork.sink.listeners;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.channels.Channel;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.listener.udp.AbstractUdpPortListener;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.transceiver.AbstractTransceiver;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.logging.LoggingUtility;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.MessageType;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.Packet;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.PacketGroup;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.PacketType;

public final class SinkUdpPortListener extends AbstractUdpPortListener {

	private AbstractTransceiver transceiver;
	
	public SinkUdpPortListener(String nodeId, AbstractTransceiver relatedTransceiver, DatagramSocket socket, Channel[] associatedChannels) {
		super(nodeId, socket, associatedChannels);
		this.transceiver = relatedTransceiver;
		
		this.start();
	}
	
	@Override
	protected void handleIncomingPacket(Packet packet,
			InetAddress fromIpAddress, int fromPortNumber) throws IOException {

		logger.debug("Received " + packet);

		if ((packet.getGroup().equals(PacketGroup.COMMAND) ||
				packet.getGroup().equals(PacketGroup.ACKNOWLEDGEMENT) ||
				packet.getGroup().equals(PacketGroup.QUERY))) {
			
			logger.debug(packet + " accepted by listener");
			
			if (packet.getGroup().equals(PacketGroup.COMMAND)) {
				
				LoggingUtility.logMessage(this.getNodeId(),
						getAssociatedChannelId(fromIpAddress, fromPortNumber),
						MessageType.RCV,
						packet.getType(), 
						packet.getValue());
				
				sendAck(fromIpAddress, fromPortNumber);
				
				LoggingUtility.logMessage(this.getNodeId(),
						getAssociatedChannelId(fromIpAddress, fromPortNumber),
						MessageType.SND,
						PacketType.ACK);
					
			} else { // QUERY or ACK
				
				LoggingUtility.logMessage(this.getNodeId(),
						getAssociatedChannelId(fromIpAddress, fromPortNumber),
						MessageType.RCV,
						packet.getType());
			
			}
			
			transceiver.handlePacket(packet);
			
		} else {
			
			logger.warn(packet + "dropped by listener - wrong type");
		
		}
	}
}
