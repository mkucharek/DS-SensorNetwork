package dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet;

import java.io.Serializable;

public class Packet implements Serializable {

	private static final long serialVersionUID = 1L;

	private PacketType type;
	
	private String value;
	
	private String src;
	private String dest;
	
	private int seqNr; // could be BIG_INTEGER
	
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

	public String getSrc() {
		return src;
	}

	public String getDest() {
		return dest;
	}

	public int getSeqNr() {
		return seqNr;
	}
	
}
