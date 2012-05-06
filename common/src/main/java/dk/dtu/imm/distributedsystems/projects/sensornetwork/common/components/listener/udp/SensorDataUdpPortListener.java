package dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.listener.udp;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.listener.udp.AbstractUdpPortListener;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.transceiver.AbstractTransceiver;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.Packet;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.PacketGroup;

public final class SensorDataUdpPortListener extends AbstractUdpPortListener {
	
	protected AbstractTransceiver transceiver; 
	
	public SensorDataUdpPortListener(String nodeId, AbstractTransceiver relatedTransceiver, DatagramSocket socket) {
		super(nodeId, socket);
		this.transceiver = relatedTransceiver;
		
		this.start();
	}

	@Override
	protected void handleIncomingPacket(Packet packet,
			InetAddress fromIpAddress, int fromPortNumber) throws IOException {
		
		logger.debug(packet + " received");
		
		// TODO Log ACK sent
		this.sendAck(fromIpAddress, fromPortNumber);
		
		// TODO Log received packets - SENSOR_DATA: DAT, ALM
		if(!(packet.getGroup().equals(PacketGroup.SENSOR_DATA))) {
			logger.debug(packet + " accepted by listener");
			transceiver.handlePacket(packet); 
		} else {
			logger.debug(packet + "dropped by listener - wrong type");
		}
		
		transceiver.handlePacket(packet);
	}
}
