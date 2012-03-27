package dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.components;

import java.util.Random;

public class SensorComponent extends AbstractComponent {

	
	public void run() {
		generateMeasurement();
	}
	
	private int generateMeasurement() {
		int mean = 25;
		int std = 5;
		Random rng = new Random();
		
		int measurement = mean + std * rng.nextInt();
		
		return measurement;
	}
}
