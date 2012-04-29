package dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.components;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.direction.Direction;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.Packet;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.PacketGroup;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.PacketType;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.Sensor;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.listener.LeftUdpPortListener;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.listener.RightUdpPortListener;


public class TransceiverComponent extends AbstractComponent {

	private Sensor sensor;
	private SensorComponent relatedSensor;
	
	private LeftUdpPortListener leftListner;
	private RightUdpPortListener rightListner;
	
	private TimerComponent transceiverTimer;
	
	int ackTimeout;
	
	Packet currentPacketWaitingForAck;
			
	public TransceiverComponent(Sensor sensor) {
		this.sensor = sensor;
		this.relatedSensor = sensor.getSensorComponent();
		this.ackTimeout = sensor.getAckTimeout();
	}
    
	/**
	 * Multicast
	 * 
	 * @param packet
	 * @param direction
	 */
	public synchronized void forwardPacketMulticast(Packet packet, Direction direction) {
		
	}
	
	/**
	 * Unicast
	 * 
	 * @param packet
	 * @param direction
	 */
	public synchronized void forwardPacketUnicast(Packet packet, Direction direction, boolean retransmit) {
		
		for (int channel = 0; channel < sensor.getLeftChannelIDs().length; channel++) {
			transceiverTimer = new TimerComponent(ackTimeout);
			transceiverTimer.start(); // create Timer and set it
		
			currentPacketWaitingForAck = packet;	
			
			Packet packetFromLeftChannel = leftListner.getCurrentPacket();
			
			
		}
		
		if (retransmit) {
			forwardPacketUnicast(packet, direction, false);
		} else {
			return; // packet dropped
		}
	}
	
//	/**
//	 * Sends CMD packet over control channel
//	 * 
//	 * @param packet
//	 */
//	public synchronized void sendToSensor(Packet packet) {
//		relatedSensor.setCurrentPacket(packet);
//	}

	@Override
	public void run() {
		
		while (true) {
		
			/*
			 *  fetch packet from sensor
			 *  packet group: SENSOR_DATA (DATA, ALARM_DATA)
			 */
			
			Packet packetFromSensor = relatedSensor.getCurrentOutPacket();
			
			if (packetFromSensor.getGroup() == PacketGroup.COMMAND) {
				if (packetFromSensor.getType() == PacketType.DAT) { 
					if (!(transceiverTimer.isAlive())) {
						forwardPacketUnicast(packetFromSensor, Direction.LEFT, false);
					} else {
					
					/*
					 * what happens when packet from sensor wants to take timer,
					 * but cannot because some other packet is waiting for timeout
					 */
					
					}
				} else if (packetFromSensor.getType() == PacketType.ALM) { // ALARM_DATA
					if (!(transceiverTimer.isAlive())) {
							
						/*
						 * retransmit: once again to all if no ACKs were received from any of the left
						 * 
						 * how to implement retransmit?
						 */
						
						forwardPacketUnicast(packetFromSensor, Direction.LEFT, true); 
						
	
						transceiverTimer = new TimerComponent(ackTimeout);
						transceiverTimer.start(); // create Timer and set it
					} else {
						
					}
				} else {
					System.err.println("Sensor node - Transceiver: Wrong type of packet received on sensor channel");
				}
			}
			
			
			/*
			 *  fetch packet from left listener
			 *  packet group: CMD - push to sensor and forward multicast to right channel
			 *  packet type: ACK
			 */
			
			Packet packetFromLeftChannel = leftListner.getCurrentPacket();
	
			if (packetFromLeftChannel.getGroup() == PacketGroup.COMMAND) {
				relatedSensor.setCurrentPacket(packetFromLeftChannel);
				
				forwardPacketMulticast(packetFromLeftChannel, Direction.RIGHT);
			} else if (packetFromLeftChannel.getType() == PacketType.ACK) {	
				if (packetFromLeftChannel.equals(currentPacketWaitingForAck)) {
					
					/*
					 * should compare seq numbers, not whole packages
					 * 
					 * need some way to differentiate between unique packages: seq numbers
					 * 
					 * the seq number of ACK packet should agree
					 * with the one that transceiver waits for
					 * before timer reset
					 */
//					if (transceiverTimer.isAlive()) { // This will never happen
						transceiverTimer.interrupt(); // reset Timer
//					} else {
//						// ACK arrived to late, drop it
//						packetFromLeftChannel = null;
//					}
				} else {
					// wrong ACK arrived, drop it
					packetFromLeftChannel = null;
				}

			} else {
				System.err.println("Sensor node - Transceiver: Wrong type of packet received on left channel");
			}
	
			/*
			 * fetch packet from right listener
			 * packet group: CMD - forward to left channel
			 */
	
			Packet packetFromRightChannel = rightListner.getCurrentPacket();
	
			if (packetFromRightChannel.getGroup() == PacketGroup.SENSOR_DATA) {
				forwardPacketUnicast(new Packet(PacketType.ACK), Direction.RIGHT, false);
				
				if (packetFromRightChannel.getType() == PacketType.DAT) { 
					if (!(transceiverTimer.isAlive())) {
						forwardPacketUnicast(packetFromRightChannel, Direction.LEFT, false);
					

					} else {
						
					}
					
					// to musi byc inny timer - to co ma byc kilka timerow na raz?
					// ma jeden czekac az drugi skonczy zanim zaczac?
					
					
				} else if (packetFromRightChannel.getType() == PacketType.ALM) { // ALARM_DATA
					if (!(transceiverTimer.isAlive())) {
						forwardPacketUnicast(packetFromRightChannel, Direction.LEFT, true); 
						
						transceiverTimer = new TimerComponent(ackTimeout);
						transceiverTimer.start(); // create Timer and set it
						
						currentPacketWaitingForAck = packetFromRightChannel;
						
						// if unsuccessful on all channels forward once again with retransmit = false
						// forwardPacketUnicast(packetFromSensor, Direction.LEFT, false); 
						
						
					}
					
				} else {
					System.err.println("Sensor node - Transceiver: Wrong type of packet received on right channel");
				}
			}
		}
		
	}
	
}
