package dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.sender;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Queue;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.channels.Channel;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.sender.udp.AbstractUdpPortSender;
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
public class Sender extends AbstractUdpPortSender {
	
	/** The left channels. */
	private Channel[] leftChannels;
	
	/** The right channels. */
	private Channel[] rightChannels;

	/** The successful sends. */
	public int successfulSends; // TODO to be deleted; implemented for SenderTest purposes
	

	/**
	 * Instantiates a new sender.
	 *
	 * @param portNumber the port number
	 * @param buffer the buffer
	 * @param leftChannels the left channels
	 * @param rightChannels the right channels
	 * @param ackTimeout the ack timeout
	 */
	public Sender(int portNumber, Queue<Packet> buffer, Channel[] leftChannels,
			Channel[] rightChannels, int ackTimeout) {
		super(portNumber, buffer, ackTimeout);
		this.leftChannels = leftChannels;
		this.rightChannels = rightChannels;
		
		this.successfulSends = 0;
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
	
	/**
	 * Send unicast left.
	 *
	 * @param packet the packet
	 * @return true, if successful
	 * @throws WrongPacketSizeException the wrong packet size exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws InterruptedException 
	 */
	private boolean sendUnicastLeft(Packet packet) throws WrongPacketSizeException, IOException, InterruptedException {
		
		for(Channel channel : this.leftChannels) {
			
			InetAddress currentLeftChannelIP = InetAddress.getByName(channel.getIpAddress());

			// TODO Should the Addresses in Sensor Class be already InetAddresses when they are read form properties files?
			
			// send Packet to left channel through UDP Connection
			sendPacket(packet, currentLeftChannelIP, channel.getPortNumber());

			
			// TODO Log packets sent - SENSOR_DATA: DAT, ALM
			
			timer.start();
			
			synchronized (this) {
				this.wait();
			}
			
			synchronized (this.ackObtained) {
				if(this.ackObtained) {
					++successfulSends;
					this.ackObtained = false;
					return true;
				}
			}
			
			// timeout passed - transmit to the next one
			
		}
		
		// packet not sent
		
		return false;
	}

	/**
	 * Send multicast right.
	 *
	 * @param packet the packet
	 * @throws WrongPacketSizeException the wrong packet size exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void sendMulticastRight(Packet packet) throws WrongPacketSizeException, IOException {
		
		for(Channel channel : this.rightChannels) {
			
			InetAddress currentLeftChannelIP = InetAddress.getByName(channel.getIpAddress());

			// TODO Should the Addresses in Sensor Class be already InetAddresses when they are read form properties files?
			
			// send Packet to right channel through UDP Connection
			sendPacket(packet, currentLeftChannelIP, channel.getPortNumber());

			
			// TODO Log packets sent - CMD: THR, PRD
			
		}
		
	}
	
}
