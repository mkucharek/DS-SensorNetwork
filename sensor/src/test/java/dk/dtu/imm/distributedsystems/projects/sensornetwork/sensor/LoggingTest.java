package dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor;

import org.junit.Test;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.logging.LoggingUtility;

public class LoggingTest {

	@Test
	public void testLowLevelLogging() {
		
		Object[] parameters = {"id", "rId", "mes", "pac", "value"};
		
		for (int i=1; i<10; i++) {
			LoggingUtility.logMessage(parameters);
		}
	}
	
	@Test
	public void testAdminLogging() {
		
		for (int i=1; i<10; i++) {
			SensorUtility.logMessage("id", "rId", "msg", "pck", "VAL");
		}
	}

}
