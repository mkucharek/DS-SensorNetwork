package dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor;


import java.util.Arrays;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.components.SensorComponent;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.components.TransceiverComponent;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.listener.LeftUdpPortListener;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.listener.RightUdpPortListener;

/**
 * Sensor Node for Sensor Network
 *
 */
public class Sensor 
{
	TransceiverComponent transceiverComponent;
	SensorComponent sensorComponent;
	
	
	int id;
	int period;
	int threshold;
	
	int leftPort;
	int rightPort;
	
	int[] leftChannelIDs;
	String[] leftChannelIPs;
	int[] leftChannelPorts;

	int[] rightChannelIDs;
	String[] rightChannelIPs;
	int[] rightChannelPorts;
	
	TransreceiverComponent transceiver;
	SensorComponent sensor;
	
	TimerComponent sensorTimer;
	TimerComponent transceiverTimer;
	
    public Sensor(	int id, int period,	int threshold,	
					int leftPort, int rightPort,
					int[] leftChannelIDs, String[] leftChannelIPs, int[] leftChannelPorts,
					int[] rightChannelIDs, String[] rightChannelIPs, int[] rightChannelPorts) {	
    	
    	this.transceiverComponent = new TransceiverComponent(this);
    	this.sensorComponent = new SensorComponent(this.transceiverComponent);
    	
    	this.id = id;
    	this.period = period;
    	this.threshold = threshold;
    	
    	this.leftPort = leftPort;
    	this.rightPort = rightPort;
    	
    	this.leftChannelIDs = Arrays.copyOf(leftChannelIDs, leftChannelIDs.length);
    	this.leftChannelIPs = Arrays.copyOf(leftChannelIPs, leftChannelIPs.length);
    	this.leftChannelPorts = Arrays.copyOf(leftChannelPorts, leftChannelPorts.length);

    	this.rightChannelIDs = Arrays.copyOf(rightChannelIDs, rightChannelIDs.length);
    	this.rightChannelIPs = Arrays.copyOf(rightChannelIPs, rightChannelIPs.length);
    	this.rightChannelPorts = Arrays.copyOf(rightChannelPorts,  rightChannelPorts.length);
    }
	
	public static void main(String[] args)
    {
		
		System.out.println("Hello Sensor!");
		
    }

	public SensorComponent getSensorComponent() {
		return sensorComponent;
	}
	
	
}
