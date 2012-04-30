package dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.sender;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Queue;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.listener.UdpPortListener;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.Packet;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.PacketGroup;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.PacketType;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.Sensor;

public class Sender extends Thread {

	private Sensor sensor;
	
	public int successfulSends; // TODO to be deleted; implemented for SenderTest purposes
	
	private Queue<Packet> buffer;
	
	private DatagramSocket serverSocket;
	
	public Sender(Sensor sensor) {
		this.sensor = sensor;
		this.buffer = new LinkedList<Packet>();
		successfulSends = 0;
	}
	
	public synchronized void addToBuffer(Packet packet) {
		this.buffer.offer(packet);
		this.notify();
	}
	
	public synchronized void passAck() {
		this.interrupt();
	}
	
	
	
	private void sendPacketOverUDP(Packet packet, InetAddress toIpAddress, int toPortNumber) throws IOException {
		
		// TODO Implement UDP Connection

		ByteArrayOutputStream bStream = new ByteArrayOutputStream();
		ObjectOutput oo = new ObjectOutputStream(bStream);
		oo.writeObject(packet);
		oo.close();
		
		byte[] serializedPacket = bStream.toByteArray();
		
		// check if the packet size fits in the default size
		if (serializedPacket.length > UdpPortListener.PACKET_SIZE) {
			throw new RuntimeException("Packet " + packet
					+ "exceeds the default packet size");
		}
		
		DatagramPacket sendPacket = new DatagramPacket(serializedPacket,
				serializedPacket.length, toIpAddress, toPortNumber);

		serverSocket.send(sendPacket);
		
	}
	
	private boolean sendUnicastLeft(Packet packet) {
		
		for (int channelIndex = 0; channelIndex < sensor.getLeftChannelIDs().length; channelIndex++) {
			
//			InetAddress currentLeftChannelIP = null;
//			
//			try {
//				currentLeftChannelIP = InetAddress.getByName((sensor.getLeftChannelIPs())[channelIndex]);
//			
//				// TODO Should the Addresses in Sensor Class be already InetAddresses
//				// when they are read form properties files?
//			} catch (UnknownHostException e) {
//
//			}
//			
//			// send Packet to left channel through UDP Connection
//			
//			try {
//				sendPacketOverUDP(packet, currentLeftChannelIP, (sensor.getLeftChannelPorts())[channelIndex]);
//			} catch (IOException e) {
//
//			}
			
			// TODO Log packets sent - SENSOR_DATA: DAT, ALM
			
			synchronized (this) {
				try {
					
					this.wait(sensor.getAckTimeout());
				
				} catch (InterruptedException e) {
					// ACK received - packet received
					
					++successfulSends;
					return true;
				}
			}
			
			// timeout passed - transmit to the next one
			
		}
		// packet not sent
		
		return false;
	}
	
	private void sendMulticastRight(Packet packet) {
		
		for (int channelIndex = 0; channelIndex < sensor.getRightChannelIDs().length; channelIndex++) {
			
			InetAddress currentRightChannelIP = null;
			
			try {
				currentRightChannelIP = InetAddress.getByName((sensor.getRightChannelIPs())[channelIndex]);
			
				// TODO Should the Addresses in Sensor Class be already InetAddresses when they are read form properties files?
			} catch (UnknownHostException e) {

			}
			
			// send Packet to right channel through UDP Connection
			
			try {
				sendPacketOverUDP(packet, currentRightChannelIP, (sensor.getRightChannelPorts())[channelIndex]);
			} catch (IOException e) {

			}
			
			// TODO Log packets sent - CMD: THR, PRD
		}

	}
	
	@Override
	public void run() {
		
		while (true) {
			if (!buffer.isEmpty()) {
				
				Packet packet = buffer.poll();
				
				if (packet.getGroup() == PacketGroup.SENSOR_DATA) {
					
					if (packet.getType() == PacketType.DAT) { // DATA
						sendUnicastLeft(packet);
						
					} else if (packet.getType() == PacketType.ALM) { // ALARM_DATA
						
						if (!sendUnicastLeft(packet)) {
							sendUnicastLeft(packet); // retransmit
						}
						
					} else {
						System.err.println("");
					}
				} else if (packet.getGroup() == PacketGroup.COMMAND) {
					sendMulticastRight(packet);
				}
			}
			
			synchronized (this) {
				try {
					
					this.wait();
				
				} catch (InterruptedException e) {
					// ACK passed - continue
				}
			}
		}
		
	}
	
}
