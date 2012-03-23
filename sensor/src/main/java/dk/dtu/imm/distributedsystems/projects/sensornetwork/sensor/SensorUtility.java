package dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor;

import dk.dtu.imm.distributedsystems.projecs.sensornetwork.common.logging.LoggingUtility;

public class SensorUtility {
	
	public static void logMessage(String id, String remoteId, String msgType, String pckType, String value) {
		LoggingUtility.logMessage(new Object[]{id, remoteId, msgType, pckType, value});
	}

}
