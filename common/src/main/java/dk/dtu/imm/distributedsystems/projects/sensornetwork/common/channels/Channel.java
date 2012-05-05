package dk.dtu.imm.distributedsystems.projects.sensornetwork.common.channels;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class Channel.
 *
 * @author Maciej Kucharek <a href="mailto:s091828 (at) student.dtu.dk">s091828 (at) student.dtu.dk</a>
 */
public class Channel {

	/** The logger. */
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The id. */
	private String id;

	/** The Ip address. */
	private String IpAddress;

	/** The port number. */
	private int portNumber;

	/**
	 * Instantiates a new channel.
	 *
	 * @param id the id
	 * @param IpAddress the ip address
	 * @param portNumber the port number
	 */
	public Channel(String id, String IpAddress, int portNumber) {
		super();
		this.id = id;
		this.IpAddress = IpAddress;
		this.portNumber = portNumber;

		logger.debug("Created new " + this.toString());
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Gets the ip address.
	 *
	 * @return the ip address
	 */
	public String getIpAddress() {
		return IpAddress;
	}

	/**
	 * Gets the port number.
	 *
	 * @return the port number
	 */
	public int getPortNumber() {
		return portNumber;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Channel [id=" + id + ", IpAddress=" + IpAddress
				+ ", portNumber=" + portNumber + "]";
	}

}
