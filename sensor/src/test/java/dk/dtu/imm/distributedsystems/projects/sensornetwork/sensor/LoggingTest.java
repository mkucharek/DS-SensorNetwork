package dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor;

import org.junit.Test;

import dk.dtu.imm.distributedsystems.projecs.sensornetwork.common.logging.LoggingUtility;

public class LoggingTest {

	@Test
	public void testLogging() {
		
		Object[] parameters = {"logger","id", "remo", "mes", "pac", "value"};
		
		for (int i=1; i<10; i++) {
			LoggingUtility.getInstance().logMessage(parameters);
		}
	}

}
