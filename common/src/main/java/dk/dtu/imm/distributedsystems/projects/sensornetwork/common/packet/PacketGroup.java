package dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet;

/**
 * The Enum PacketGroup.
 *
 * @author Maciej Kucharek <a href="mailto:s091828 (at) student.dtu.dk">s091828 (at) student.dtu.dk</a>
 */
public enum PacketGroup {

	/** The SENSO r_ data. */
	SENSOR_DATA,
	
	/** The ACKNOWLEDGEMENT. */
	ACKNOWLEDGEMENT,
	
	/** The COMMAND. */
	COMMAND,
	
	/** The REQUEST. */
	REQUEST;

}
