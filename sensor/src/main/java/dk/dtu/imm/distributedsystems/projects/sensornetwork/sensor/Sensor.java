package dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor;


import java.io.FileNotFoundException;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.channels.Channel;
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
	int senderPort;
	
	/** The left channels. */
	private Channel[] leftChannels;
	
	/** The right channels. */
	private Channel[] rightChannels;
	
	int ackTimeout;
	
    public Sensor(	int id, int period,	int threshold,	
					int leftPort, int rightPort,
					int[] leftChannelIDs, String[] leftChannelIPs, int[] leftChannelPorts,
					int[] rightChannelIDs, String[] rightChannelIPs, int[] rightChannelPorts,
					int ackTimeout) {	
    	
    	this.id = id;
    	this.period = period;
    	this.threshold = threshold;
    	
    	this.leftPort = leftPort;
    	this.rightPort = rightPort;
    	this.senderPort = 8888;
    	
    	this.leftChannels = new Channel[leftChannelIDs.length];
    	
    	for(int i=0; i<leftChannelIDs.length; ++i) {
    		this.leftChannels[i] = new Channel(String.valueOf(leftChannelIDs[i]), 
    				leftChannelIPs[i], 
    				leftChannelPorts[i]);
    	}
    	
    	this.rightChannels = new Channel[rightChannelIDs.length];
    	
    	for(int i=0; i<rightChannelIDs.length; ++i) {
    		this.rightChannels[i] = new Channel(String.valueOf(rightChannelIDs[i]), 
    				rightChannelIPs[i], 
    				rightChannelPorts[i]);
    	}
    	
    	this.ackTimeout = ackTimeout;
    	
    	this.transceiverComponent = new TransceiverComponent(this);
    	this.sensorComponent = new SensorComponent(this.transceiverComponent, period, threshold);
    	this.sensorComponent.start();
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
	
	public int getSenderPort() {
		return senderPort;
	}


	public Channel[] getLeftChannels() {
		return leftChannels;
	}


	public Channel[] getRightChannels() {
		return rightChannels;
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
