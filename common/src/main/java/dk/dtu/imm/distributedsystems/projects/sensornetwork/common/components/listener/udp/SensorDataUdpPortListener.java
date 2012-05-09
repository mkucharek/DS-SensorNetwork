package dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.listener.udp;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.channels.Channel;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.transceiver.AbstractTransceiver;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.logging.LoggingUtility;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.MessageType;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.Packet;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.PacketGroup;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.PacketType;

/**
 * The concrete implementation of AbstractUdpPortListener that listens
 * only for the SENSOR_DATA packet groups.
 * 
 * @author Maciej Kucharek <a href="mailto:s091828 (at) student.dtu.dk">s091828 (at) student.dtu.dk</a>
 */
public class SensorDataUdpPortListener extends AbstractUdpPortListener {

	/** The transceiver. */
	protected AbstractTransceiver transceiver;

	/**
	 * Instantiates a new sensor data udp port listener.
	 *
	 * @param nodeId the node id
	 * @param relatedTransceiver the related transceiver
	 * @param socket the socket
	 * @param associatedChannels the associated channels
	 */
	public SensorDataUdpPortListener(String nodeId,
			AbstractTransceiver relatedTransceiver, DatagramSocket socket,
			Channel[] associatedChannels) {
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

		logger.debug(packet + " received");

		// SENSOR_DATA only
		LoggingUtility.logMessage(this.getNodeId(), packet.getSrcNodeId(), 
				MessageType.RCV, packet.getType(), packet.getSrcNodeId() + ":" + packet.getValue());

		this.sendAck(fromIpAddress, fromPortNumber);
		LoggingUtility.logMessage(this.getNodeId(), packet.getSrcNodeId(),
				MessageType.SND, PacketType.ACK);

		if (packet.getGroup().equals(PacketGroup.SENSOR_DATA)) {
			logger.debug(packet + " accepted by listener");

			transceiver.handlePacket(packet);
		} else {
			logger.warn(packet + "dropped by listener - wrong type");
		}
	}
}
