package dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.components;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.Packet;

public interface Transceiver {

	public void handlePacket(Packet packet);
}
