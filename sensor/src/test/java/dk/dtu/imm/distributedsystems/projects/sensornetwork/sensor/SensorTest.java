package dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import dk.dtu.imm.distributedsystems.projecs.sensornetwork.common.GlobalUtility;

public class SensorTest {

	private Sensor sensor11;
	private Sensor sensor21;
	
	@Before
	public void setupSensor11() throws FileNotFoundException {
		
		Properties properties = GlobalUtility.getProperties("sensor11.properties");


		sensor11 = new Sensor(	Integer.parseInt(properties.getProperty("ID")),
								Integer.parseInt(properties.getProperty("PERIOD")),
								Integer.parseInt(properties.getProperty("THRESHOLD")),
								Integer.parseInt(properties.getProperty("LEFT_PORT")),
								Integer.parseInt(properties.getProperty("RIGHT_PORT")),
								convertStringArraytoIntArray(properties.getProperty("LEFT_CHANNEL_ID").split(";")),
								properties.getProperty("LEFT_CHANNEL_IP").split(";"),
								convertStringArraytoIntArray(properties.getProperty("LEFT_CHANNEL_PORT").split(";")),
								convertStringArraytoIntArray(properties.getProperty("RIGHT_CHANNEL_ID").split(";")),
								properties.getProperty("RIGHT_CHANNEL_IP").split(";"),
								convertStringArraytoIntArray(properties.getProperty("RIGHT_CHANNEL_PORT").split(";")));
	}
	
	@Before
	public void setupSensor21() throws FileNotFoundException {
		
		Properties properties = GlobalUtility.getProperties("sensor21.properties");	
		
		sensor21 = new Sensor(	Integer.parseInt(properties.getProperty("ID")),
								Integer.parseInt(properties.getProperty("PERIOD")),
								Integer.parseInt(properties.getProperty("THRESHOLD")),
								Integer.parseInt(properties.getProperty("LEFT_PORT")),
								Integer.parseInt(properties.getProperty("RIGHT_PORT")),
								convertStringArraytoIntArray(properties.getProperty("LEFT_CHANNEL_ID").split(";")),
								properties.getProperty("LEFT_CHANNEL_IP").split(";"),
								convertStringArraytoIntArray(properties.getProperty("LEFT_CHANNEL_PORT").split(";")),
								convertStringArraytoIntArray(properties.getProperty("RIGHT_CHANNEL_ID").split(";")),
								properties.getProperty("RIGHT_CHANNEL_IP").split(";"),
								convertStringArraytoIntArray(properties.getProperty("RIGHT_CHANNEL_PORT").split(";")));	
	}
	
	@Test
	public void testPropertiesSensor11() {
		assertEquals(sensor11.id, 11);
		assertEquals(sensor11.period, 10);
		assertEquals(sensor11.threshold, 40);
		
		assertEquals(sensor11.leftPort, 20111);
		assertEquals(sensor11.rightPort, 21111);
		
		assertEquals(sensor11.leftChannelIDs[0], 0);
		assertEquals(sensor11.leftChannelIPs[0], "192.168.1.100");
		assertEquals(sensor11.leftChannelPorts[0], 21100);
		
		assertEquals(sensor11.rightChannelIDs[0], 21);
		assertEquals(sensor11.rightChannelIDs[1], 22);
		assertEquals(sensor11.rightChannelIDs[2], 23);
		
		assertEquals(sensor11.rightChannelIPs[0], "192.168.1.121");
		assertEquals(sensor11.rightChannelIPs[1], "192.168.1.122");
		assertEquals(sensor11.rightChannelIPs[2], "192.168.1.123");
		
		assertEquals(sensor11.rightChannelPorts[0], 20121);
		assertEquals(sensor11.rightChannelPorts[1], 20122);
		assertEquals(sensor11.rightChannelPorts[2], 20123);
	}
	
	@Test
	public void testPropertiesSensor21() {
		assertEquals(sensor21.id, 21);
		assertEquals(sensor21.period, 10);
		assertEquals(sensor21.threshold, 40);
		
		assertEquals(sensor21.leftPort, 20121);
		assertEquals(sensor21.rightPort, 21121);
		
		assertEquals(sensor21.leftChannelIDs[0], 11);
		assertEquals(sensor21.leftChannelIDs[1], 12);
		assertEquals(sensor21.leftChannelIDs[2], 13);
		
		assertEquals(sensor21.leftChannelIPs[0], "192.168.1.111");
		assertEquals(sensor21.leftChannelIPs[1], "192.168.1.112");
		assertEquals(sensor21.leftChannelIPs[2], "192.168.1.113");
		
		assertEquals(sensor21.leftChannelPorts[0], 21111);
		assertEquals(sensor21.leftChannelPorts[1], 21112);
		assertEquals(sensor21.leftChannelPorts[2], 21113);
		
		assertEquals(sensor21.rightChannelIDs[0], -1);

		assertEquals(sensor21.rightChannelIPs[0], "");
		
		assertEquals(sensor21.rightChannelPorts[0], -1);

	}
	
	@Test
	public void testConnections11to21() {
		assertEquals(sensor11.rightChannelIDs[0], sensor21.id);
		assertEquals(sensor11.rightChannelIPs[0], "192.168.1.121");
		assertEquals(sensor11.rightChannelPorts[0], sensor21.leftPort);
		
		assertEquals(sensor21.leftChannelIDs[0], sensor11.id);
		assertEquals(sensor21.leftChannelIPs[0], "192.168.1.111");
		assertEquals(sensor21.leftChannelPorts[0], sensor11.rightPort);
	}
	
	private int[] convertStringArraytoIntArray(String[] sarray) {
		int intarray[] = new int[sarray.length];
		
		if (sarray[0].length() == 0) {
			intarray[0] = -1;
		} else {
			for (int i = 0; i < sarray.length; i++) {
				intarray[i] = Integer.parseInt(sarray[i]);
			}
		}
		
		return intarray;
	}

}
