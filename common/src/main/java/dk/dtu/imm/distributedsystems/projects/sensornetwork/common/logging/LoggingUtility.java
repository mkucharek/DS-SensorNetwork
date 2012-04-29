package dk.dtu.imm.distributedsystems.projects.sensornetwork.common.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingUtility {
	
	private Logger logger = LoggerFactory.getLogger(LoggingUtility.class);

	/* Here is the instance of the Singleton */
	private static LoggingUtility instance_;

	/* Need the following object to synchronize */
	/* a block */
	private static Object syncObject_ = new Object();

	/* Prevent direct access to the constructor */
	private LoggingUtility() {
		super();
	}

	public static LoggingUtility getInstance() {
		/*
		 * in a non-thread-safe version of a Singleton the following line could
		 * be executed, and the
		 */
		/* thread could be immediately swapped out */
		if (instance_ == null) {
			synchronized (syncObject_) {
				if (instance_ == null) {
					instance_ = new LoggingUtility();
				}
			}
		}
		return instance_;
	}
	
	@SuppressWarnings("unused")
	public static void logMessage(Object[] parameters) {
		
		StringBuffer buf = new StringBuffer();
		
		for (Object parameter : parameters) {
			buf.append("%12s");
		}

		LoggingUtility.getInstance().logger.trace(String.format(buf.toString(),parameters));
		
	}
}
