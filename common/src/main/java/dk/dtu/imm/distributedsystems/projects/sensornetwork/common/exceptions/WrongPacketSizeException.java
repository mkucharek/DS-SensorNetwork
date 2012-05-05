package dk.dtu.imm.distributedsystems.projects.sensornetwork.common.exceptions;

/**
 * The Class WrongPacketSizeException.
 *
 * @author Maciej Kucharek <a href="mailto:s091828 (at) student.dtu.dk">s091828 (at) student.dtu.dk</a>
 */
public class WrongPacketSizeException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The actual packet size. */
	private int actualSize;

	/**
	 * Instantiates a new wrong packet size exception.
	 *
	 * @param message the message
	 * @param size the size
	 */
	public WrongPacketSizeException(String message, int size) {
		super(message);
		
		this.actualSize = size;
	}

	/**
	 * Gets the actual packet size.
	 *
	 * @return the actual size
	 */
	public int getActualSize() {
		return actualSize;
	}

}
