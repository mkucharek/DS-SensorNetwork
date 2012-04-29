package dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.listener;

import java.io.IOException;
import java.net.InetAddress;

import org.slf4j.LoggerFactory;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.listener.UdpPortListener;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.Packet;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.components.TransceiverComponent;

public final class LeftUdpPortListener extends UdpPortListener {

	private TransceiverComponent relatedTransceiver;  // to powinno byc w PortListner, bo we wszystkich listnerach, ale paczka common nie widzi innych paczek (u mnie - WP)
	
	public LeftUdpPortListener(TransceiverComponent relatedTransceiver, int portNumber) {
		super(LoggerFactory.getLogger(LeftUdpPortListener.class), portNumber);
		this.relatedTransceiver = relatedTransceiver;
	}
	
	@Override
	protected void handleIncomingPacket(Packet packet,
			InetAddress fromIpAddress, int fromPortNumber) throws IOException {
		// TODO Auto-generated method stub
		
	}
}
