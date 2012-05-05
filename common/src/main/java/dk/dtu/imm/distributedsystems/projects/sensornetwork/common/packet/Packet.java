package dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet;

import java.io.Serializable;

/**
 * The Class Packet.
 *
 * @author Maciej Kucharek <a href="mailto:s091828 (at) student.dtu.dk">s091828 (at) student.dtu.dk</a>
 */
public class Packet implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The type. */
	private PacketType type;

	/** The value. */
	private String value;

	/**
	 * Instantiates a new packet.
	 *
	 * @param type the type
	 */
	public Packet(PacketType type) {
		this.type = type;
		this.value = "Not specified";
	}

	/**
	 * Instantiates a new packet.
	 *
	 * @param type the type
	 * @param value the value
	 */
	public Packet(PacketType type, String value) {
		this.type = type;
		this.value = value;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public PacketType getType() {
		return type;
	}

	/**
	 * Gets the group.
	 *
	 * @return the group
	 */
	public PacketGroup getGroup() {
		return type.getPacketGroup();
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

}
