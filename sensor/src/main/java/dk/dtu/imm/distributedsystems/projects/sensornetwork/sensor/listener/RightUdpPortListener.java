package dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.listener;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.listener.udp.AbstractUdpPortListener;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.transceiver.AbstractTransceiver;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.Packet;

public final class RightUdpPortListener extends AbstractUdpPortListener {
	
	private AbstractTransceiver transceiver; 
	
	public RightUdpPortListener(String nodeId, AbstractTransceiver relatedTransceiver, DatagramSocket socket) {
		super(nodeId, socket);
		this.transceiver = relatedTransceiver;
		
		this.start();
	}

	@Override
	protected void handleIncomingPacket(Packet packet,
			InetAddress fromIpAddress, int fromPortNumber) throws IOException {
		
		logger.debug("Received " + packet);
		
		this.sendAck(fromIpAddress, fromPortNumber);
		
		// TODO Log ACK sent
		
		// TODO Log received packets - SENSOR_DATA: DAT, ALM
		
		transceiver.handlePacket(packet);
	}
}
