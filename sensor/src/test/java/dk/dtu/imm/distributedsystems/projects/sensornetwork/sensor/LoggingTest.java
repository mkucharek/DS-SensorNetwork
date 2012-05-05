package dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor;

import junit.framework.Assert;

import org.junit.Test;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.LogChecker;
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
	public void testSensorLogging() {
		
		for (int i=1; i<10; i++) {
			Assert.assertTrue(LogChecker.checkLine(SensorUtility.getLogMessage(String.valueOf(i), String.valueOf(i+10), "RCV", "DAT", String.valueOf(i+5)), LogChecker.SENSOR));
		}
	}

}
