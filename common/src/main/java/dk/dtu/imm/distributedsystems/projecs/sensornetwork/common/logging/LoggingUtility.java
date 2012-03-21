package dk.dtu.imm.distributedsystems.projecs.sensornetwork.common.logging;

public class LoggingUtility {

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
	
	public String getLogMessage(String id, String remote, String messageType, String packetType, String value) {
		
		StringBuffer sbuf = new StringBuffer(128);
	    sbuf.append(id);
	    sbuf.append(" ");
	    sbuf.append(this.getLogMessage(remote, messageType, packetType, value));
	    return sbuf.toString();
	    
	}
	
    public String getLogMessage(String remote, String messageType, String packetType, String value) {
		
		StringBuffer sbuf = new StringBuffer(128);
	    sbuf.append(remote);
	    sbuf.append(" ");
	    sbuf.append(this.getLogMessage(messageType, packetType, value));
	    return sbuf.toString();
	    
	}
	
	public String getLogMessage(String messageType, String packetType, String value) {
		
		StringBuffer sbuf = new StringBuffer(128);
	    sbuf.append(messageType);
	    sbuf.append(" ");
	    sbuf.append(packetType);
	    sbuf.append(" ");
	    sbuf.append(value);
	    return sbuf.toString();
	    
	}
}
