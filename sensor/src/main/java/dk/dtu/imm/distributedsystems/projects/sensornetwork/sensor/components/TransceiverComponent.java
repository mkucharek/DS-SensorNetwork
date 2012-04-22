package dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.components;

import dk.dtu.imm.distributedsystems.projecs.sensornetwork.common.direction.Direction;
import dk.dtu.imm.distributedsystems.projecs.sensornetwork.common.packet.Packet;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.Sensor;


public class TransceiverComponent extends AbstractComponent {

	private Sensor sensor;
	
	public TransceiverComponent(Sensor sensor) {
		this.sensor = sensor;
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
	public synchronized void forwardPacketUnicast(Packet packet, Direction direction) {
		
	}
	
	public synchronized void sendToSensor(Packet packet) {
		
	}
	
}
