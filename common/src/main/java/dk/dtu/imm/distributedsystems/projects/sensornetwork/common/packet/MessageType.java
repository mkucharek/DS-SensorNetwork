package dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet;

/**
 * The Enum MessageType.
 *
 * @author Maciej Kucharek <a href="mailto:s091828 (at) student.dtu.dk">s091828 (at) student.dtu.dk</a>
 */
public enum MessageType {
	
	/** Outgoing message. */
	SND,
	
	/** Incoming message. */
	RCV,
	
	/** Internally generated message. */
	GEN,
	
	/** Internal command. */
	SET;

}
