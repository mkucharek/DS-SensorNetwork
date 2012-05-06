package dk.dtu.imm.distributedsystems.projects.sensornetwork.sink;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.GlobalUtility;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.channels.Channel;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.exceptions.NodeInitializationException;

public class SinkUtility {
	
	protected final static Logger logger = LoggerFactory.getLogger(SinkUtility.class);
	
	public static Sink getSinkInstance(String propertyFilePath) throws NodeInitializationException {
		return getSinkInstance(propertyFilePath, false);
	}
	
	public static Sink getSinkInstance(String propertyFilePath, boolean fromClasspath) throws NodeInitializationException {
		
		String errorMsg = "Cannot instanciate a Sink using " + propertyFilePath + " file.";
		
		Properties properties;
		try {
			if(fromClasspath) {
				properties = GlobalUtility.getPropertiesFromClasspath(propertyFilePath);
			} else {
				properties = GlobalUtility.getProperties(propertyFilePath);
			}
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
			defaultProperties = GlobalUtility.getPropertiesFromClasspath(GlobalUtility.TEMPLATE_PROPERTIES_FILE_NAME);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(GlobalUtility.TEMPLATE_PROPERTIES_FILE_NAME + " file is missing in classpath - application error");
			
		}
		
		if (!properties.keySet().containsAll(defaultProperties.keySet())) {
			// missing properties
			String msg = "Cannot instanciate a Sink using " + propertyFilePath + " file - missing required properties";
			logger.error(msg);
			throw new NodeInitializationException(msg, propertyFilePath);
		}
		
		String delimiter = GlobalUtility.VALUE_DELIMITER;
		
		Channel[] leftChannels = new Channel[] {new Channel("ADMIN", properties.getProperty("ADMIN_IP"), Integer.parseInt(properties.getProperty("ADMIN_PORT"))) };
		
		Channel[] rightChannels = GlobalUtility.getChannelArray(properties.getProperty("RIGHT_CHANNEL_ID").split(delimiter),
				properties.getProperty("RIGHT_CHANNEL_IP").split(delimiter), 
				properties.getProperty("RIGHT_CHANNEL_PORT").split(delimiter));
		
		return new Sink(properties.getProperty("ID"),
				Integer.parseInt(properties.getProperty("PERIOD")),
				Integer.parseInt(properties.getProperty("THRESHOLD")),
				Integer.parseInt(properties.getProperty("LEFT_PORT")),
				Integer.parseInt(properties.getProperty("RIGHT_PORT")),
				leftChannels,
				rightChannels,
				GlobalUtility.ACK_TIMEOUT_MS);
	}

}
