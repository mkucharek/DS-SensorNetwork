package dk.dtu.imm.distributedsystems.projects.sensornetwork.admin;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

public class AdminTest {

	private Admin admin;
	
	@Before
	public void setup() {
		
		URI uri;
		Properties properties = new Properties();
		
		try {
			uri = new URI(this.getClass().getResource("/admin.properties")
					.toString());

			properties.load(new FileInputStream(uri.getPath()));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Configuration file not available.");
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
