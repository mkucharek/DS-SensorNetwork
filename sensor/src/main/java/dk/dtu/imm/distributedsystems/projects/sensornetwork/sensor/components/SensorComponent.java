package dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.components;

import java.util.Random;

import dk.dtu.imm.distributedsystems.projecs.sensornetwork.common.packet.Packet;
import dk.dtu.imm.distributedsystems.projecs.sensornetwork.common.packet.PacketType;

public class SensorComponent extends AbstractComponent {

	int period;
	int threshold;
	
	Packet currentOutPacket;
	Packet currentInPacket;

	private TransceiverComponent relatedTransceiver;
	private TimerComponent sensorTimer;
	
	public SensorComponent(TransceiverComponent relatedTransceiver, int period, int threshold) {
		this.relatedTransceiver = relatedTransceiver;
	
		this.period = period;
		this.threshold = threshold;
	}
	
	private int getTemperature() {
		int mean = 25;
		int std = 5;
		Random rng = new Random();
		
		int temperature = mean + std * rng.nextInt();
		
		return temperature;
	}
	
	public Packet getCurrentOutPacket() {
		return currentOutPacket;
	}
	
	public void setCurrentPacket(Packet currentInPacket) {
		this.currentInPacket = currentInPacket;
	}

//	void setPeriod(int p) {
//		period = p;
//	}
//	
//	void setThreshold(int t) {
//		period = t;
//	}
	
	public void run() {
		
		try {
			int measurement;
			
			while (true) {
				
				/*
				 * TODO if new CMD packet arrived to sensor - flag changed?
				 * read the contents of packet and change period or threshold value, respectively
				 */
				
				if (currentInPacket.getType() == PacketType.PRD) {
					period = Integer.parseInt(currentInPacket.getValue());
				} else if (currentInPacket.getType() == PacketType.THR) {
					threshold = Integer.parseInt(currentInPacket.getValue());
				} else {
					System.err.println("Sensor node - Sensor: Wrong type of packet received on sensor channel");
				}
						
				/*
				 * TODO if Timer thread notified that waiting period is over ( or ask timer for time in every iteration)
				 * measure and send data
				 */
				
				measurement = getTemperature();
				
				if (measurement > threshold) {
					currentOutPacket = new Packet(PacketType.ALM, Integer.toString(measurement));
				}
				else {
					currentOutPacket = new Packet(PacketType.DAT, Integer.toString(measurement));
				}
				
				

			}

		} catch (Exception e) {
			
		}
		
	}

	
}
