package dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.components;

import java.util.Random;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.Packet;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.SensorUtility;

public class SensorComponentTest {

	private static final int PERIOD = 100;
	
	private static final int THRESHOLD = 25;
	
	private static final int MAX_COUNT = 25;
	
	private SensorComponent sensorComponent;
	
	private DummyTransceiver transceiver;
	
	@Before
	public void setUp() {
		this.transceiver = new DummyTransceiver();
		
		this.sensorComponent = new SensorComponent(this.transceiver, PERIOD, THRESHOLD);
	}
	
	@Test
	public void testMeasurementCount() {
		
		Random rng = new Random();
		
		int measurementCount = rng.nextInt(MAX_COUNT-1) + 1;
		
		this.sensorComponent.start();
		
		try {
			Thread.sleep(PERIOD * measurementCount);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			Assert.fail();
			e.printStackTrace();
		}
		
		Assert.assertEquals(true, this.transceiver.callCounter >= measurementCount - 1);
		Assert.assertEquals(true, this.transceiver.callCounter <= measurementCount + 1);
		
	}
	
	@Test
	public void testMeasurementValues() {
		
		int measurementCount = MAX_COUNT;
		
		this.sensorComponent.start();
		
		try {
			Thread.sleep(PERIOD/10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			Assert.fail();
			e.printStackTrace();
		}
		
		for(int i=0; i<measurementCount; ++i) {
			try {
				Thread.sleep(PERIOD);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				Assert.fail();
				e.printStackTrace();
			}
			Assert.assertNotNull(transceiver.lastPacket);
			Assert.assertTrue(Double.parseDouble(this.transceiver.lastPacket.getValue()) >= SensorUtility.MEASUREMENT_MEAN - SensorUtility.MEASUREMENT_STD);
			Assert.assertTrue(Double.parseDouble(this.transceiver.lastPacket.getValue()) <= SensorUtility.MEASUREMENT_MEAN + SensorUtility.MEASUREMENT_STD);
			
		}
		
	}

}

class DummyTransceiver implements Transceiver {

	public int callCounter;
	
	public Packet lastPacket;
	
	@Override
	public void handlePacket(Packet packet) {
		this.lastPacket = packet;
		++callCounter;
	}
	
}
