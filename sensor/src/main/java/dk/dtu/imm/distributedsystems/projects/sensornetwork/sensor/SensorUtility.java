package dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor;

import java.io.FileNotFoundException;
import java.util.Properties;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.GlobalUtility;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.logging.LoggingUtility;

public class SensorUtility {
	
	public static final int MEASUREMENT_MEAN = 25;
	
	public static final int MEASUREMENT_STD = 5;
	
	public static void logMessage(String id, String remoteId, String msgType, String pckType, String value) {
		LoggingUtility.logMessage(new Object[]{id, remoteId, msgType, pckType, value});
	}
	
	public static Sensor getSensorInstance(String propertyFilePath) throws FileNotFoundException {
		
		Properties properties = GlobalUtility.getProperties(propertyFilePath);

		// TODO: check if the properties exist
		
		return new Sensor(Integer.parseInt(properties.getProperty("ID")),
				Integer.parseInt(properties.getProperty("PERIOD")),
				Integer.parseInt(properties.getProperty("THRESHOLD")),
				Integer.parseInt(properties.getProperty("LEFT_PORT")),
				Integer.parseInt(properties.getProperty("RIGHT_PORT")),
				GlobalUtility.convertStringArraytoIntArray(properties
						.getProperty("LEFT_CHANNEL_ID").split(";")), properties
						.getProperty("LEFT_CHANNEL_IP").split(";"),
				GlobalUtility.convertStringArraytoIntArray(properties
						.getProperty("LEFT_CHANNEL_PORT").split(";")),
				GlobalUtility.convertStringArraytoIntArray(properties
						.getProperty("RIGHT_CHANNEL_ID").split(";")),
				properties.getProperty("RIGHT_CHANNEL_IP").split(";"),
				GlobalUtility.convertStringArraytoIntArray(properties
						.getProperty("RIGHT_CHANNEL_PORT").split(";")), GlobalUtility.ACK_TIMEOUT_MS);
	}

}
