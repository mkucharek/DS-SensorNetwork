package dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.components;

import java.util.Random;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.Packet;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.PacketType;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.SensorUtility;

public class SensorComponent extends AbstractComponent {

	int period;
	int threshold;

	private Transceiver relatedTransceiver;
	
	public SensorComponent(Transceiver relatedTransceiver, int period, int threshold) {
		this.relatedTransceiver = relatedTransceiver;
	
		this.period = period;
		this.threshold = threshold;
		
	}
	
	private int getTemperature() {
		Random rng = new Random();
		
		int temperature = (int) (SensorUtility.MEASUREMENT_MEAN + SensorUtility.MEASUREMENT_STD * (2 * rng.nextDouble()-1));
		
		return temperature;
	}

	public synchronized void setPeriod(int p) {
		this.period = p;
	}
	
	public synchronized void setThreshold(int t) {
		this.threshold = t;
	}
	
	public void run() {
		
		int measurement;
		Packet outPacket;
		
		while (true) {
			
			synchronized(this) {
				try {
					this.wait(period);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			 measurement = getTemperature();
			
			 synchronized (this) {
				 if (measurement > threshold) {
					 outPacket = new Packet(PacketType.ALM,
					 Integer.toString(measurement));
				 }
				 else {
					 outPacket = new Packet(PacketType.DAT,
					 Integer.toString(measurement));
				 }
			 }
			
			 this.relatedTransceiver.handlePacket(outPacket);
		}
		
	}
	
}
