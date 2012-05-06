package dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.listener;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.listener.udp.AbstractUdpPortListener;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.transceiver.AbstractTransceiver;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.Packet;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.PacketGroup;

public final class LeftUdpPortListener extends AbstractUdpPortListener {

	private AbstractTransceiver transceiver;
	
	public LeftUdpPortListener(String nodeId, AbstractTransceiver relatedTransceiver, DatagramSocket socket) {
		super(nodeId, socket);
		this.transceiver = relatedTransceiver;
		
		this.start();
	}
	
	@Override
	protected void handleIncomingPacket(Packet packet,
			InetAddress fromIpAddress, int fromPortNumber) throws IOException {
		
		logger.info("Received " + packet);
		
		// TODO Log received packets - ACK; CMD: THR, PRD
		if(!(packet.getGroup().equals(PacketGroup.COMMAND) || 
				packet.getGroup().equals(PacketGroup.ACKNOWLEDGEMENT))) {
			logger.debug(packet + " accepted by listener");
			transceiver.handlePacket(packet); 
		} else {
			logger.debug(packet + "dropped by listener - wrong type");
		}
		
		
		
		
	}
}
