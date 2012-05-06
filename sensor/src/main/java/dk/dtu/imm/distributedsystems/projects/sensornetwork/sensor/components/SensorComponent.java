package dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.components;

import java.util.Random;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.transceiver.AbstractTransceiver;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.Packet;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.PacketType;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.SensorUtility;

public class SensorComponent extends Thread {

	private int period;
	
	private int threshold;

	private AbstractTransceiver transceiver;

	public SensorComponent(AbstractTransceiver relatedTransceiver, int period,
			int threshold) {
		this.transceiver = relatedTransceiver;

		this.period = period;
		this.threshold = threshold;

		this.start();
	}

	public synchronized void setPeriod(int p) {
		this.period = p;
	}

	public synchronized void setThreshold(int t) {
		this.threshold = t;
	}
	
	public int getPeriod() {
		return period;
	}

	public int getThreshold() {
		return threshold;
	}

	public void run() {

		int measurement;
		Packet outPacket;

		while (true) {

			synchronized (this) {
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
				} else {
					outPacket = new Packet(PacketType.DAT,
							Integer.toString(measurement));
				}
			}

			transceiver.handlePacket(outPacket);
		}

	}
	
	private int getTemperature() {
		Random rng = new Random();

		int temperature = (int) (SensorUtility.MEASUREMENT_MEAN + SensorUtility.MEASUREMENT_STD
				* (2 * rng.nextDouble() - 1));

		return temperature;
	}

}
