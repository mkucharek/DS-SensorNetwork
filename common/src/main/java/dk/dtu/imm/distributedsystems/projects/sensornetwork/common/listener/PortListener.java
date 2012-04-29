package dk.dtu.imm.distributedsystems.projects.sensornetwork.common.listener;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.Packet;


public abstract class PortListener extends Thread {

	protected int portNumber;
	protected Packet currentPacket;
	
	public PortListener(int portNumber) {
		this.portNumber = portNumber;
	}

	public Packet getCurrentPacket() {
		return currentPacket;
	}
	
}
