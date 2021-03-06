package dk.dtu.imm.distributedsystems.projects.sensornetwork.admin.sender;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Queue;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.admin.Admin;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.admin.AdminUtility;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.channels.Channel;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.sender.udp.AbstractUdpPortSender;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.timer.Timer;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.exceptions.ConnectionHandlerException;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.exceptions.WrongPacketSizeException;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.logging.LoggingUtility;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.MessageType;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.Packet;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.PacketGroup;

/**
 * The Class AdminUdpPortSender.
 */
public class AdminUdpPortSender extends AbstractUdpPortSender {
	
	/** The related admin. */
	private Admin relatedAdmin;
	
	/** The right socket. */
	protected DatagramSocket rightSocket;
	
	/** The right channels. */
	protected Channel[] rightChannels;
	
	/** The query obtained. */
	private Boolean queryObtained = false;
	
	/** The query timeout. */
	private int queryTimeout;

	/**
	 * Instantiates a new sender.
	 *
	 * @param nodeId the node id
	 * @param rightSocket the right socket
	 * @param buffer the buffer
	 * @param admin the admin
	 * @param rightChannels the right channels
	 * @param ackTimeout the ack timeout
	 */
	public AdminUdpPortSender(String nodeId, DatagramSocket rightSocket, Queue<Packet> buffer,
			Admin admin, Channel[] rightChannels, int ackTimeout) {
		super(nodeId, buffer, ackTimeout);
		this.rightSocket = rightSocket;
		this.rightChannels = rightChannels;
		
		this.relatedAdmin = admin;
		
		this.queryTimeout = AdminUtility.QUERY_TIMEOUT_MS;
		
		this.start();
	}
	
	/* (non-Javadoc)
	 * @see dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.sender.udp.AbstractUdpPortSender#passAck()
	 */
	public synchronized void passQuery() {
		
		try {
			this.timer.interrupt();
		} catch (NullPointerException e) {
			return;
		}
		this.queryObtained = true;
		this.notify();
	}
	
	
	/**
	 * Send unicast right.
	 *
	 * @param packet the packet
	 * @return true, if successful
	 * @throws WrongPacketSizeException the wrong packet size exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	protected boolean sendUnicastRight(Packet packet) throws WrongPacketSizeException, IOException, InterruptedException {
		
		logger.debug("Sending unicast right " + packet);

		for (Channel channel : this.rightChannels) {

			InetAddress currentLeftChannelIP = InetAddress.getByName(channel
					.getIpAddress());

			// send Packet to left channel through UDP Connection
			sendPacket(rightSocket, packet, currentLeftChannelIP,
					channel.getPortNumber());

			if (packet.getGroup().equals(PacketGroup.COMMAND)) {

				LoggingUtility.logMessage(this.getNodeId(), channel.getId(),
						MessageType.SND, packet.getType(), packet.getValue());

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

				relatedAdmin.getUserInterface().showError(packet);

			} else if (packet.getGroup().equals(PacketGroup.QUERY)) {

				LoggingUtility.logMessage(this.getNodeId(), channel.getId(),
						MessageType.SND, packet.getType());
				
				timer = new Timer(queryTimeout, this);
				this.queryObtained = false;
				timer.start();

				synchronized (this) {
					this.wait();
				}

				synchronized (this.queryObtained) {
					if (this.queryObtained) {
						logger.debug("Report for QUERY received");
						this.queryObtained = false;
						return true;
					}
				}

				logger.info("Timeout passed");

				relatedAdmin.getUserInterface().showError(packet);

			} else {

				logger.warn("Wrong type of packet to be sent");

			}
		}
		
		return false;
	}

	/* (non-Javadoc)
	 * @see dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.sender.AbstractPortSender#handleOutgoingPacket(dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.Packet)
	 */
	@Override
	protected void handleOutgoingPacket(Packet packet) throws ConnectionHandlerException, InterruptedException {
		
		logger.debug("Sending " + packet);
		
		try {
			if (packet.getGroup().equals(PacketGroup.COMMAND) ||
					packet.getGroup().equals(PacketGroup.QUERY)) {
				
				sendUnicastRight(packet);
			
			} else {
			
				logger.warn("Received invalid " + packet);
			
			}
		} catch (WrongPacketSizeException e) {
			logger.warn(
					e.getMessage() + " The actual packet size is: "
							+ e.getActualSize(), e);
		} catch (IOException e) {
			throw new ConnectionHandlerException(e, this.getClass());
		}
		
	}
	
}
