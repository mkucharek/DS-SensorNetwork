package dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.components;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.LinkedList;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.channels.Channel;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.listener.udp.SensorDataUdpPortListener;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.sender.udp.AbstractUdpPortSender;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.transceiver.AbstractTwoChannelTransceiver;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.logging.LoggingUtility;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.MessageType;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.Packet;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.PacketGroup;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.PacketType;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.Sensor;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.SensorUtility;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.listener.LeftUdpPortListener;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.sender.UdpPortSender;


public class TransceiverComponent extends AbstractTwoChannelTransceiver {

	protected DatagramSocket leftSocket;
	
	protected DatagramSocket rightSocket;
	
	private Sensor sensor;
			
	public TransceiverComponent(String nodeId, int leftPortNumber, int rightPortNumber, 
			Channel[] leftChannels, Channel[] rightChannels, int ackTimeout) {
		super(null, null, null);

		try {
			this.leftSocket = new DatagramSocket(leftPortNumber);
			this.rightSocket = new DatagramSocket(rightPortNumber);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// manually set listeners
		this.getAllListeners()[0] = new LeftUdpPortListener(nodeId, this,
				this.leftSocket);
		this.getAllListeners()[1] = new SensorDataUdpPortListener(nodeId, this,
				this.rightSocket);
		
		this.setSender(new UdpPortSender(nodeId, this.leftSocket, this.rightSocket,
				new LinkedList<Packet>(), leftChannels, rightChannels,
				ackTimeout));
	}
	
	@Override
	public synchronized void handlePacket(Packet packet) {
		
		logger.debug("Handling " + packet);
		
		if (packet.getGroup() == PacketGroup.COMMAND) {
			if (packet.getType() == PacketType.PRD) {
				sensor.getSensorComponent().setPeriod(Integer.parseInt(packet.getValue()));
			} else if (packet.getType() == PacketType.THR) {
				sensor.getSensorComponent().setThreshold(Integer.parseInt(packet.getValue()));
			} else {
				logger.info("Wrong type of Command Packet Received");
			}
			
			LoggingUtility.logMessage(sensor.getId(), packet.getSrcNodeId(), MessageType.SET, packet.getType(), packet.getValue());
		} else if (packet.getGroup() == PacketGroup.SENSOR_DATA) {
			this.getPortSender().addToBuffer(packet);
			
			LoggingUtility.logMessage(sensor.getId(), packet.getSrcNodeId(), MessageType.GEN, packet.getType(), packet.getValue());
		} else if (packet.getGroup() == PacketGroup.ACKNOWLEDGEMENT) {
			logger.debug("Passing ACK to sender");
			
			((AbstractUdpPortSender) this.getPortSender()).passAck();
		} else {
			logger.info("Wrong type group of Packet Received");
		}
	}

	@Override
	public void close() {
		
		this.getLeftPortListener().interrupt();
		this.getRightPortListener().interrupt();
		this.getPortSender().interrupt();
		
		this.leftSocket.close();
		this.rightSocket.close();
	}
}
