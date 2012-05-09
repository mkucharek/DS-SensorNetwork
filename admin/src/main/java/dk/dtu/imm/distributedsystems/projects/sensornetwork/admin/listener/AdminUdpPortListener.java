package dk.dtu.imm.distributedsystems.projects.sensornetwork.admin.listener;

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

/**
 * The Class AdminUdpPortListener.
 */
public final class AdminUdpPortListener extends AbstractUdpPortListener {

	/** The transceiver. */
	private AbstractTransceiver transceiver;
	
	/**
	 * Instantiates a new admin udp port listener.
	 *
	 * @param nodeId the node id
	 * @param relatedTransceiver the related transceiver
	 * @param socket the socket
	 * @param associatedChannels the associated channels
	 */
	public AdminUdpPortListener(String nodeId, AbstractTransceiver relatedTransceiver, DatagramSocket socket, Channel[] associatedChannels) {
		super(nodeId, socket, associatedChannels);
		this.transceiver = relatedTransceiver;
		
		this.start();
	}
	
	/* (non-Javadoc)
	 * @see dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.listener.udp.AbstractUdpPortListener#handleIncomingPacket(dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.Packet, java.net.InetAddress, int)
	 */
	@Override
	protected void handleIncomingPacket(Packet packet,
			InetAddress fromIpAddress, int fromPortNumber) throws IOException {

		logger.debug("Received " + packet);
		
		
		if (packet.getType().equals(PacketType.ALM) || 
				packet.getGroup().equals(PacketGroup.ACKNOWLEDGEMENT) ||
				packet.getGroup().equals(PacketGroup.QUERY)) {
				
			logger.debug(packet + " accepted by listener");
			
			if (packet.getType().equals(PacketType.ALM)) {
				
				LoggingUtility.logMessage(this.getNodeId(),
						getAssociatedChannelId(fromIpAddress, fromPortNumber),
						MessageType.RCV,
						packet.getType(), 
						packet.getSrcNodeId() + ":" + packet.getValue());
				
				sendAck(fromIpAddress, fromPortNumber);
				
				LoggingUtility.logMessage(this.getNodeId(),
						getAssociatedChannelId(fromIpAddress, fromPortNumber),
						MessageType.SND,
						PacketType.ACK);
			
			} else if (packet.getGroup().equals(PacketGroup.QUERY)) {
				
				LoggingUtility.logMessage(this.getNodeId(),
						getAssociatedChannelId(fromIpAddress, fromPortNumber),
						MessageType.RCV,
						packet.getType(), 
						packet.getValue());
				
			} else { // ACK
			
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
