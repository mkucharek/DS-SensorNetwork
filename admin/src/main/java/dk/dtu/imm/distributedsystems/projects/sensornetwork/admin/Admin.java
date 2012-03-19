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
	
    public Admin(String s) {
		Properties properties = new Properties();
		try {
		    properties.load(new FileInputStream(s));
		} catch (IOException e) {
		}
		
		port = Integer.parseInt(properties.getProperty("PORT"));
		sinkIP = properties.getProperty("SINK_IP");
		sinkPort = Integer.parseInt(properties.getProperty("SINK_PORT"));
    }
    
	public static void main(String[] args)
    {
	
    }
}
