package dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor;

import java.util.Arrays;

/**
 * Sensor Node for Sensor Network
 *
 */
public class Sensor 
{
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
	
    public Sensor(	int id, int period,	int threshold,	
					int leftPort, int rightPort,
					int[] leftChannelIDs, String[] leftChannelIPs, int[] leftChannelPorts,
					int[] rightChannelIDs, String[] rightChannelIPs, int[] rightChannelPorts) {	
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

    }
}
