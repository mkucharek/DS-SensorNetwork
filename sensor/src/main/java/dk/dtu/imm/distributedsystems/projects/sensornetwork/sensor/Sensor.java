package dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor;


import java.io.FileNotFoundException;
import java.util.Arrays;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.components.SensorComponent;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.components.TransceiverComponent;

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
	
	int ackTimeout;
	
    public Sensor(	int id, int period,	int threshold,	
					int leftPort, int rightPort,
					int[] leftChannelIDs, String[] leftChannelIPs, int[] leftChannelPorts,
					int[] rightChannelIDs, String[] rightChannelIPs, int[] rightChannelPorts,
					int ackTimeout) {	
    	
    	this.transceiverComponent = new TransceiverComponent(this);
    	this.sensorComponent = new SensorComponent(this.transceiverComponent, period, threshold);
    	this.sensorComponent.start();
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
    	
    	this.ackTimeout = ackTimeout;
    }
	
	
	public TransceiverComponent getTransceiverComponent() {
		return transceiverComponent;
	}


	public SensorComponent getSensorComponent() {
		return sensorComponent;
	}

	public int getId() {
		return id;
	}


	public int getPeriod() {
		return period;
	}


	public int getThreshold() {
		return threshold;
	}


	public int getLeftPort() {
		return leftPort;
	}


	public int getRightPort() {
		return rightPort;
	}


	public int[] getLeftChannelIDs() {
		return leftChannelIDs;
	}


	public String[] getLeftChannelIPs() {
		return leftChannelIPs;
	}


	public int[] getLeftChannelPorts() {
		return leftChannelPorts;
	}


	public int[] getRightChannelIDs() {
		return rightChannelIDs;
	}


	public String[] getRightChannelIPs() {
		return rightChannelIPs;
	}


	public int[] getRightChannelPorts() {
		return rightChannelPorts;
	}


	public int getAckTimeout() {
		return ackTimeout;
	}

	public static void main(String[] args) {
		
		if(args.length != 1) {
			System.out.println("Please provide only one parameter - a suitable property file");
			return;
		}
		
		Sensor sensor = null;
		
		try {
			sensor = SensorUtility.getSensorInstance(args[1]);
		} catch (FileNotFoundException e) {
			System.out.println("Property file does not exist");
			return;
		}
		
		
		
    }
	
}
