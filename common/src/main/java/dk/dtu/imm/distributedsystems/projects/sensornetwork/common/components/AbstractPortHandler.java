package dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components;

import java.net.SocketException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.exceptions.ConnectionHandlerException;

/**
 * The Class AbstractPortHandler.
 *
 * @author Maciej Kucharek <a href="mailto:s091828 (at) student.dtu.dk">s091828 (at) student.dtu.dk</a>
 */
public abstract class AbstractPortHandler extends Thread {

	/** The logger. */
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/** The port number. */
	protected int portNumber;
	
	/**
	 * Instantiates a new abstract port handler.
	 *
	 * @param portNumber the port number
	 */
	protected AbstractPortHandler(int portNumber) {
		this.portNumber = portNumber;
		this.start();
	}
	
	/**
	 * Sets the up.
	 *
	 * @throws SocketException the socket exception
	 */
	protected abstract void setUp() throws SocketException;
	
	/**
	 * Handle connection.
	 *
	 * @throws ConnectionHandlerException the connection handler exception
	 */
	protected abstract void handleConnection() throws ConnectionHandlerException, InterruptedException;
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		
		try {
			setUp();
			
		} catch (SocketException ex) {
			logger.error("Port " + portNumber + " is occupied.");
			return;
		}

		while (true) {

			try {
				
				handleConnection();

			} catch (InterruptedException e) {
				logger.debug("Thread interrupted");
				return;
			} catch (ConnectionHandlerException e) {
				logger.error(e.getMessage(), e);
				return;
			}
		}

	}

}
