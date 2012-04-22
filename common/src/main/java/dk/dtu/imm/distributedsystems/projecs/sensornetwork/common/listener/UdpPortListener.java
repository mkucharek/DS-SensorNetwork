package dk.dtu.imm.distributedsystems.projecs.sensornetwork.common.listener;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import org.slf4j.Logger;

import dk.dtu.imm.distributedsystems.projecs.sensornetwork.common.packet.Packet;
import dk.dtu.imm.distributedsystems.projecs.sensornetwork.common.packet.PacketType;

/**
 * The standard Udp Port Listener thread that listens on the provided
 * port and receives Packet objects. Whenever a Packet is received, 
 * the execution is being forwarded to abstract handleIncomingPacket
 * method. This class also provides a method to send and ACK packet
 * back to the sender of the incoming Packet.
 *
 * @see Packet
 */
public abstract class UdpPortListener extends PortListener {

	/** The logger. */
	protected Logger logger;

	/** The Constant PACKET_SIZE. */
	public static final int PACKET_SIZE = 512;

	/** The server socket. */
	protected DatagramSocket serverSocket;

	/**
	 * Instantiates a new udp port listener.
	 *
	 * @param logger the logger
	 * @param portNumber the port number
	 */
	public UdpPortListener(Logger logger, int portNumber) {
		super(portNumber);

		this.logger = logger;
	}

	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public final void run() {
		try {

			// Create a Datagram socket on the chosen port
			serverSocket = new DatagramSocket(portNumber);

			byte[] receiveData = null;

			while (true) {

				receiveData = new byte[UdpPortListener.PACKET_SIZE];

				// Create a new Datagram packet for the client message
				DatagramPacket receivePacket = new DatagramPacket(receiveData,
						receiveData.length);

				if (logger.isDebugEnabled()) {
					logger.debug("Waiting for datagram packet");
				}

				// Receive the message
				serverSocket.receive(receivePacket);

				ObjectInputStream ois = new ObjectInputStream(
						new ByteArrayInputStream(receivePacket.getData()));

				Packet packet = (Packet) ois.readObject();

				// Obtain IP address and port of the sender
				InetAddress fromIpAddress = receivePacket.getAddress();
				int fromPortNumber = receivePacket.getPort();

				// call handler method that will be provided by concrete
				// implementations
				this.handleIncomingPacket(packet, fromIpAddress, fromPortNumber);

			}

		} catch (SocketException ex) {
			System.out.println("UDP Port " + portNumber + " is occupied.");
			System.exit(1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Send ack packet to the provided client.
	 *
	 * @param toIpAddress the to ip address
	 * @param toPortNumber the to port number
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void sendAck(InetAddress toIpAddress, int toPortNumber)
			throws IOException {

		// Create Packet object and serialize it to a byte array
		Packet ackPacket = new Packet(PacketType.ACK);

		ByteArrayOutputStream bStream = new ByteArrayOutputStream();
		ObjectOutput oo = new ObjectOutputStream(bStream);
		oo.writeObject(ackPacket);
		oo.close();

		byte[] serializedAckPacket = bStream.toByteArray();

		// check if the packet size fits in the default size
		if (serializedAckPacket.length > UdpPortListener.PACKET_SIZE) {
			throw new RuntimeException("Packet " + ackPacket
					+ "exceeds the default packet size");
		}

		// Create a new Datagram packet for the client
		DatagramPacket sendPacket = new DatagramPacket(serializedAckPacket,
				serializedAckPacket.length, toIpAddress, toPortNumber);

		// Send reponse back to the client
		serverSocket.send(sendPacket);

	}

	/**
	 * Handle an incoming packet. This method needs to be provided by
	 * the concrete implementations of that class.
	 *
	 * @param packet the packet
	 * @param fromIpAddress the from ip address
	 * @param fromPortNumber the from port number
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected abstract void handleIncomingPacket(Packet packet,
			InetAddress fromIpAddress, int fromPortNumber) throws IOException;
}
