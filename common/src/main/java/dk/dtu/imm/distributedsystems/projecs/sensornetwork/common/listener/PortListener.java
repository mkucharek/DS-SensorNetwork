package dk.dtu.imm.distributedsystems.projecs.sensornetwork.common.listener;


public abstract class PortListener extends Thread {

	protected int portNumber;
	
	public PortListener(int portNumber) {
		this.portNumber = portNumber;
	}
	
}
