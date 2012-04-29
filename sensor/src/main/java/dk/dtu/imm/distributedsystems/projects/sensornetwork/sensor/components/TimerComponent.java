package dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.components;

import java.util.concurrent.atomic.AtomicInteger;

public class TimerComponent extends AbstractComponent {

	AtomicInteger time;
	
	public TimerComponent(int periodInSeconds) {
		time = new AtomicInteger(periodInSeconds*1000);
	}
	
	@Override
	public void run() {
		try {
			while (true) {
				if (time.decrementAndGet() <= 0) {
					return; // Timeout or period expired, kill thread
				}

				Thread.sleep(1);
			}
		} catch (InterruptedException e) {	
			return; // Packet ACK received before timeout, kill thread
		}	
	}
	
}
