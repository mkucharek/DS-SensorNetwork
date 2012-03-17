package dk.dtu.distributedsystems.projects.sensornetwork;

import dk.dtu.distributedsystems.projects.sensornetwork.nodes.admin.Admin;
import dk.dtu.distributedsystems.projects.sensornetwork.nodes.sensor.Sensor;
import dk.dtu.distributedsystems.projects.sensornetwork.nodes.sink.Sink;

/**
 * Main project class
 *
 */
public class User 
{
	private Admin adminNode;
	
	private Sink sinkNode;
	
	private Sensor[] sensorNodeArray;
	
	
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }
}
