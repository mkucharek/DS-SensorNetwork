package dk.dtu.imm.distributedsystems.projects.sensornetwork.common.nodes;

/**
 * The Class AbstractNode.
 *
 * @author Maciej Kucharek <a href="mailto:s091828 (at) student.dtu.dk">s091828 (at) student.dtu.dk</a>
 */
public abstract class AbstractNode {

	/** The id. */
	protected String id;
	
	/**
	 * Instantiates a new abstract node.
	 *
	 * @param id the id
	 */
	public AbstractNode(String id) {
		this.id = id;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	
}
