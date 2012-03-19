package dk.dtu.imm.distributedsystems.projects.sensornetwork.admin;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class AdminTest {

	private Admin admin;
	
	@Before
	public void setup() {
		admin = new Admin("C:\\Users\\Wojtek\\Documents\\Git Repositories\\ds-sensornetwork\\admin\\src\\test\\java\\dk\\dtu\\imm\\distributedsystems\\projects\\sensornetwork\\admin\\admin.properties");
	}
	
	@Test
	public void test() {

		assertEquals(admin.port, 20000);
		assertEquals(admin.sinkIP, "192.168.1.101");
		assertEquals(admin.sinkPort, 20101);
	}

}
