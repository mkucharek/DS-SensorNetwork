package dk.dtu.imm.distributedsystems.projecs.sensornetwork.common.listener;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class PortListener extends Thread {

	private int portNumber;
	
	public PortListener(int portNumber) {
		this.portNumber = portNumber;
	}
	
	@Override
	public void run() {
//		try {
//			// Create a Datagram socket on port PORT
//			DatagramSocket serverSocket = new DatagramSocket(portNumber);
//
//			byte[] receiveData = new byte[1024];
//			byte[] sendData  = new byte[1024];
//
//			while(true) {
//				
//			
//
//							receiveData = new byte[1024];
//
//							// Create a new Datagram packet for the client message
//							DatagramPacket receivePacket =
//								new DatagramPacket(receiveData, receiveData.length);
//
//							System.out.println ("Waiting for datagram packet");
//
//							// Receive the message
//							serverSocket.receive(receivePacket);
//
//							String sentence = new String(receivePacket.getData());
//
//							// Obraint IP address and port of the sender
//							InetAddress IPAddress = receivePacket.getAddress();
//							int port = receivePacket.getPort();
//
//							System.out.println ("From: " + IPAddress + ":" + port);
//							System.out.println ("Message: " + sentence);
//
//							String capitalizedSentence = sentence.toUpperCase();
//
//							sendData = capitalizedSentence.getBytes();
//
//							// Create a new Datagram packet for the client
//							DatagramPacket sendPacket =
//								new DatagramPacket(sendData, sendData.length, IPAddress,
//																	 port);
//
//							// Send reponse back to the client
//							serverSocket.send(sendPacket);
//
//						}
//
//					}
//					catch (SocketException ex) {
//						System.out.println("UDP Port " + PORT + " is occupied.");
//						System.exit(1);
//					}

	}
}
