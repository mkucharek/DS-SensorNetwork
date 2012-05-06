package dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet;

public enum MessageType {
	
	/** Outgoing message */
	SND,
	
	/** Incoming message */
	RCV,
	
	/** Internally generated message*/
	GEN,
	
	/** Internal command */
	SET;

}
