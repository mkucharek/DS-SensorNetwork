package dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.components;

import java.util.Random;

import dk.dtu.imm.distributedsystems.projecs.sensornetwork.common.packet.Packet;
import dk.dtu.imm.distributedsystems.projecs.sensornetwork.common.packet.PacketType;

public class SensorComponent extends AbstractComponent {

	int period;
	int threshold;
	
	Packet currentOutPacket;
	Packet currentInPacket;
	
	boolean newInPacketArrived;

	private TransceiverComponent relatedTransceiver;
	private TimerComponent sensorTimer;
	
	public SensorComponent(TransceiverComponent relatedTransceiver, int period, int threshold) {
		this.relatedTransceiver = relatedTransceiver;
	
		this.period = period;
		this.threshold = threshold;
		
		this.sensorTimer = new TimerComponent(period);
		sensorTimer.start();
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
	
	public synchronized void setCurrentPacket(Packet currentInPacket) {
		this.currentInPacket = currentInPacket;
		this.newInPacketArrived = true;
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
				 * if new CMD packet arrived to sensor - for now: flag changed
				 * read the contents of packet and change period or threshold value, respectively
				 * 
				 * mimics TCP for now, packets + flag
				 */
				
				if (newInPacketArrived) {
					newInPacketArrived = false;
					
					if (currentInPacket.getType() == PacketType.PRD) {
						period = Integer.parseInt(currentInPacket.getValue());
					} else if (currentInPacket.getType() == PacketType.THR) {
						threshold = Integer.parseInt(currentInPacket.getValue());
					} else {
						System.err.println("Sensor node - Sensor: Wrong type of packet received on control channel");
					}
					
				}
				
				/*
				 * if Timer is finished, waiting period is over (check if timer is alive in every iteration)
				 * measure and send data
				 * 
				 * Is it energy-efficient to create and start a new thread every time period is over?
				 * For small number of packets received it is better, because 
				 * 
				 * Maybe interrupts are a preferred solution?
				 * 
				 * Or Timer thread should be created once and send information about timeout every time period is over and sleep?
				 * Preferred solution for counting period for sensor as it has to constantly count period.
				 */
				
				if(!(sensorTimer.isAlive())) {
				
					measurement = getTemperature();
					
					if (measurement > threshold) {
						currentOutPacket = new Packet(PacketType.ALM, Integer.toString(measurement));
					}
					else {
						currentOutPacket = new Packet(PacketType.DAT, Integer.toString(measurement));
					}
					
					sensorTimer = new TimerComponent(period);
					sensorTimer.start();
				}
			}

		} catch (Exception e) {
			
		}
		
	}

	
}
