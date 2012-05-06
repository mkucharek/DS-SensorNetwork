package dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.transceiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.listener.AbstractPortListener;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.sender.AbstractPortSender;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.Packet;

/**
 * The Class AbstractTransceiver.
 *
 * @author Maciej Kucharek <a href="mailto:s091828 (at) student.dtu.dk">s091828 (at) student.dtu.dk</a>
 */
public abstract class AbstractTransceiver {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/** The listeners. */
	private AbstractPortListener[] listeners;

	/** The sender. */
	private AbstractPortSender sender;

	/**
	 * Instantiates a new abstract transceiver.
	 *
	 * @param listeners the listeners
	 * @param sender the sender
	 */
	protected AbstractTransceiver(AbstractPortListener[] listeners,
			AbstractPortSender sender) {
		this.listeners = listeners;
		this.sender = sender;
	}

	/**
	 * Gets the all listeners.
	 *
	 * @return the all listeners
	 */
	protected AbstractPortListener[] getAllListeners() {
		return listeners;
	}

	/**
	 * Gets the port sender.
	 *
	 * @return the port sender
	 */
	public AbstractPortSender getPortSender() {
		return sender;
	}

	/**
	 * Handle packet.
	 *
	 * @param packet the packet
	 */
	public abstract void handlePacket(Packet packet);

}
