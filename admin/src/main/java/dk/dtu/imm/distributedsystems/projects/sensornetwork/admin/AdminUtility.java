package dk.dtu.imm.distributedsystems.projects.sensornetwork.admin;

import dk.dtu.imm.distributedsystems.projecs.sensornetwork.common.logging.LoggingUtility;

public class AdminUtility {
	
	public static void logMessage(String msgType, String pckType, String value) {
		LoggingUtility.logMessage(new Object[]{msgType, pckType, value});
	}

}
