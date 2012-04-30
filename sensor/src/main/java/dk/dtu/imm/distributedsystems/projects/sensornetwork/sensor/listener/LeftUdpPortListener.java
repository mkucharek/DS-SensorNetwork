package dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.listener;

import java.io.IOException;
import java.net.InetAddress;

import org.slf4j.LoggerFactory;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.listener.UdpPortListener;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.Packet;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.SensorUtility;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.components.Transceiver;

public final class LeftUdpPortListener extends UdpPortListener {

	private Transceiver relatedTransceiver;
	
	public LeftUdpPortListener(Transceiver relatedTransceiver, int portNumber) {
		super(LoggerFactory.getLogger(LeftUdpPortListener.class), portNumber);
		this.relatedTransceiver = relatedTransceiver;
	}
	
	@Override
	protected void handleIncomingPacket(Packet packet,
			InetAddress fromIpAddress, int fromPortNumber) throws IOException {
		
		this.sendAck(fromIpAddress, fromPortNumber);
		
		relatedTransceiver.handlePacket(packet);
		
	}
}
