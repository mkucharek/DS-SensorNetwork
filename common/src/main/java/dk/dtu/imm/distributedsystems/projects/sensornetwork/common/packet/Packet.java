package dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet;

import java.io.Serializable;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.GlobalUtility;

/**
 * The Class Packet.
 *
 * @author Maciej Kucharek <a href="mailto:s091828 (at) student.dtu.dk">s091828 (at) student.dtu.dk</a>
 */
public class Packet implements Serializable {

	/** The Constant serialVersionUID. */
	protected static final long serialVersionUID = 1L;

	/** The type. */
	protected PacketType type;

	/** The value. */
	protected String value;
	
	/** The src node id. */
	protected String srcNodeId;

	/**
	 * Instantiates a new packet.
	 *
	 * @param type the type
	 */
	public Packet(String nodeId, PacketType type) {
		
		if(PacketGroup.SENSOR_DATA.equals(type.getPacketGroup()) || 
				PacketGroup.COMMAND.equals(type.getPacketGroup())) {
			throw new IllegalStateException(type + " must have a value");
		}
		
		this.srcNodeId = nodeId;
		this.type = type;
		this.value = GlobalUtility.PACKET_DEFAULT_VALUE;
	}

	/**
	 * Instantiates a new packet.
	 *
	 * @param type the type
	 * @param value the value
	 */
	public Packet(String nodeId, PacketType type, String value) {
		this.srcNodeId = nodeId;
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
	
	/**
	 * Gets the src node id.
	 *
	 * @return the src node id
	 */
	public String getSrcNodeId() {
		return srcNodeId;
	}

	@Override
	public String toString() {
		return "Packet [type=" + type + ", value=" + value + ", srcNodeId="
				+ srcNodeId + "]";
	}

}
