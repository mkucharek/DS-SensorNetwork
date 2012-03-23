package dk.dtu.imm.distributedsystems.projects.sensornetwork.sink;

import dk.dtu.imm.distributedsystems.projecs.sensornetwork.common.logging.LoggingUtility;

public class SinkUtility {
	
	public static void logMessage(String remoteId, String msgType, String pckType, String value) {
		LoggingUtility.logMessage(new Object[]{remoteId, msgType, pckType, value});
	}

}
