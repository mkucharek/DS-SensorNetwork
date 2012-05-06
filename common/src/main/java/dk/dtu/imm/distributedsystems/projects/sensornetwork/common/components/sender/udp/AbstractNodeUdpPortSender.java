package dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.sender.udp;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Queue;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.channels.Channel;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.timer.Timer;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.exceptions.WrongPacketSizeException;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.Packet;

public abstract class AbstractNodeUdpPortSender extends AbstractUdpPortSender {
	
	/** The left channels. */
	protected Channel[] leftChannels;
	
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
	public AbstractNodeUdpPortSender(int portNumber, Queue<Packet> buffer, Channel[] leftChannels,
			Channel[] rightChannels, int ackTimeout) {
		super(portNumber, buffer, ackTimeout);
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
	 * @throws InterruptedException 
	 */
	protected boolean sendUnicastLeft(Packet packet) throws WrongPacketSizeException, IOException, InterruptedException {
		
		for(Channel channel : this.leftChannels) {
			
			InetAddress currentLeftChannelIP = InetAddress.getByName(channel.getIpAddress());

			// TODO Should the Addresses in Sensor Class be already InetAddresses when they are read form properties files?
			
			// send Packet to left channel through UDP Connection
			sendPacket(packet, currentLeftChannelIP, channel.getPortNumber());

			
			// TODO Log packets sent - SENSOR_DATA: DAT, ALM
			
			timer = new Timer(ackTimeout, this);
			timer.start();
			
			synchronized (this) {
				this.wait();
			}
			
			synchronized (this.ackObtained) {
				if(this.ackObtained) {
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
	protected void sendMulticastRight(Packet packet) throws WrongPacketSizeException, IOException {
		
		for(Channel channel : this.rightChannels) {
			
			InetAddress currentLeftChannelIP = InetAddress.getByName(channel.getIpAddress());

			// TODO Should the Addresses in Sensor Class be already InetAddresses when they are read form properties files?
			
			// send Packet to right channel through UDP Connection
			sendPacket(packet, currentLeftChannelIP, channel.getPortNumber());

			
			// TODO Log packets sent - CMD: THR, PRD
			
		}
		
	}
	
}
