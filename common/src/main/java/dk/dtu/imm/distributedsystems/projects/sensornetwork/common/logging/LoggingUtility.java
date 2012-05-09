package dk.dtu.imm.distributedsystems.projects.sensornetwork.common.logging;

import java.util.Date;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.nodes.NodeType;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.MessageType;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.PacketType;

/**
 * The Class LoggingUtility.
 * 
 * Used for logging the messages to STDOUT as per requirements.
 *
 * @author Maciej Kucharek <a href="mailto:s091828 (at) student.dtu.dk">s091828 (at) student.dtu.dk</a>
 */
public class LoggingUtility {

	/**
	 * Actually logs the desired message to STDOUT.
	 *
	 * @param parameters the parameters
	 */
	public static synchronized void logMessage(Object[] parameters) {

		System.out.println(getLogMessage(parameters));
	}
	
	public static void logMessage(String id, String remoteId, MessageType msgType, PacketType pckType, String value) {
		
		System.out.println(getLogMessage(id, remoteId, msgType, pckType, value));
		
	}
	
	public static void logMessage(String id, String remoteId, MessageType msgType, PacketType pckType) {
		logMessage(id, remoteId, msgType, pckType, "");
	}
	
	public static String getLogMessage(String id, String remoteId, MessageType msgType, PacketType pckType, String value) {
		if(id.equals(NodeType.ADMIN.toString())) {
			// ADMIN
			return LoggingUtility.getLogMessage(new Object[]{msgType, pckType, value});
			
		} else if (id.equals(NodeType.SINK.toString())) {
			// SINK
			return LoggingUtility.getLogMessage(new Object[]{remoteId, msgType, pckType, value});
			
		} else {
			// SENSOR
			return LoggingUtility.getLogMessage(new Object[]{id, remoteId, msgType, pckType, value});
		}
	}
	
	public static String getLogMessage(String id, String remoteId, MessageType msgType, PacketType pckType) {
		return getLogMessage(id, remoteId, msgType, pckType, "");
	}
	
	/**
	 * Gets the formatted log message, but does not print it to STDOUT.
	 *
	 * @param parameters the parameters
	 * @return the log message
	 */
	public static synchronized String getLogMessage(Object[] parameters) {
		
		StringBuffer buf = new StringBuffer();

		buf.append(new Date().getTime());

		for (@SuppressWarnings("unused")
		Object parameter : parameters) {
//			buf.append("%10s");
			buf.append("\t%s");
		}
		
		return String.format(buf.toString(), parameters);
	}
}
