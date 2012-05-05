package dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.sender.udp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Queue;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.GlobalUtility;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.sender.AbstractPortSender;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.timer.Timer;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.timer.TimerHolder;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.exceptions.WrongPacketSizeException;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.Packet;

/**
 * The Class AbstractUdpPortSender.
 *
 * @author Maciej Kucharek <a href="mailto:s091828 (at) student.dtu.dk">s091828 (at) student.dtu.dk</a>
 */
public abstract class AbstractUdpPortSender extends AbstractPortSender implements TimerHolder {

	/** The server socket. */
	protected DatagramSocket serverSocket;
	
	protected Boolean ackObtained = false;
	
	protected int ackTimeout;
	
	protected Timer timer;
	
	/**
	 * Instantiates a new abstract udp port sender.
	 *
	 * @param portNumber the port number
	 * @param buffer the buffer
	 */
	public AbstractUdpPortSender(int portNumber, Queue<Packet> buffer, int ackTimeout) {
		super(portNumber, buffer);
		
		this.ackTimeout = ackTimeout;
	}
	
	/* (non-Javadoc)
	 * @see dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.AbstractPortHandler#setUp()
	 */
	@Override
	protected void setUp() throws SocketException {
		
		// Create a Datagram socket on the chosen port
		serverSocket = new DatagramSocket(portNumber);
		
		this.timer = new Timer(ackTimeout, this);
	}
	
	@Override
	public void interrupt() {
		super.interrupt();
		this.serverSocket.close();
	}
	
	/**
	 * Pass ack.
	 */
	public synchronized void passAck() {
		this.ackObtained = true;
		this.timer.interrupt();
		this.notify();
	}
	
	/**
	 * Send packet.
	 *
	 * @param packet the packet
	 * @param toIpAddress the to ip address
	 * @param toPortNumber the to port number
	 * @throws WrongPacketSizeException the wrong packet size exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void sendPacket(Packet packet, InetAddress toIpAddress, int toPortNumber) throws WrongPacketSizeException, IOException {

		ByteArrayOutputStream bStream = new ByteArrayOutputStream();
		ObjectOutput oo = new ObjectOutputStream(bStream);
		oo.writeObject(packet);
		oo.close();
		
		byte[] serializedPacket = bStream.toByteArray();
		
		// check if the packet size fits in the default size
		if (serializedPacket.length > GlobalUtility.UDP_PACKET_SIZE) {
			throw new WrongPacketSizeException("Packet " + packet
					+ "exceeds the default packet size.", serializedPacket.length);
		}
		
		DatagramPacket sendPacket = new DatagramPacket(serializedPacket,
				serializedPacket.length, toIpAddress, toPortNumber);

		serverSocket.send(sendPacket);
		
	}

}