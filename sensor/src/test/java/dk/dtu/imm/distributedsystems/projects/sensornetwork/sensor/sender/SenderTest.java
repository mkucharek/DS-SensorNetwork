package dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.sender;

import java.lang.Thread.State;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.Packet;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.PacketType;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.Sensor;

public class SenderTest {

	Sensor sensor;
	
	Sender sender;
	
	private static final int SLEEPVAL = 500;
	
	@Before
	public void before() {
		
		this.sensor = new Sensor(13, 
				15, 
				20, 
				9998, 
				9999, 
				new int[] {1,2}, 
				new String[] {"ss"}, 
				new int[] {192}, 
				new int[] {1,2}, 
				new String[] {"ss"}, 
				new int[] {192},
				SLEEPVAL);
		
		this.sender = new Sender(sensor);
	}
	
	@Test
	public void testData() {
		
		sender.start();
		
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
		
		sender.start();
		
		Assert.assertTrue(sender.isAlive());
		
		Packet packet = new Packet(PacketType.DAT);
		
		sender.addToBuffer(packet);
		
		try {
			Thread.sleep((long) (SLEEPVAL*sensor.getLeftChannelIDs().length));
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
		
		sender.start();
		
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
		
		sender.start();
		
		Assert.assertTrue(sender.isAlive());
		
		Packet packet = new Packet(PacketType.ALM);
		
		sender.addToBuffer(packet);
		
		try {
			Thread.sleep( (long) (SLEEPVAL*sensor.getLeftChannelIDs().length * 1.5) );
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
		
		sender.start();
		
		Assert.assertTrue(sender.isAlive());
		
		Packet packet = new Packet(PacketType.ALM);
		
		sender.addToBuffer(packet);
		
		try {
			Thread.sleep((long) (SLEEPVAL*sensor.getLeftChannelIDs().length * 2));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			Assert.fail();
			e.printStackTrace();
		}
		
		sender.passAck();
		
		Assert.assertEquals(0, sender.successfulSends);
		
	}

}
