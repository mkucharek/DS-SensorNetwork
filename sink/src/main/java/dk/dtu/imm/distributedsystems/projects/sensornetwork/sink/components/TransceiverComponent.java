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
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.PacketType;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.sink.Sink;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.sink.listeners.SinkUdpPortListener;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.sink.sender.SinkUdpPortSender;


public class TransceiverComponent extends AbstractTwoChannelTransceiver {

	protected DatagramSocket leftSocket;
	
	protected DatagramSocket rightSocket;
	
	protected Sink sink;
	
	protected Map<String, Integer> sensorValuesMap;
			
	public TransceiverComponent(Sink sink, String nodeId, int leftPortNumber, int rightPortNumber, Channel[] leftChannels,
			Channel[] rightChannels, int ackTimeout) {
		super(nodeId, null, null, null);

		try {
			this.leftSocket = new DatagramSocket(leftPortNumber);
			this.rightSocket = new DatagramSocket(rightPortNumber);
		} catch (SocketException e) {
			throw new RuntimeException(e);
		}
		
		this.sink = sink;
		
		// manually set listeners
		this.getAllListeners()[0] = new SinkUdpPortListener(nodeId, this,
				this.leftSocket, leftChannels);
		this.getAllListeners()[1] = new SensorDataUdpPortListener(nodeId, this,
				this.rightSocket, rightChannels);
		
		this.setSender(new SinkUdpPortSender(nodeId, this.leftSocket, this.rightSocket,
				new LinkedList<Packet>(), leftChannels, rightChannels,
				ackTimeout));
		
		sensorValuesMap = new HashMap<String, Integer>();
	}
	
	private synchronized String getMaxSensorValue() {
		
		if (sensorValuesMap.isEmpty()) {
			return "";
		}
		
		Integer maxValue = Integer.MIN_VALUE;
		
		for (Integer curInt: sensorValuesMap.values()) {
			if (curInt > maxValue) {
				maxValue = curInt;
			}
		}
		
		return Integer.toString(maxValue);
	}
	
	private synchronized String getMinSensorValue() {
		
		if (sensorValuesMap.isEmpty()) {
			return "";
		}
		
		Integer minValue = Integer.MAX_VALUE;
		
		for (Integer curInt: sensorValuesMap.values()) {
			if (curInt < minValue) {
				minValue = curInt;
			}
		}
		
		return Integer.toString(minValue);

	}
	
	private synchronized String getAvgSensorValue() {
		
		if (sensorValuesMap.isEmpty()) {
			return "";
		}
		
		Integer sum = 0;
		
		for (Integer curInt: sensorValuesMap.values()) {
			sum += curInt;
		}
		
		return Integer.toString(sum / sensorValuesMap.size());

	}
	
	@Override
	public synchronized void handlePacket(Packet packet) {
		
		logger.debug("Handling " + packet);
		
		if (packet.getGroup().equals(PacketGroup.COMMAND)) {
			logger.debug("CMD packet found - adding to sender buffer");
			this.getPortSender().addToBuffer(packet);
			
		} else if (packet.getGroup().equals(PacketGroup.SENSOR_DATA)) {
			try {
				synchronized(this) {
					sensorValuesMap.put(packet.getSrcNodeId(), Integer.parseInt(packet.getValue()));
				}
				
				logger.debug("ValueMap updated - new entry is <" + packet.getSrcNodeId() + "," + Integer.parseInt(packet.getValue()) + ">");
				logger.debug("ValueMap size is now " + sensorValuesMap.size());
			} catch (NumberFormatException e) {
				logger.warn(packet + " corrupted");
			}
			
			// inform Admin Node about ALARM_DATA
			
			if (packet.getType().equals(PacketType.ALM)) {
				logger.debug("ALM data found - adding to sender buffer");
				this.getPortSender().addToBuffer(packet);
			}
			
		} else if (packet.getGroup().equals(PacketGroup.ACKNOWLEDGEMENT)) {
			logger.debug("Passing ACK to sender");
			
			((AbstractUdpPortSender) this.getPortSender()).passAck();
		} else if (packet.getGroup().equals(PacketGroup.QUERY)) {
			
			if (packet.getType().equals(PacketType.MAX)) {
				
				this.getPortSender().addToBuffer(new Packet(this.getId(), packet.getType(), getMaxSensorValue()));
				
			} else if (packet.getType().equals(PacketType.MIN)) {
				
				this.getPortSender().addToBuffer(new Packet(this.getId(), packet.getType(), getMinSensorValue()));
				
			} else if (packet.getType().equals(PacketType.AVG)) {
				
				this.getPortSender().addToBuffer(new Packet(this.getId(), packet.getType(), getAvgSensorValue()));
				
			} else {
				logger.warn("Wrong type of QUERY Packet Received");
			}
		
		} else {
			logger.warn("Wrong type group of Packet Received");
		}
	}

	@Override
	public void close() {
		
		super.close();
		
		this.leftSocket.close();
		this.rightSocket.close();
		
	}

}
