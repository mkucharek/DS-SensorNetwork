package dk.dtu.distributedsystems.projects.sensornetwork;

import dk.dtu.distributedsystems.projects.sensornetwork.nodes.admin.AdminNode;
import dk.dtu.distributedsystems.projects.sensornetwork.nodes.sensor.SensorNode;
import dk.dtu.distributedsystems.projects.sensornetwork.nodes.sink.SinkNode;

/**
 * Main project class
 *
 */
public class User 
{
	private AdminNode adminNode;
	
	private SinkNode sinkNode;
	
	private SensorNode[] sensorNodeArray;
	
	
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }
}
