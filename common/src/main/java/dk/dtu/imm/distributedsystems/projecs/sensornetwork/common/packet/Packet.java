package dk.dtu.imm.distributedsystems.projecs.sensornetwork.common.packet;

import java.io.Serializable;

public class Packet implements Serializable {

	private static final long serialVersionUID = 1L;

	private PacketType type;
	
	private String value;
	
	public Packet(PacketType type) {
		this.type = type;
		this.value = "Not specified";
	}
	
	public Packet(PacketType type, String value) {
		this.type = type;
		this.value = value;
	}

	public PacketType getType() {
		return type;
	}
	
	public PacketGroup getGroup() {
		return type.getPacketGroup();
	}

	public String getValue() {
		return value;
	}
	
}
