package dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.listener;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.components.TransceiverComponent;

public class RightUdpPortListener extends Thread {

	private Logger logger = LoggerFactory.getLogger(RightUdpPortListener.class);
	
	private final int PACKET_SIZE = 512;
	
	private int portNumber;
	
	private TransceiverComponent relatedTransceiver;
	
	private DatagramSocket serverSocket;
	
	public RightUdpPortListener(TransceiverComponent relatedTransceiver, int portNumber) {
		this.relatedTransceiver = relatedTransceiver;
		this.portNumber = portNumber;
	}
	
	@Override
	public void run() {
		try	{

			// Create a Datagram socket on port PORT
			serverSocket = new DatagramSocket(portNumber);

			byte[] receiveData = null;

			while(true) {

				receiveData = new byte[PACKET_SIZE];

				// Create a new Datagram packet for the client message
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

				if (logger.isDebugEnabled()) {
					logger.debug("Waiting for datagram packet");
				}

				// Receive the message
				try {
					serverSocket.receive(receivePacket);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				String sentence = new String(receivePacket.getData());

				// Obraint IP address and port of the sender
				InetAddress IPAddress = receivePacket.getAddress();
				int port = receivePacket.getPort();

				System.out.println ("From: " + IPAddress + ":" + port);
				System.out.println ("Message: " + sentence);

				String capitalizedSentence = sentence.toUpperCase();

//				sendData = capitalizedSentence.getBytes();
//
//				// Create a new Datagram packet for the client
//				DatagramPacket sendPacket =
//					new DatagramPacket(sendData, sendData.length, IPAddress,
//														 port);
//
//				// Send reponse back to the client
//				serverSocket.send(sendPacket);

			}

		}
		catch (SocketException ex) {
			System.out.println("UDP Port " + portNumber + " is occupied.");
			System.exit(1);
		}
	}
}
