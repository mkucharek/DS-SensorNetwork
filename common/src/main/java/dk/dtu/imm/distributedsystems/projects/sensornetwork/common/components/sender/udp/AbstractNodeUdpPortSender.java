package dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.sender.udp;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Queue;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.channels.Channel;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.timer.Timer;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.exceptions.WrongPacketSizeException;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.logging.LoggingUtility;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.MessageType;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.Packet;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.PacketGroup;

/**
 * The Class AbstractNodeUdpPortSender.
 *
 * @author Maciej Kucharek <a href="mailto:s091828 (at) student.dtu.dk">s091828 (at) student.dtu.dk</a>
 */
public abstract class AbstractNodeUdpPortSender extends AbstractUdpPortSender {

	/** The server socket. */
	protected DatagramSocket leftSocket;

	/** The right socket. */
	protected DatagramSocket rightSocket;

	/** The left channels. */
	protected Channel[] leftChannels;

	/** The right channels. */
	protected Channel[] rightChannels;

	/**
	 * Instantiates a new sender.
	 *
	 * @param nodeId the node id
	 * @param leftSocket the left socket
	 * @param rightSocket the right socket
	 * @param buffer the buffer
	 * @param leftChannels the left channels
	 * @param rightChannels the right channels
	 * @param ackTimeout the ack timeout
	 */
	public AbstractNodeUdpPortSender(String nodeId, DatagramSocket leftSocket,
			DatagramSocket rightSocket, Queue<Packet> buffer,
			Channel[] leftChannels, Channel[] rightChannels, int ackTimeout) {
		super(nodeId, buffer, ackTimeout);

		this.leftSocket = leftSocket;
		this.rightSocket = rightSocket;

		this.leftChannels = leftChannels;
		this.rightChannels = rightChannels;

	}

	/**
	 * Send unicast left.
	 *
	 * @param packet the packet
	 * @return true, if successful
	 * @throws WrongPacketSizeException the wrong packet size exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	protected boolean sendUnicastLeft(Packet packet)
			throws WrongPacketSizeException, IOException, InterruptedException {

		logger.debug("Sending unicast left " + packet);

		for (Channel channel : this.leftChannels) {

			InetAddress currentLeftChannelIP = InetAddress.getByName(channel
					.getIpAddress());

			// send Packet to left channel through UDP Connection
			sendPacket(leftSocket, packet, currentLeftChannelIP,
					channel.getPortNumber());

			if (packet.getGroup().equals(PacketGroup.SENSOR_DATA)) {
				
				LoggingUtility.logMessage(this.getNodeId(), channel.getId(),
						MessageType.SND, packet.getType(),
						packet.getSrcNodeId() + ":" + packet.getValue());
				
			} else if (packet.getGroup().equals(PacketGroup.QUERY)) {
				
				LoggingUtility.logMessage(this.getNodeId(), channel.getId(),
						MessageType.SND, packet.getType(),
						packet.getValue());
				
			} else {
				
				logger.warn("Wrong type of packet to be sent");
				
			}

			timer = new Timer(ackTimeout, this);
			this.ackObtained = false;
			timer.start();

			synchronized (this) {
				this.wait();
			}

			synchronized (this.ackObtained) {
				if (this.ackObtained) {
					logger.debug("ACK received");
					this.ackObtained = false;
					return true;
				}
			}

			logger.info("Timeout passed");
			// timeout passed

		}

		return false;
	}

	/**
	 * Send multicast right.
	 * 
	 * @param packet
	 *            the packet
	 * @throws WrongPacketSizeException
	 *             the wrong packet size exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	protected void sendMulticastRight(Packet packet)
			throws WrongPacketSizeException, IOException {

		for (Channel channel : this.rightChannels) {

			InetAddress currentLeftChannelIP = InetAddress.getByName(channel
					.getIpAddress());

			// send Packet to right channel through UDP Connection
			sendPacket(rightSocket, packet, currentLeftChannelIP,
					channel.getPortNumber());
			
			// COMMAND packets only
			LoggingUtility.logMessage(this.getNodeId(), channel.getId(),
					MessageType.SND, packet.getType(), packet.getValue());
		}

	}

}
