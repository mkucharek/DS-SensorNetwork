package dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet;

/**
 * The Enum PacketType.
 *
 * @author Maciej Kucharek <a href="mailto:s091828 (at) student.dtu.dk">s091828 (at) student.dtu.dk</a>
 */
public enum PacketType {
	
	/** The DAT. */
	DAT(PacketGroup.SENSOR_DATA),
	
	/** The ALM. */
	ALM(PacketGroup.SENSOR_DATA), 
	
	/** The ACK. */
	ACK(PacketGroup.ACKNOWLEDGEMENT), 
	
	/** The THR. */
	THR(PacketGroup.COMMAND), 
	
	/** The PRD. */
	PRD(PacketGroup.COMMAND), 
	
	/** The MIN. */
	MIN(PacketGroup.QUERY), 
	
	/** The MAX. */
	MAX(PacketGroup.QUERY), 
	
	/** The AVG. */
	AVG(PacketGroup.QUERY);
	
	/** The packet group. */
	private PacketGroup packetGroup;
	
	/**
	 * Instantiates a new packet type.
	 *
	 * @param packetGroup the packet group
	 */
	PacketType(PacketGroup packetGroup) {
		this.packetGroup = packetGroup;
	}
	
	/**
	 * Gets the packet group.
	 *
	 * @return the packet group
	 */
	public PacketGroup getPacketGroup() {
		return this.packetGroup;
	}
}
