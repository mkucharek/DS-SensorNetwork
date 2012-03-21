package dk.dtu.imm.distributedsystems.projects.sensornetwork.admin;

import java.io.*;
import java.util.Properties;

/**
 * Admin Node for Sensor Network
 *
 */
public class Admin 
{
	int port;
    String sinkIP;
    int sinkPort;
	
    public Admin(int pport, String psinkIP, int psinkPort) {	
		port = pport;
		sinkIP = psinkIP;
		sinkPort = psinkPort;
    }
    
	public static void main(String[] args)
    {
	
    }
}
