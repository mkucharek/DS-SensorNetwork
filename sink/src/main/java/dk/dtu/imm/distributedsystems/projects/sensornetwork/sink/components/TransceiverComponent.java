package dk.dtu.imm.distributedsystems.projects.sensornetwork.sink.components;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.channels.Channel;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.listener.udp.SensorDataUdpPortListener;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.sender.udp.AbstractUdpPortSender;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.transceiver.AbstractTwoChannelTransceiver;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.Packet;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.PacketGroup;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.sink.Sink;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.sink.listeners.LeftUdpPortListener;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.sink.sender.UdpPortSender;


public class TransceiverComponent extends AbstractTwoChannelTransceiver {

	protected DatagramSocket leftSocket;
	
	protected DatagramSocket rightSocket;
	
	protected Sink sink;
	
	protected Map<String, Integer> sensorValuesMap;
			
	public TransceiverComponent(String nodeId, int leftPortNumber, int rightPortNumber, Channel[] leftChannels,
			Channel[] rightChannels, int ackTimeout) {
		super(nodeId, null, null, null);

		try {
			this.leftSocket = new DatagramSocket(leftPortNumber);
			this.rightSocket = new DatagramSocket(rightPortNumber);
		} catch (SocketException e) {
			throw new RuntimeException(e);
		}
		
		// manually set listeners
		this.getAllListeners()[0] = new LeftUdpPortListener(nodeId, this,
				this.leftSocket, leftChannels);
		this.getAllListeners()[1] = new SensorDataUdpPortListener(nodeId, this,
				this.rightSocket, rightChannels);
		
		this.setSender(new UdpPortSender(nodeId, this.leftSocket, this.rightSocket,
				new LinkedList<Packet>(), leftChannels, rightChannels,
				ackTimeout));
		
		sensorValuesMap = new HashMap<String, Integer>();
	}
	
	@Override
	public synchronized void handlePacket(Packet packet) {
		
		logger.debug("Handling " + packet);
		
		// TODO: Handle QUERY packet type
		
		if (packet.getGroup().equals(PacketGroup.COMMAND)) {
			this.getPortSender().addToBuffer(packet);
			
		} else if (packet.getGroup().equals(PacketGroup.SENSOR_DATA)) {
			try {
				sensorValuesMap.put(packet.getSrcNodeId(), Integer.parseInt(packet.getValue()));
			} catch (NumberFormatException e) {
				logger.warn(packet + " corrupted");
			}
			
		} else if (packet.getGroup().equals(PacketGroup.ACKNOWLEDGEMENT)) {
			logger.debug("Passing ACK to sender");
			
			((AbstractUdpPortSender) this.getPortSender()).passAck();
		} else {
			logger.info("Wrong type group of Packet Received");
		}
	}

	@Override
	public void close() {
		
		super.close();
		
		this.leftSocket.close();
		this.rightSocket.close();
		
	}

}
