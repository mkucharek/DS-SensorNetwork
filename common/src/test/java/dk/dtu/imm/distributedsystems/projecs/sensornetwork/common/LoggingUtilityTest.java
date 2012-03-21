package dk.dtu.imm.distributedsystems.projecs.sensornetwork.common;

import static org.junit.Assert.*;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dk.dtu.imm.distributedsystems.projecs.sensornetwork.common.logging.LoggingUtility;

public class LoggingUtilityTest {
	
	Logger logger = LoggerFactory.getLogger(LoggingUtilityTest.class);

	@Test
	public void test() {
		
		logger.debug(LoggingUtility.getInstance().getLogMessage("messageType", "packetType", "valu"));
		logger.debug(LoggingUtility.getInstance().getLogMessage("messageTyp", "packtType", "valuesss"));
		logger.debug(LoggingUtility.getInstance().getLogMessage("messageTe", "pactType", "alue"));
		logger.debug(LoggingUtility.getInstance().getLogMessage("messaype", "petType", "vue"));
		logger.debug(LoggingUtility.getInstance().getLogMessage("mgeType", "ckType", "value"));
	
	}

}
