package dk.dtu.imm.distributedsystems.projects.sensornetwork.common.logging;

import java.util.Date;

/**
 * The Class LoggingUtility.
 *
 * @author Maciej Kucharek <a href="mailto:s091828 (at) student.dtu.dk">s091828 (at) student.dtu.dk</a>
 */
public class LoggingUtility {

	/**
	 * Log message.
	 *
	 * @param parameters the parameters
	 */
	public static synchronized void logMessage(Object[] parameters) {

		StringBuffer buf = new StringBuffer();

		buf.append(new Date().getTime());

		for (@SuppressWarnings("unused")
		Object parameter : parameters) {
			buf.append("%10s");
		}

		System.out.println(String.format(buf.toString(), parameters));
	}
}
