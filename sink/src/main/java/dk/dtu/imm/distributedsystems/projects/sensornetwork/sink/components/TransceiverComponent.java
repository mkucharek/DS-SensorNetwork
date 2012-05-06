package dk.dtu.imm.distributedsystems.projects.sensornetwork.sink.components;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.LinkedList;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.channels.Channel;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.listener.udp.AbstractUdpPortListener;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.transceiver.AbstractTwoChannelTransceiver;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.Packet;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.sink.Sink;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.sink.listeners.LeftUdpPortListener;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.sink.listeners.UdpPortSender;


public class TransceiverComponent extends AbstractTwoChannelTransceiver {

	private Sink sink;
			
	public TransceiverComponent(String nodeId, int leftPortNumber, int rightPortNumber,
			int senderPortNumber, Channel[] leftChannels,
			Channel[] rightChannels, int ackTimeout) {
		super(nodeId, null, null, null);

		try {
		// manually set listeners
		this.getAllListeners()[0] = new LeftUdpPortListener(nodeId, this,
				new DatagramSocket(leftPortNumber), leftChannels);
		//this.getAllListeners()[1] = new RightUdpPortListener(nodeId, this,
		//		new DatagramSocket(rightPortNumber));
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
		this.setSender(new UdpPortSender(nodeId, ((AbstractUdpPortListener) this.getLeftPortListener()).getServerSocket(), ((AbstractUdpPortListener) this.getRightPortListener()).getServerSocket(),
				new LinkedList<Packet>(), leftChannels, rightChannels,
				ackTimeout));
	}
	
	@Override
	public synchronized void handlePacket(Packet packet) {
		
		// TODO: Proper implementation
		throw new UnsupportedOperationException();
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}
}
