package dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.components;

import java.util.concurrent.atomic.AtomicInteger;

public class TimerComponent extends AbstractComponent {

	AtomicInteger time;
	
	public TimerComponent(int periodInSeconds) {
		time = new AtomicInteger(periodInSeconds*1000);
	}
	
	public void run() {
		try {
			while (true) {
				if (time.decrementAndGet() <= 0) {
					// notify about timeout
					return;
				}

				Thread.sleep(1);
			}
		} catch (InterruptedException e) {	
			// finish counting and kill thread
			
			return;
		}
	}
	
}
