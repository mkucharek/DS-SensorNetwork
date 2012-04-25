package dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.components;

import dk.dtu.imm.distributedsystems.projecs.sensornetwork.common.direction.Direction;
import dk.dtu.imm.distributedsystems.projecs.sensornetwork.common.packet.Packet;
import dk.dtu.imm.distributedsystems.projecs.sensornetwork.common.packet.PacketGroup;
import dk.dtu.imm.distributedsystems.projecs.sensornetwork.common.packet.PacketType;
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
		
	}
	
	/**
	 * Sends CMD packet over control channel
	 * 
	 * @param packet
	 */
	public synchronized void sendToSensor(Packet packet) {
		relatedSensor.setCurrentPacket(packet);
	}

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
					forwardPacketUnicast(packetFromSensor, Direction.LEFT, false);
					
					transceiverTimer = new TimerComponent(ackTimeout);
					transceiverTimer.start(); // create Timer and set it
				} else if (packetFromSensor.getType() == PacketType.ALM) { // ALARM_DATA
					forwardPacketUnicast(packetFromSensor, Direction.LEFT, true); // once again to all if no ACKs were received from any of the left
					// how to implement retransmit?
					transceiverTimer = new TimerComponent(ackTimeout);
					transceiverTimer.start(); // create Timer and set it
				} else {
					System.err.println("Sensor node - Transceiver: Wrong type of packet received on sensor channel");
				}
			}
			
			
			/*
			 *  fetch packet from left listener
			 *  packet group: CMD - push to sensor and forward multicast to right channel
			 */
			
			Packet packetFromLeftChannel = leftListner.getCurrentPacket();
	
			if (packetFromLeftChannel.getGroup() == PacketGroup.COMMAND) {
				relatedSensor.setCurrentPacket(packetFromLeftChannel);
				
				forwardPacketMulticast(packetFromLeftChannel, Direction.RIGHT);
			} else if (packetFromLeftChannel.getGroup() == PacketGroup.ACKNOWLEDGEMENT) { // ACK
				transceiverTimer.interrupt(); // reset Timer
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
				
				if (packetFromSensor.getType() == PacketType.DAT) { 
					forwardPacketUnicast(packetFromRightChannel, Direction.LEFT, false);
					
					transceiverTimer = new TimerComponent(ackTimeout);
					transceiverTimer.start(); // create Timer and set it
					
					// to musi by inny timer - to co ma byc kilka timerow na raz?
					// ma jeden czekac az drugi skonczy zanim zaczac?
				} else if (packetFromSensor.getType() == PacketType.ALM) { // ALARM_DATA
					forwardPacketUnicast(packetFromSensor, Direction.LEFT, true); // once again to all if no ACKs were received from any of the left
					// how to implement retransmit?
					
					transceiverTimer = new TimerComponent(ackTimeout);
					transceiverTimer.start(); // create Timer and set it
				} else {
					System.err.println("Sensor node - Transceiver: Wrong type of packet received on right channel");
				}
			}
		
		// get somehow the notification from Timer that time has passed
		}
		
	}
	
}
