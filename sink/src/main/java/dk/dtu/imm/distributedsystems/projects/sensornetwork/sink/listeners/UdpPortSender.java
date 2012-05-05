package dk.dtu.imm.distributedsystems.projects.sensornetwork.sink.listeners;

import java.io.IOException;
import java.util.Queue;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.channels.Channel;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.sender.udp.AbstractNodeUdpPortSender;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.exceptions.ConnectionHandlerException;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.exceptions.WrongPacketSizeException;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.Packet;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.PacketGroup;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.PacketType;

/**
 * The Class Sender.
 *
 * @author Maciej Kucharek <a href="mailto:s091828 (at) student.dtu.dk">s091828 (at) student.dtu.dk</a>
 */
public class UdpPortSender extends AbstractNodeUdpPortSender {
	
	/**
	 * Instantiates a new sender.
	 *
	 * @param portNumber the port number
	 * @param buffer the buffer
	 * @param leftChannels the left channels
	 * @param rightChannels the right channels
	 * @param ackTimeout the ack timeout
	 */
	public UdpPortSender(int portNumber, Queue<Packet> buffer, Channel[] leftChannels,
			Channel[] rightChannels, int ackTimeout) {
		super(portNumber, buffer, leftChannels, rightChannels, ackTimeout);
		
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

	/* (non-Javadoc)
	 * @see dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.sender.AbstractPortSender#handleOutgoingPacket(dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.Packet)
	 */
	@Override
	protected void handleOutgoingPacket(Packet packet) throws ConnectionHandlerException, InterruptedException {
		
		// TODO: Check implementation
		
		try {
			if (packet.getGroup() == PacketGroup.SENSOR_DATA) {
				
				if (packet.getType() == PacketType.DAT) { // DATA
					sendUnicastLeft(packet);
					
				} else if (packet.getType() == PacketType.ALM) { // ALARM_DATA
					
					if (!sendUnicastLeft(packet)) {
						sendUnicastLeft(packet); // retransmit
					}
					
				} else {
					logger.info("Unknown SENSOR_DATA packet type - dropped");
				}
			} else if (packet.getGroup() == PacketGroup.COMMAND) {
				sendMulticastRight(packet);
			} else {
				logger.info("Invalid packet received: [Group = '" + packet.getGroup() + "', Type = '" + packet.getType() + "'] - dropped");
			}
		} catch (WrongPacketSizeException e) {
			logger.warn(e.getMessage() + " The actual packet size is: " + e.getActualSize(), e);
		} catch (IOException e) {
			throw new ConnectionHandlerException(e, this.getClass());
		}
		
	}
	
}