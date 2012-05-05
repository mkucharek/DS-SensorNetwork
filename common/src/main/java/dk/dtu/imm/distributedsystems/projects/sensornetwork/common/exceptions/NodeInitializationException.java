package dk.dtu.imm.distributedsystems.projects.sensornetwork.common.exceptions;

public class NodeInitializationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String propertyFilePath;
	
	public NodeInitializationException(String msg, String propertyFilePath) {
		super(msg);
		
		this.propertyFilePath = propertyFilePath;
	}
	
	public NodeInitializationException(String msg, String propertyFilePath, Throwable cause) {
		super(msg, cause);
		
		this.propertyFilePath = propertyFilePath;
	}

	public String getPropertyFilePath() {
		return propertyFilePath;
	}

}
