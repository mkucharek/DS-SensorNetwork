package dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.sender;

import java.io.IOException;
import java.lang.Thread.State;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.Queue;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.channels.Channel;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.exceptions.WrongPacketSizeException;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.Packet;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.PacketType;

/**
 * The Class UdpPortSenderTest.
 *
 * @author Maciej Kucharek <a href="mailto:s091828 (at) student.dtu.dk">s091828 (at) student.dtu.dk</a>
 */
public class UdpPortSenderTest {
	
	/** The sender. */
	TestableUdpPortSender sender;
	
	/** The Constant ID. */
	private static final String ID = "0";
	
	/** The Constant SLEEPVAL. */
	private static final int SLEEPVAL = 200;
	
	/** The Constant MAX_GUESS_PORT_ATTEMPTS. */
	private static final int MAX_GUESS_PORT_ATTEMPTS = 5;
	
	/** The Constant START_LEFT_PORT_NUMBER. */
	private static final int START_LEFT_PORT_NUMBER = 21100;
	
	/** The Constant START_RIGHT_PORT_NUMBER. */
	private static final int START_RIGHT_PORT_NUMBER = 21200;
	
	/** The left port number. */
	private static int leftPortNumber = 9999;
	
	/** The right port number. */
	private static int rightPortNumber = 9998;
	
	/** The left channels. */
	Channel[] leftChannels;
	
	/** The right channels. */
	Channel[] rightChannels;
	
	/** The left socket. */
	DatagramSocket leftSocket;
	
	/** The right socket. */
	DatagramSocket rightSocket;
	
	/**
	 * Guess port.
	 */
	@BeforeClass
	public static void guessPort() {
		
		DatagramSocket dummySocket = null;
		for(int i=0; i < MAX_GUESS_PORT_ATTEMPTS; ++i) {
			try {
				dummySocket = new DatagramSocket(START_LEFT_PORT_NUMBER + i);
			} catch (SocketException e) {
				continue;
			}
			leftPortNumber = START_LEFT_PORT_NUMBER + i;
			dummySocket.close();
			break;
		}
		
		
		
		for(int i=0; i < MAX_GUESS_PORT_ATTEMPTS; ++i) {
			try {
				dummySocket = new DatagramSocket(START_RIGHT_PORT_NUMBER);
			} catch (SocketException e) {
				continue;
			}
			rightPortNumber = START_RIGHT_PORT_NUMBER + i;
			dummySocket.close();
			break;
		}
		
	}
	
	/**
	 * Before.
	 */
	@Before
	public void before() {
		
		leftChannels = new Channel[] {new Channel("0", "localhost", 9990) };
		rightChannels = new Channel[] {new Channel("21", "localhost", 9900) };
		
		try {
			leftSocket = new DatagramSocket(leftPortNumber);
			rightSocket = new DatagramSocket(rightPortNumber);
		} catch (SocketException e) {
			Assert.fail("Couldn't create test sockets - please skip these tests");
		}
		
		this.sender = new TestableUdpPortSender(ID, leftSocket, rightSocket, new LinkedList<Packet>(), leftChannels, rightChannels, SLEEPVAL);
	}
	
	/**
	 * Clean up.
	 */
	@After
	public void cleanUp() {
		// interrupt previously created Sender and therefore close the socket.
		sender.interrupt();
		
		leftSocket.close();
		rightSocket.close();
	}
	
	/**
	 * Test data timeout.
	 */
	@Test
	public void testDataTimeout() {
		
		Assert.assertTrue(sender.isAlive());
		
		Packet packet = new Packet(ID, PacketType.DAT, "15");
		
		sender.addToBuffer(packet);
		
		try {
			Thread.sleep((long) (SLEEPVAL*leftChannels.length));
		} catch (InterruptedException e) {
			Assert.fail();
			e.printStackTrace();
		}
		
		sender.passAck();
		
		Assert.assertEquals(0, sender.getSuccessfulSends());
		
	}
	
	/**
	 * Test data.
	 */
	@Test
	public void testData() {
		
		Assert.assertTrue(sender.isAlive());
		
		Packet packet = new Packet(ID, PacketType.DAT, "10");
		
		sender.addToBuffer(packet);
		
		try {
			Thread.sleep(SLEEPVAL/2);
		} catch (InterruptedException e) {
			Assert.fail();
			e.printStackTrace();
		}
		
		try {
			sender.passAck();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			Thread.sleep(SLEEPVAL/4);
		} catch (InterruptedException e) {
			Assert.fail();
			e.printStackTrace();
		}
		
		Assert.assertEquals(State.WAITING, sender.getState());
		Assert.assertEquals(1, sender.getSuccessfulSends());
		
	}
	
	/**
	 * Test alarm data.
	 */
	@Test
	public void testAlarmData() {
		
		Assert.assertTrue(sender.isAlive());
		
		Packet packet = new Packet(ID, PacketType.ALM, "-30");
		
		sender.addToBuffer(packet);
		
		try {
			Thread.sleep(SLEEPVAL/2);
		} catch (InterruptedException e) {
			Assert.fail();
			e.printStackTrace();
		}
		
		sender.passAck();
		
		try {
			Thread.sleep(SLEEPVAL/4);
		} catch (InterruptedException e) {
			Assert.fail();
			e.printStackTrace();
		}
		
		Assert.assertEquals(1, sender.getSuccessfulSends());
		
	}
	
	/**
	 * Test alarm data time.
	 */
	@Test
	public void testAlarmDataTime() {
		
		Assert.assertTrue(sender.isAlive());
		
		Packet packet = new Packet(ID, PacketType.ALM, "-50");
		
		sender.addToBuffer(packet);
		
		try {
			Thread.sleep( (long) (SLEEPVAL*leftChannels.length * 1.5) );
		} catch (InterruptedException e) {
			Assert.fail();
			e.printStackTrace();
		}
		
		sender.passAck();
		
		try {
			Thread.sleep(SLEEPVAL/4);
		} catch (InterruptedException e) {
			Assert.fail();
			e.printStackTrace();
		}
		
		Assert.assertEquals(1, sender.getSuccessfulSends());
		
	}
	
	/**
	 * Test alarm data timeout.
	 */
	@Test
	public void testAlarmDataTimeout() {
		
		Assert.assertTrue(sender.isAlive());
		
		Packet packet = new Packet(ID, PacketType.ALM, "0");
		
		sender.addToBuffer(packet);
		
		try {
			Thread.sleep((long) (SLEEPVAL*leftChannels.length * 2));
		} catch (InterruptedException e) {
			Assert.fail();
			e.printStackTrace();
		}
		
		sender.passAck();
		
		Assert.assertEquals(0, sender.getSuccessfulSends());
		
	}
	
	/**
	 * Test whether receiveing more than one ACK signal is handled properly
	 * by the Sender - i.e. additional ack should be dropped.
	 */
	@Test
	public void testDataMultipleACK() {
		
		Assert.assertTrue(sender.isAlive());
		
		Packet packet = new Packet(ID, PacketType.DAT, "3");
		
		sender.addToBuffer(packet);
		
		try {
			Thread.sleep(SLEEPVAL/4);
		} catch (InterruptedException e) {
			Assert.fail();
			e.printStackTrace();
		}
		
		sender.passAck();
		sender.passAck();
		sender.passAck();
		
		try {
			Thread.sleep(SLEEPVAL/4);
		} catch (InterruptedException e) {
			Assert.fail();
			e.printStackTrace();
		}
		
		sender.passAck();
		
		try {
			Thread.sleep(SLEEPVAL/4);
		} catch (InterruptedException e) {
			Assert.fail();
			e.printStackTrace();
		}
		
		Assert.assertEquals(State.WAITING, sender.getState());
		Assert.assertEquals(1, sender.getSuccessfulSends());
		
	}

}

class TestableUdpPortSender extends UdpPortSender {

	private int successfulSends;
	
	private int multicastSends;
	
	public TestableUdpPortSender(String nodeId, DatagramSocket leftSocket, DatagramSocket rightSocket, Queue<Packet> buffer,
			Channel[] leftChannels, Channel[] rightChannels, int ackTimeout) {
		super(nodeId, leftSocket, rightSocket, buffer, leftChannels, rightChannels, ackTimeout);
		
		this.successfulSends = 0;
		this.multicastSends = 0;
	}

	public int getSuccessfulSends() {
		return successfulSends;
	}
	
	public int getMulticastSends() {
		return multicastSends;
	}

	@Override
	protected boolean sendUnicastLeft(Packet packet) throws WrongPacketSizeException, IOException, InterruptedException {
		
		if(super.sendUnicastLeft(packet)) {
			++successfulSends;
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected void sendMulticastRight(Packet packet) throws WrongPacketSizeException, IOException {
		
		super.sendMulticastRight(packet);
		++multicastSends;
	}
	
}
