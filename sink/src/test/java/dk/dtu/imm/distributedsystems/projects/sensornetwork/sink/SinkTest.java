//package dk.dtu.imm.distributedsystems.projects.sensornetwork.sink;
//
//import static org.junit.Assert.assertEquals;
//
//import java.io.FileNotFoundException;
//import java.util.Properties;
//
//import junit.framework.Assert;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.GlobalUtility;
//
//public class SinkTest {
//
//	private Sink sink;
//	
//	@Before
//	public void setup() throws FileNotFoundException {
//		
//		Properties properties = null;
//		try {
//			properties = GlobalUtility.getPropertiesFromClasspath("sink.properties");
//		} catch (Exception e) {
//			Assert.fail();
//		}
//
//		sink = new Sink(properties.getProperty("ID"),
//						Integer.parseInt(properties.getProperty("LEFT_PORT")),
//						Integer.parseInt(properties.getProperty("RIGHT_PORT")),
//						properties.getProperty("ADMIN_IP"),
//						Integer.parseInt(properties.getProperty("ADMIN_PORT")),
//						convertStringArraytoIntArray(properties.getProperty("RIGHT_CHANNEL_ID").split(";")),
//						properties.getProperty("RIGHT_CHANNEL_IP").split(";"),
//						convertStringArraytoIntArray(properties.getProperty("RIGHT_CHANNEL_PORT").split(";")));
//	}
//	
//	@Test
//	public void testProperties() {
//		assertEquals(sink.getId(), "0");
//		assertEquals(sink.leftPort, 20100);
//		assertEquals(sink.rightPort, 21100);
//		
//		assertEquals(sink.adminIP, "192.168.1.1");
//		assertEquals(sink.adminPort, 20000);
//		
//		assertEquals(sink.rightChannelIDs[0], 11);
//		assertEquals(sink.rightChannelIDs[1], 12);
//		assertEquals(sink.rightChannelIDs[2], 13);
//		
//		assertEquals(sink.rightChannelIPs[0], "192.168.1.111");
//		assertEquals(sink.rightChannelIPs[1], "192.168.1.112");
//		assertEquals(sink.rightChannelIPs[2], "192.168.1.113");
//		
//		assertEquals(sink.rightChannelPorts[0], 20111);
//		assertEquals(sink.rightChannelPorts[1], 20112);
//		assertEquals(sink.rightChannelPorts[2], 20113);
//	}
//
//	private int[] convertStringArraytoIntArray(String[] sarray) {
//		int intarray[] = new int[sarray.length];
//		
//		if (sarray[0].length() == 0) {
//			intarray[0] = -1;
//		} else {
//			for (int i = 0; i < sarray.length; i++) {
//				intarray[i] = Integer.parseInt(sarray[i]);
//			}
//		}
//		
//		return intarray;
//	}
//
//}
