package dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.components;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.common;

import java.util.Random;

public class SensorComponent extends AbstractComponent {

	int period;
	int threshold;

	private TransceiverComponent relatedTransceiver;
	
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
	
	void setPeriod(int p) {
		period = p;
	}
	
	void setThreshold(int t) {
		period = t;
	}
	
	public void run() {
		int measurement;
		String reportType;
		
		for (;;) {
			
			// listen for THR or PRD updates
			// 
//			if ("THR") {
//				setPeriod();
//			}
//			else if ("PRD") {
//				setTreshold()
//			}
			// if period is over
			// measure and send the data
			
			measurement = getTemperature();
	
			if (measurement > threshold) {
				reportType = "ALM";
			}
			else {
				reportType = "DAT";
			}
			
			// build packet
			
			// ask timer for time
		}

	}
	
	
}
