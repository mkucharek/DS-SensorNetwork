package dk.dtu.imm.distributedsystems.projects.sensornetwork.sink.listeners;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.channels.Channel;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.listener.udp.AbstractUdpPortListener;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.transceiver.AbstractTransceiver;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.Packet;

public final class LeftUdpPortListener extends AbstractUdpPortListener {

	private AbstractTransceiver transceiver;
	
	public LeftUdpPortListener(String nodeId, AbstractTransceiver relatedTransceiver, DatagramSocket socket, Channel[] associatedChannels) {
		super(nodeId, socket, associatedChannels);
		this.transceiver = relatedTransceiver;
	}
	
	@Override
	protected void handleIncomingPacket(Packet packet,
			InetAddress fromIpAddress, int fromPortNumber) throws IOException {
			
		// TODO Log received packets - ACK; CMD: THR, PRD
		
		transceiver.handlePacket(packet); 
		
	}
}
