package dk.dtu.imm.distributedsystems.projects.sensornetwork.common.logging;

import java.util.Date;

/**
 * The Class LoggingUtility.
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
