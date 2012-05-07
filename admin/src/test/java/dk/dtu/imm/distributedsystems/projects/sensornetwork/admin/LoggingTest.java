package dk.dtu.imm.distributedsystems.projects.sensornetwork.admin;

import org.junit.Test;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.logging.LoggingUtility;

public class LoggingTest {

	@Test
	public void testLowLevelLogging() {
		
		Object[] parameters = {"msg", "pck", "VAL"};
		
		for (int i=1; i<10; i++) {
			LoggingUtility.logMessage(parameters);
		}
	}
	
	// TODO Tests using LogChecker
	
}
