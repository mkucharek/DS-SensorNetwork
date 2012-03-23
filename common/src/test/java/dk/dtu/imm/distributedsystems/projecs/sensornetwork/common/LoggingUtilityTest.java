package dk.dtu.imm.distributedsystems.projecs.sensornetwork.common;

import static org.junit.Assert.*;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dk.dtu.imm.distributedsystems.projecs.sensornetwork.common.logging.LoggingUtility;

public class LoggingUtilityTest {
	
	Logger logger = LoggerFactory.getLogger(LoggingUtilityTest.class);

//	@Test
//	public void test() {
//	
//		LoggingUtility.getInstance().logMessage(logger, "id", "remo", "mes", "pac", "value");
//		LoggingUtility.getInstance().logMessage(logger, "id", "remo", "mes", "pac", "value");
//		LoggingUtility.getInstance().logMessage(logger, "id", "remo", "mes", "pac", "value");
//		LoggingUtility.getInstance().logMessage(logger, "id", "remo", "mes", "pac", "value");
//		LoggingUtility.getInstance().logMessage(logger, "id", "remo", "mes", "pac", "value");
//
//		
//	}
	
	@Test
	public void test2() {
		
		Object[] parameters = {"logger","id", "remo", "mes", "pac", "value"};
		
		for (int i=1; i<10; i++) {
			LoggingUtility.getInstance().logMessage(logger,parameters);
		}
	}
	
	@Test
	public void test3() {
		
		Object[] parameters = {"logger","id", "remo", "mes", "pac", "value"};
		
		for (int i=1; i<10; i++) {
			LoggingUtility.getInstance().logMessage(parameters);
		}
	}

}
