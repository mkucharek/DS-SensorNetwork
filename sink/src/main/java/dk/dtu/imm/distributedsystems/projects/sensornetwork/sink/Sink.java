package dk.dtu.imm.distributedsystems.projects.sensornetwork.sink;

import java.util.Arrays;

/**
 * Sink Node for Sensor Network
 *
 */
public class Sink 
{
	int id;
	
	int leftPort;
	int rightPort;
	
	String adminIP;
	int adminPort;
	
	int[] rightChannelIDs;
	String[] rightChannelIPs;
	int[] rightChannelPorts;
	
	public Sink(int id, int leftPort, int rightPort,
				String adminIP, int adminPort,
				int[] rightChannelIDs, String[] rightChannelIPs, int[] rightChannelPorts) {	
		this.id = id;
		this.leftPort = leftPort;
		this.rightPort = rightPort;
		
		this.adminIP = adminIP;
		this.adminPort = adminPort;
	
		this.rightChannelIDs = Arrays.copyOf(rightChannelIDs, rightChannelIDs.length);
		this.rightChannelIPs = Arrays.copyOf(rightChannelIPs, rightChannelIPs.length);
		this.rightChannelPorts = Arrays.copyOf(rightChannelPorts, rightChannelPorts.length);
}
	
	public static void main(String[] args)
    {

    }
}
