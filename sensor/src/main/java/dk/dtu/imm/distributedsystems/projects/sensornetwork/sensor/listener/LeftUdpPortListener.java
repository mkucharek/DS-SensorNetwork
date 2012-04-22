package dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.components.TransceiverComponent;

public class LeftUdpPortListener extends Thread {

private Logger logger = LoggerFactory.getLogger(LeftUdpPortListener.class);
	
	private int portNumber;
	
	private TransceiverComponent relatedTransceiver;
	
	public LeftUdpPortListener(TransceiverComponent relatedTransceiver, int portNumber) {
		this.relatedTransceiver = relatedTransceiver;
		this.portNumber = portNumber;
	}
}
