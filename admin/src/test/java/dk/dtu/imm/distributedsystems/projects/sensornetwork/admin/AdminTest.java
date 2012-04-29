package dk.dtu.imm.distributedsystems.projects.sensornetwork.admin;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.GlobalUtility;

public class AdminTest {

	private Admin admin;
	
	@Before
	public void setup() {
		
		Properties properties = new Properties();
		try {
			properties = GlobalUtility.getProperties("admin.properties");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		admin = new Admin(	Integer.parseInt(properties.getProperty("PORT")), 
							properties.getProperty("SINK_IP"),
							Integer.parseInt(properties.getProperty("SINK_PORT")));
		
	}
	
	@Test
	public void testProperties() {
		assertEquals(admin.port, 20000);
		assertEquals(admin.sinkIP, "192.168.1.101");
		assertEquals(admin.sinkPort, 20101);
	}

}
