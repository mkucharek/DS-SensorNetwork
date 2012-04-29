package dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.listener;

import java.io.IOException;
import java.net.InetAddress;

import org.slf4j.LoggerFactory;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.listener.UdpPortListener;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.Packet;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.components.TransceiverComponent;

public final class RightUdpPortListener extends UdpPortListener {
	
	private TransceiverComponent relatedTransceiver; 
	
	
	public RightUdpPortListener(TransceiverComponent relatedTransceiver, int portNumber) {
		super(LoggerFactory.getLogger(RightUdpPortListener.class), portNumber);
		this.relatedTransceiver = relatedTransceiver;
	}

	@Override
	protected void handleIncomingPacket(Packet packet,
			InetAddress fromIpAddress, int fromPortNumber) throws IOException {
		// TODO Auto-generated method stub
		
		/*
		 * would be good to save the number of channel the packet came from
		 */
		
	}
}
