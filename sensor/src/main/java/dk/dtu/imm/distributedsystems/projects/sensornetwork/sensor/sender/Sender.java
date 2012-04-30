package dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.sender;

import java.util.LinkedList;
import java.util.Queue;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.Packet;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.PacketGroup;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.PacketType;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.Sensor;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.components.TimerComponent;

public class Sender extends Thread {

	Sensor sensor;
	
	public int successfulSends;
	
	TimerComponent timer;
	
	Queue<Packet> buffer;
	
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
	
	@Override
	public void run() {
		
		while (true) {
			if (!buffer.isEmpty()) {
				
				Packet packet = buffer.poll();
				
				if (packet.getGroup() == PacketGroup.SENSOR_DATA) {
					
					if (packet.getType() == PacketType.DAT) { 
						sendUnicastLeft(packet);
						
					} else if (packet.getType() == PacketType.ALM) { // ALARM_DATA
						
						for(int i=0; i < 2; ++i) {
							if(sendUnicastLeft(packet)) {
								break;
							}
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
	
	private boolean sendUnicastLeft(Packet packet) {
		
		for (int channel = 0; channel < sensor.getLeftChannelIDs().length; channel++) {
			
			// send Packet to left channel - UDP Connection
			
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
		
		for (int channel = 0; channel < sensor.getRightChannelIDs().length; channel++) {
			// send Packet to right channel - UDP Connection
		}

	}
}
