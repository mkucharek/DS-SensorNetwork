package dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.transceiver;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.listener.AbstractPortListener;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.sender.AbstractPortSender;

/**
 * The Class AbstractTwoChannelTransceiver.
 *
 * @author Maciej Kucharek <a href="mailto:s091828 (at) student.dtu.dk">s091828 (at) student.dtu.dk</a>
 */
public abstract class AbstractTwoChannelTransceiver extends AbstractTransceiver {

	/**
	 * Instantiates a new abstract two channel transceiver.
	 *
	 * @param leftPortListener the left port listener
	 * @param rightPortListener the right port listener
	 * @param sender the sender
	 */
	protected AbstractTwoChannelTransceiver(
			AbstractPortListener leftPortListener,
			AbstractPortListener rightPortListener, AbstractPortSender sender) {
		super(
				new AbstractPortListener[] { leftPortListener,
						rightPortListener }, sender);
	}

	/**
	 * Gets the left port listener.
	 *
	 * @return the left port listener
	 */
	protected AbstractPortListener getLeftPortListener() {
		return super.getAllListeners()[0];
	}

	/**
	 * Gets the right port listener.
	 *
	 * @return the right port listener
	 */
	protected AbstractPortListener getRightPortListener() {
		return super.getAllListeners()[1];
	}

}
