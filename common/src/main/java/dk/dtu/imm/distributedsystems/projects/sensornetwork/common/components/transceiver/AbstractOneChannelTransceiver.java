package dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.transceiver;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.listener.AbstractPortListener;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.sender.AbstractPortSender;

/**
 * The Class AbstractOneChannelTransceiver.
 *
 * @author Maciej Kucharek <a href="mailto:s091828 (at) student.dtu.dk">s091828 (at) student.dtu.dk</a>
 */
public abstract class AbstractOneChannelTransceiver extends AbstractTransceiver {

	/**
	 * Instantiates a new abstract one channel transceiver.
	 *
	 * @param portListener the port listener
	 * @param sender the sender
	 */
	protected AbstractOneChannelTransceiver(AbstractPortListener portListener,
			AbstractPortSender sender) {
		super(new AbstractPortListener[] { portListener }, sender);
	}

	/**
	 * Gets the port listener.
	 *
	 * @return the port listener
	 */
	protected AbstractPortListener getPortListener() {
		return super.getAllListeners()[0];
	}

}