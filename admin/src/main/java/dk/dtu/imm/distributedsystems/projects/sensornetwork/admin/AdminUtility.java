package dk.dtu.imm.distributedsystems.projects.sensornetwork.admin;

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

public class AdminUtility {
	
	/** The Constant QUERY_TIMEOUT_MS. */
	public static final int QUERY_TIMEOUT_MS = 1000;
	
	protected final static Logger logger = LoggerFactory.getLogger(AdminUtility.class);

	public static Admin getAdminInstance(String propertyFilePath) throws NodeInitializationException {
		return getAdminInstance(propertyFilePath, false);
	}
	
	public static Admin getAdminInstance(String propertyFilePath, boolean fromClasspath) throws NodeInitializationException {
		
		String errorMsg = "Cannot instanciate a Sensor using " + propertyFilePath + " file.";
		
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
			String msg = "Cannot instanciate a Sensor using " + propertyFilePath + " file - missing required properties";
			logger.error(msg);
			throw new NodeInitializationException(msg, propertyFilePath);
		}
		
		String delimiter = GlobalUtility.VALUE_DELIMITER;
		
		Channel[] rightChannels = null;
		
		try {
			rightChannels = GlobalUtility.getChannelArray(new String[] {NodeType.SINK.toString()},
					properties.getProperty("SINK_IP").split(delimiter), 
					properties.getProperty("SINK_PORT").split(delimiter));
		} catch (NumberFormatException e) {
			String msg = "Cannot instanciate a Sensor using "
					+ propertyFilePath + " file - RIGHT_CHANNEL_PORT invalid.";
			logger.error(msg);
			throw new NodeInitializationException(msg, propertyFilePath, e);
		}
		
		
		if(rightChannels.length == 0) {
			String msg = "Cannot instanciate a Sensor using " + propertyFilePath + " file - SINK_IP and SINK_PORT must be specified.";
			logger.error(msg);
			throw new NodeInitializationException(msg, propertyFilePath);
		}
		
		try {
			return new Admin(Integer.parseInt(properties.getProperty("PORT")),
					rightChannels,
					GlobalUtility.ACK_TIMEOUT_MS);
		} catch (NumberFormatException e) {
			String msg = "Cannot instanciate a Sensor using "
					+ propertyFilePath + " file - PORT invalid.";
			logger.error(msg);
			throw new NodeInitializationException(msg, propertyFilePath, e);
		}
	}
	
}
