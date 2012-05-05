package dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.GlobalUtility;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.channels.Channel;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.exceptions.NodeInitializationException;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.logging.LoggingUtility;

public class SensorUtility {
	
	protected final static Logger logger = LoggerFactory.getLogger(SensorUtility.class);
	
	public static final int MEASUREMENT_MEAN = 25;
	
	public static final int MEASUREMENT_STD = 5;
	
	public static void logMessage(String id, String remoteId, String msgType, String pckType, String value) {
		LoggingUtility.logMessage(new Object[]{id, remoteId, msgType, pckType, value});
	}
	
	public static String getLogMessage(String id, String remoteId, String msgType, String pckType, String value) {
		return LoggingUtility.getLogMessage(new Object[]{id, remoteId, msgType, pckType, value});
	}
	
	public static Sensor getSensorInstance(String propertyFilePath) throws NodeInitializationException {
		
		String errorMsg = "Cannot instanciate a Sensor using " + propertyFilePath + " file.";
		
		Properties properties;
		try {
			properties = GlobalUtility.getProperties(propertyFilePath);
		} catch (FileNotFoundException e) {
			logger.error(errorMsg);
			throw new NodeInitializationException(errorMsg, propertyFilePath, e);
		} catch (IOException e) {
			logger.error(errorMsg);
			throw new NodeInitializationException(errorMsg, propertyFilePath, e);
		} catch (URISyntaxException e) {
			logger.error(errorMsg);
			throw new NodeInitializationException(errorMsg, propertyFilePath, e);
		}

		Properties defaultProperties;
		try {
			defaultProperties = GlobalUtility.getProperties(GlobalUtility.TEMPLATE_PROPERTIES_FILE_NAME);
		} catch (Exception e) {
			throw new RuntimeException(GlobalUtility.TEMPLATE_PROPERTIES_FILE_NAME + " file is missing in classpath - application error");
		}
		
		if (!properties.keySet().containsAll(defaultProperties.keySet())) {
			// missing properties
			String msg = "Cannot instanciate a Sensor using " + propertyFilePath + " file - missing required properties";
			logger.error(msg);
			throw new NodeInitializationException(msg, propertyFilePath);
		}
		
		String delimiter = GlobalUtility.VALUE_DELIMITER;
		
		Channel[] leftChannels = GlobalUtility.getChannelArray(properties.getProperty("LEFT_CHANNEL_ID").split(delimiter),
				properties.getProperty("LEFT_CHANNEL_IP").split(delimiter), 
				properties.getProperty("LEFT_CHANNEL_PORT").split(delimiter));
		
		Channel[] rightChannels = GlobalUtility.getChannelArray(properties.getProperty("RIGHT_CHANNEL_ID").split(delimiter),
				properties.getProperty("RIGHT_CHANNEL_IP").split(delimiter), 
				properties.getProperty("RIGHT_CHANNEL_PORT").split(delimiter));
		
		return new Sensor(properties.getProperty("ID"),
				Integer.parseInt(properties.getProperty("PERIOD")),
				Integer.parseInt(properties.getProperty("THRESHOLD")),
				Integer.parseInt(properties.getProperty("LEFT_PORT")),
				Integer.parseInt(properties.getProperty("RIGHT_PORT")),
				Integer.parseInt(properties.getProperty("SENDER_PORT")),
				leftChannels,
				rightChannels,
				GlobalUtility.ACK_TIMEOUT_MS);
	}

}
