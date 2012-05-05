package dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.sender;

import java.lang.Thread.State;
import java.util.LinkedList;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.GlobalUtility;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.channels.Channel;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.Packet;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.PacketType;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.Sensor;

public class SenderTest {

	Sensor sensor;
	
	Sender sender;
	
	private static final int SLEEPVAL = 500;
	
	Channel[] leftChannels;
	Channel[] rightChannels;
	
	
	@Before
	public void before() {
		
		leftChannels = new Channel[] {new Channel("0", "localhost", 9990) };
		rightChannels = new Channel[] {new Channel("21", "localhost", 9900) };
		
		this.sender = new Sender(9000, new LinkedList<Packet>(), leftChannels, rightChannels, GlobalUtility.ACK_TIMEOUT_MS);
	}
	
	@After
	public void cleanUp() {
		// interrupt previously created Sender and therefore close the socket.
		sender.interrupt();
	}
	
	@Test
	public void testData() {
		
		Assert.assertTrue(sender.isAlive());
		
		Packet packet = new Packet(PacketType.DAT);
		
		sender.addToBuffer(packet);
		
		try {
			Thread.sleep(SLEEPVAL/2);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			Assert.fail();
			e.printStackTrace();
		}
		
		sender.passAck();
		
		try {
			Thread.sleep(SLEEPVAL/4);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			Assert.fail();
			e.printStackTrace();
		}
		
		Assert.assertEquals(State.WAITING, sender.getState());
		Assert.assertEquals(1, sender.successfulSends);
		
	}
	
	@Test
	public void testDataTimeout() {
		
		Assert.assertTrue(sender.isAlive());
		
		Packet packet = new Packet(PacketType.DAT);
		
		sender.addToBuffer(packet);
		
		try {
			Thread.sleep((long) (SLEEPVAL*leftChannels.length));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			Assert.fail();
			e.printStackTrace();
		}
		
		sender.passAck();
		
		Assert.assertEquals(0, sender.successfulSends);
		
	}
	
	@Test
	public void testAlarmData() {
		
		Assert.assertTrue(sender.isAlive());
		
		Packet packet = new Packet(PacketType.ALM);
		
		sender.addToBuffer(packet);
		
		try {
			Thread.sleep(SLEEPVAL/2);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			Assert.fail();
			e.printStackTrace();
		}
		
		sender.passAck();
		
		try {
			Thread.sleep(SLEEPVAL/4);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			Assert.fail();
			e.printStackTrace();
		}
		
		Assert.assertEquals(1, sender.successfulSends);
		
	}
	
	@Test
	public void testAlarmDataTime() {
		
		Assert.assertTrue(sender.isAlive());
		
		Packet packet = new Packet(PacketType.ALM);
		
		sender.addToBuffer(packet);
		
		try {
			Thread.sleep( (long) (SLEEPVAL*leftChannels.length * 1.5) );
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			Assert.fail();
			e.printStackTrace();
		}
		
		sender.passAck();
		
		try {
			Thread.sleep(SLEEPVAL/4);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			Assert.fail();
			e.printStackTrace();
		}
		
		Assert.assertEquals(1, sender.successfulSends);
		
	}
	
	@Test
	public void testAlarmDataTimeout() {
		
		Assert.assertTrue(sender.isAlive());
		
		Packet packet = new Packet(PacketType.ALM);
		
		sender.addToBuffer(packet);
		
		try {
			Thread.sleep((long) (SLEEPVAL*leftChannels.length * 2));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			Assert.fail();
			e.printStackTrace();
		}
		
		sender.passAck();
		
		Assert.assertEquals(0, sender.successfulSends);
		
	}

}
