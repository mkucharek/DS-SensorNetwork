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
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.nodes.NodeType;

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
		
		Channel[] leftChannels = null;
		Channel[] rightChannels = null;
		
		try {
			leftChannels = GlobalUtility.getChannelArray(new String[] { NodeType.ADMIN.toString() }, 
					properties.getProperty("ADMIN_IP").split(delimiter),
					properties.getProperty("ADMIN_PORT").split(delimiter));

			rightChannels = GlobalUtility.getChannelArray(properties.getProperty("RIGHT_CHANNEL_ID").split(delimiter),
							properties.getProperty("RIGHT_CHANNEL_IP").split(delimiter),
							properties.getProperty("RIGHT_CHANNEL_PORT").split(delimiter));

		} catch (NumberFormatException e) {
			String msg = "Cannot instanciate a Sensor using "
					+ propertyFilePath + " file - RIGHT_CHANNEL_PORT invalid.";
			logger.error(msg);
			throw new NodeInitializationException(msg, propertyFilePath, e);
		}
		
		if(rightChannels.length == 0) {
			String msg = "Cannot instanciate a Sensor using " + propertyFilePath + " file - RIGHT_CHANNEL_ID, RIGHT_CHANNEL_IP and RIGHT_CHANNEL_PORT must be specified.";
			logger.error(msg);
			throw new NodeInitializationException(msg, propertyFilePath);
		}
		
		try {
			return new Sink(NodeType.SINK.toString(),
					Integer.parseInt(properties.getProperty("LEFT_PORT")),
					Integer.parseInt(properties.getProperty("RIGHT_PORT")),
					leftChannels,
					rightChannels,
					GlobalUtility.ACK_TIMEOUT_MS);
		} catch (NumberFormatException e) {
			String msg = "Cannot instanciate a Sensor using "
					+ propertyFilePath + " file - LEFT_PORT or RIGHT_PORT invalid.";
			logger.error(msg);
			throw new NodeInitializationException(msg, propertyFilePath, e);
		}
	}

}
