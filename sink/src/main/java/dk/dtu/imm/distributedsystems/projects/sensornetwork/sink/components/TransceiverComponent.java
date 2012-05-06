package dk.dtu.imm.distributedsystems.projects.sensornetwork.sink.components;

import java.util.LinkedList;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.channels.Channel;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.transceiver.AbstractTwoChannelTransceiver;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.Packet;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.sink.Sink;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.sink.listeners.LeftUdpPortListener;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.sink.listeners.RightUdpPortListener;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.sink.listeners.UdpPortSender;


public class TransceiverComponent extends AbstractTwoChannelTransceiver {

	private Sink sink;
			
	public TransceiverComponent(String nodeId, int leftPortNumber, int rightPortNumber,
			int senderPortNumber, Channel[] leftChannels,
			Channel[] rightChannels, int ackTimeout) {
		super(null, null, new UdpPortSender(nodeId, senderPortNumber,
				new LinkedList<Packet>(), leftChannels, rightChannels,
				ackTimeout));

		// manually set listeners
		this.getAllListeners()[0] = new LeftUdpPortListener(nodeId, this,
				leftPortNumber);
		this.getAllListeners()[1] = new RightUdpPortListener(nodeId, this,
				rightPortNumber);
	}
	
	@Override
	public synchronized void handlePacket(Packet packet) {
		
		// TODO: Proper implementation
		throw new UnsupportedOperationException();
	}
}
