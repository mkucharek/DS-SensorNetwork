package dk.dtu.imm.distributedsystems.projects.sensornetwork.common.exceptions;

/**
 * The Class ConnectionHandlerException.
 *
 * @author Maciej Kucharek <a href="mailto:s091828 (at) student.dtu.dk">s091828 (at) student.dtu.dk</a>
 */
public class ConnectionHandlerException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The throwing class. */
	private Class<?> throwingClass;

	/**
	 * Instantiates a new connection handler exception.
	 *
	 * @param message the message
	 * @param throwingClass the throwing class
	 */
	public ConnectionHandlerException(String message, Class<?> throwingClass) {
		super(message);

		this.throwingClass = throwingClass;
	}
	
	/**
	 * Instantiates a new connection handler exception.
	 *
	 * @param cause the cause
	 * @param throwingClass the throwing class
	 */
	public ConnectionHandlerException(Throwable cause, Class<?> throwingClass) {
		super(cause);

		this.throwingClass = throwingClass;
	}

	/**
	 * Instantiates a new connection handler exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 * @param throwingClass the throwing class
	 */
	public ConnectionHandlerException(String message, Throwable cause,
			Class<?> throwingClass) {
		super(message, cause);

		this.throwingClass = throwingClass;
	}

	/**
	 * Gets the throwing class.
	 *
	 * @return the throwing class
	 */
	public Class<?> getThrowingClass() {
		return throwingClass;
	}

}
