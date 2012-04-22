package dk.dtu.imm.distributedsystems.projecs.sensornetwork.common.packet;

public enum PacketType {
	DAT(PacketGroup.SENSOR_DATA),
	ALM(PacketGroup.SENSOR_DATA), 
	ACK(PacketGroup.ACKNOWLEDGEMENT), 
	THR(PacketGroup.COMMAND), 
	PRD(PacketGroup.COMMAND), 
	MIN(PacketGroup.REQUEST), 
	MAX(PacketGroup.REQUEST), 
	AVG(PacketGroup.REQUEST);
	
	private PacketGroup packetGroup;
	
	PacketType(PacketGroup packetGroup) {
		this.packetGroup = packetGroup;
	}
	
	public PacketGroup getPacketGroup() {
		return this.packetGroup;
	}
}
