package dk.dtu.imm.distributedsystems.projecs.sensornetwork.common;

import org.junit.Test;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.logging.LoggingUtility;

public class LoggingUtilityTest {
	
	@Test
	public void test3() {
		
		Object[] parameters = {"logger","id", "remo", "mes", "pac", "value"};
		
		for (int i=1; i<10; i++) {
			LoggingUtility.logMessage(parameters);
		}
	}

}
