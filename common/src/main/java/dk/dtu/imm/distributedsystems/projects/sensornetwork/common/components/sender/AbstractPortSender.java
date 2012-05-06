package dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.sender;

import java.util.Queue;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.AbstractPortHandler;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.exceptions.ConnectionHandlerException;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.Packet;

/**
 * The Class AbstractPortSender.
 *
 * @author Maciej Kucharek <a href="mailto:s091828 (at) student.dtu.dk">s091828 (at) student.dtu.dk</a>
 */
public abstract class AbstractPortSender extends AbstractPortHandler {
	
	/** The buffer. */
	protected Queue<Packet> buffer;
	
	/**
	 * Instantiates a new abstract port sender.
	 *
	 * @param portNumber the port number
	 * @param buffer the buffer
	 */
	public AbstractPortSender(String nodeId, int portNumber, Queue<Packet> buffer) {
		super(nodeId, portNumber);
		this.buffer = buffer;
	}
	
	/**
	 * Adds the to buffer.
	 *
	 * @param packet the packet
	 */
	public synchronized void addToBuffer(Packet packet) {
		
		if (this.buffer.offer(packet)) {
			logger.debug("Added " + packet + " to sender buffer. Buffer size is now " + this.buffer.size());
			this.notify();
		}
	}
	
	/* (non-Javadoc)
	 * @see dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.AbstractPortHandler#handleConnection()
	 */
	@Override
	protected void handleConnection() throws ConnectionHandlerException, InterruptedException {
		if (!buffer.isEmpty()) {

			Packet packet = buffer.poll();

			handleOutgoingPacket(packet);

		}
		
		synchronized (this) {
			this.wait();
		}
	}

	/**
	 * Handle outgoing packet.
	 *
	 * @param packet the packet
	 */
	protected abstract void handleOutgoingPacket(Packet packet) throws ConnectionHandlerException, InterruptedException;
	
}
