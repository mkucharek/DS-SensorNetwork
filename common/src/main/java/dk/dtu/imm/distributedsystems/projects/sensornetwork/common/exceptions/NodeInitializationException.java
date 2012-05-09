package dk.dtu.imm.distributedsystems.projects.sensornetwork.common.exceptions;

/**
 * The Class NodeInitializationException.
 * 
 * Indicates that the node cannot be initialized because of either wrong property file
 * specified, or the requested ports are occupied.
 *
 * @author Maciej Kucharek <a href="mailto:s091828 (at) student.dtu.dk">s091828 (at) student.dtu.dk</a>
 */
public class NodeInitializationException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The property file path. */
	private String propertyFilePath;
	
	/**
	 * Instantiates a new node initialization exception.
	 *
	 * @param msg the msg
	 * @param propertyFilePath the property file path
	 */
	public NodeInitializationException(String msg, String propertyFilePath) {
		super(msg);
		
		this.propertyFilePath = propertyFilePath;
	}
	
	/**
	 * Instantiates a new node initialization exception.
	 *
	 * @param msg the msg
	 * @param propertyFilePath the property file path
	 * @param cause the cause
	 */
	public NodeInitializationException(String msg, String propertyFilePath, Throwable cause) {
		super(msg, cause);
		
		this.propertyFilePath = propertyFilePath;
	}

	/**
	 * Gets the property file path.
	 *
	 * @return the property file path
	 */
	public String getPropertyFilePath() {
		return propertyFilePath;
	}

}
