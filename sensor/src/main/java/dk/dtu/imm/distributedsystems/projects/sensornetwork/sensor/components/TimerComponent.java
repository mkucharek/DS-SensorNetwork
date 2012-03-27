package dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.components;



public class TimerComponent extends AbstractComponent {

	int time;
	boolean set;
	
	public TimerComponent() {
		time = 0;
		set = false;
	}
	
	void setTimer() {
		set = true;
	}
	
	void resetTimer() {
		set = false;
		time = 0;
	}
	
	void sendTimeout() {
		
	}
	
	public void run() {
		
	}
	
}
