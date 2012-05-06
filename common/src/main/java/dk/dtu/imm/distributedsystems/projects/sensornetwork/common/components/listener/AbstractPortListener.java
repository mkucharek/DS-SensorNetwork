package dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.listener;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.AbstractPortHandler;


/**
 * The Class AbstractPortHandler.
 * 
 * @author Maciej Kucharek <a href="mailto:s091828 (at) student.dtu.dk">s091828 (at) student.dtu.dk</a>
 */
public abstract class AbstractPortListener extends AbstractPortHandler {
	
	/**
	 * Instantiates a new abstract port listener.
	 *
	 * @param portNumber the port number
	 */
	protected AbstractPortListener(String nodeId, int portNumber) {
		super(nodeId, portNumber);
	}
	
}
