package dk.dtu.imm.distributedsystems.projects.sensornetwork.admin.sender;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Queue;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.channels.Channel;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.sender.udp.AbstractUdpPortSender;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.timer.Timer;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.exceptions.ConnectionHandlerException;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.exceptions.WrongPacketSizeException;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.logging.LoggingUtility;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.MessageType;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.Packet;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.PacketGroup;

public class AdminUdpPortSender extends AbstractUdpPortSender {
	
	/** The right socket. */
	protected DatagramSocket rightSocket;
	
	/** The right channels. */
	protected Channel[] rightChannels;

	/**
	 * Instantiates a new sender.
	 *
	 * @param portNumber the port number
	 * @param buffer the buffer
	 * @param leftChannels the left channels
	 * @param rightChannels the right channels
	 * @param ackTimeout the ack timeout
	 */
	public AdminUdpPortSender(String nodeId, DatagramSocket rightSocket, Queue<Packet> buffer,
			Channel[] rightChannels, int ackTimeout) {
		super(nodeId, buffer, ackTimeout);
		this.rightSocket = rightSocket;
		this.rightChannels = rightChannels;
		
		this.start();
	}
	
	/**
	 * Send unicast right.
	 *
	 * @param packet the packet
	 * @return true, if successful
	 * @throws WrongPacketSizeException the wrong packet size exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws InterruptedException 
	 */
	protected boolean sendUnicastRight(Packet packet) throws WrongPacketSizeException, IOException, InterruptedException {
		
		logger.debug("Sending unicast right " + packet);
		
		for(Channel channel : this.rightChannels) {
			
			InetAddress currentLeftChannelIP = InetAddress.getByName(channel.getIpAddress());

			// send Packet to left channel through UDP Connection
			sendPacket(rightSocket, packet, currentLeftChannelIP, channel.getPortNumber());

			LoggingUtility.logMessage(this.getNodeId(), channel.getId(), MessageType.SND, packet.getType(), packet.getSrcNodeId() + ":" + packet.getValue());
			
			timer = new Timer(ackTimeout, this);
			this.ackObtained = false;
			timer.start();
			
			synchronized (this) {
				this.wait();
			}
			
			synchronized (this.ackObtained) {
				if(this.ackObtained) {
					logger.debug("ACK received");
					this.ackObtained = false;
					return true;
				}
			}
			
			logger.debug("Timeout passed");
			// timeout passed
			
		}
		
		return false;
	}

	/* (non-Javadoc)
	 * @see dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.sender.AbstractPortSender#handleOutgoingPacket(dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.Packet)
	 */
	@Override
	protected void handleOutgoingPacket(Packet packet) throws ConnectionHandlerException, InterruptedException {
		
		logger.debug("Sending " + packet);
		
		// TODO: Handle QUERY packet type
		
		try {
			if (packet.getGroup().equals(PacketGroup.COMMAND)) {
				sendUnicastRight(packet);
			} else {
				logger.info("Received invalid " + packet);
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
