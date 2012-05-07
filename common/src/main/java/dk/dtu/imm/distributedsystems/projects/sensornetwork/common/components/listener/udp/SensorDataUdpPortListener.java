package dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.listener.udp;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.channels.Channel;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.transceiver.AbstractTransceiver;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.logging.LoggingUtility;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.MessageType;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.Packet;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.PacketGroup;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.PacketType;

public class SensorDataUdpPortListener extends AbstractUdpPortListener {
	
	protected AbstractTransceiver transceiver; 
	
	public SensorDataUdpPortListener(String nodeId, AbstractTransceiver relatedTransceiver, DatagramSocket socket, Channel[] associatedChannels) {
		super(nodeId, socket, associatedChannels);
		this.transceiver = relatedTransceiver;
		
		this.start();
	}

	@Override
	protected void handleIncomingPacket(Packet packet,
			InetAddress fromIpAddress, int fromPortNumber) throws IOException {
		
		logger.debug(packet + " received");
		
		this.sendAck(fromIpAddress, fromPortNumber);
		LoggingUtility.logMessage(this.getNodeId(), packet.getSrcNodeId(), MessageType.SND, PacketType.ACK);
		
		LoggingUtility.logMessage(packet.getSrcNodeId(), this.getNodeId(), MessageType.RCV, packet.getType());
		
		if(packet.getGroup().equals(PacketGroup.SENSOR_DATA)) {
			logger.debug(packet + " accepted by listener");
			
			transceiver.handlePacket(packet); 
		} else {
			logger.warn(packet + "dropped by listener - wrong type");
		}
	}
}
