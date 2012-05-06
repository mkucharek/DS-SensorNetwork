package dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.components;

import java.util.Random;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.listener.AbstractPortListener;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.sender.AbstractPortSender;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.components.transceiver.AbstractTransceiver;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.Packet;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.SensorUtility;

public class SensorComponentTest {

	private static final String ID = "0";
	
	private static final int PERIOD = 100;
	
	private static final int THRESHOLD = 25;
	
	private static final int MAX_COUNT = 25;
	
	@SuppressWarnings("unused")
	private SensorComponent sensorComponent;
	
	private DummyTransceiver transceiver;
	
	@Before
	public void setUp() {
		// setting up a dummy transceiver with no listeners nor sender
		this.transceiver = new DummyTransceiver(ID, null, null);
		
		
	}
	
	@After
	public void cleanUp() {
		this.transceiver.close();
		this.sensorComponent.interrupt();
	}
	
	@Test
	public void testMeasurementCount() {
		
		Random rng = new Random();
		
		int measurementCount = rng.nextInt(MAX_COUNT-1) + 1;
		
		this.sensorComponent = new SensorComponent(ID, this.transceiver, PERIOD, THRESHOLD);
		
		try {
			Thread.sleep(PERIOD * measurementCount);
		} catch (InterruptedException e) {
			Assert.fail();
			e.printStackTrace();
		}
		
		Assert.assertEquals(true, this.transceiver.getCallCounter() >= measurementCount - 1);
		Assert.assertEquals(true, this.transceiver.getCallCounter() <= measurementCount + 1);
		
	}
	
	@Test
	public void testMeasurementValues() {
		
		int measurementCount = MAX_COUNT;
		
		this.sensorComponent = new SensorComponent(ID, this.transceiver, PERIOD, THRESHOLD);
		
		try {
			Thread.sleep(PERIOD/10);
		} catch (InterruptedException e) {
			Assert.fail();
			e.printStackTrace();
		}
		
		for(int i=0; i<measurementCount; ++i) {
			try {
				Thread.sleep(PERIOD);
			} catch (InterruptedException e) {
				Assert.fail();
				e.printStackTrace();
			}
			Assert.assertNotNull(transceiver.getLastPacket());
			Assert.assertTrue(Double.parseDouble(this.transceiver.getLastPacket().getValue()) >= SensorUtility.MEASUREMENT_MEAN - SensorUtility.MEASUREMENT_STD);
			Assert.assertTrue(Double.parseDouble(this.transceiver.getLastPacket().getValue()) <= SensorUtility.MEASUREMENT_MEAN + SensorUtility.MEASUREMENT_STD);
			
		}
		
	}

}

class DummyTransceiver extends AbstractTransceiver {

	private int callCounter;
	
	private Packet lastPacket;
	
	protected DummyTransceiver(String id, AbstractPortListener[] listeners,
			AbstractPortSender sender) {
		super(id, listeners, sender);
		
		this.callCounter = 0;
		this.lastPacket = null;
	}
	
	public int getCallCounter() {
		return callCounter;
	}

	public Packet getLastPacket() {
		return lastPacket;
	}

	@Override
	public void handlePacket(Packet packet) {
		this.lastPacket = packet;
		++callCounter;
	}

	@Override
	public void close() {
		// do nothing
		
	}
	
}
