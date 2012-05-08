package dk.dtu.imm.distributedsystems.projects.sensornetwork.sink.sender;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Queue;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.channels.Channel;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.sender.udp.AbstractNodeUdpPortSender;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.exceptions.ConnectionHandlerException;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.exceptions.WrongPacketSizeException;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.logging.LoggingUtility;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.MessageType;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.Packet;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.PacketGroup;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.PacketType;

public class SinkUdpPortSender extends AbstractNodeUdpPortSender {
	
	/**
	 * Instantiates a new sender.
	 *
	 * @param portNumber the port number
	 * @param buffer the buffer
	 * @param leftChannels the left channels
	 * @param rightChannels the right channels
	 * @param ackTimeout the ack timeout
	 */
	public SinkUdpPortSender(String nodeId, DatagramSocket leftSocket, DatagramSocket rightSocket, Queue<Packet> buffer, Channel[] leftChannels,
			Channel[] rightChannels, int ackTimeout) {
		super(nodeId, leftSocket, rightSocket, buffer, leftChannels, rightChannels, ackTimeout);
		this.leftChannels = leftChannels;
		this.rightChannels = rightChannels;
		
		this.start();
	}
	
	/**
	 * Gets the left channels.
	 *
	 * @return the left channels
	 */
	public Channel[] getLeftChannels() {
		return leftChannels;
	}

	/**
	 * Gets the right channels.
	 *
	 * @return the right channels
	 */
	public Channel[] getRightChannels() {
		return rightChannels;
	}

	protected void sendReport(Packet packet)
			throws WrongPacketSizeException, IOException, InterruptedException {
		
		logger.debug("Sending report thorugh unicast left " + packet);

		InetAddress currentLeftChannelIP = InetAddress
				.getByName(this.leftChannels[0].getIpAddress());

		// send Packet to left channel through UDP Connection
		sendPacket(leftSocket, packet, currentLeftChannelIP,
				this.leftChannels[0].getPortNumber());

		if (packet.getGroup().equals(PacketGroup.SENSOR_DATA)) {

			LoggingUtility.logMessage(this.getNodeId(), this.leftChannels[0].getId(),
					MessageType.SND, packet.getType(), packet.getSrcNodeId()
							+ ":" + packet.getValue());

		} else if (packet.getGroup().equals(PacketGroup.QUERY)) {

			LoggingUtility.logMessage(this.getNodeId(), this.leftChannels[0].getId(),
					MessageType.SND, packet.getType(), packet.getValue());

		} else {

			logger.warn("Wrong type of packet to be sent");

		}
	}
	
	/* (non-Javadoc)
	 * @see dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.sender.AbstractPortSender#handleOutgoingPacket(dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.Packet)
	 */
	@Override
	protected void handleOutgoingPacket(Packet packet) throws ConnectionHandlerException, InterruptedException {
		
		logger.debug("Sending " + packet);
		
		try {
			if (packet.getGroup().equals(PacketGroup.COMMAND)) {
				
				sendMulticastRight(packet);
				
			} else if (packet.getGroup().equals(PacketGroup.QUERY)) {
				
				// send Report to Query without ACK (no TIMEOUT)
				
				sendReport(packet);
					
			} else if (packet.getType().equals(PacketType.ALM)) {
				
				sendUnicastLeft(packet);
				
			} else {
				
				logger.warn("Invalid packet received in Sender: [Group = '"
						+ packet.getGroup() + "', Type = '" + packet.getType()
						+ "'] - dropped");
				
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
