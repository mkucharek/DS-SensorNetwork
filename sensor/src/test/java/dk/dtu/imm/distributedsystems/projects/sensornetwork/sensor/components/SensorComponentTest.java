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
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.PacketType;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.SensorUtility;

/**
 * The Class SensorComponentTest.
 *
 * @author Maciej Kucharek <a href="mailto:s091828 (at) student.dtu.dk">s091828 (at) student.dtu.dk</a>
 */
public class SensorComponentTest {

	/** The Constant ID. */
	private static final String ID = "0";
	
	/** The Constant PERIOD. */
	private static final int PERIOD = 100;
	
	/** The Constant THRESHOLD. */
	private static final int THRESHOLD = 25;
	
	/** The Constant MAX_COUNT. */
	private static final int MAX_COUNT = 25;
	
	/** The sensor component. */
	private SensorComponent sensorComponent;
	
	/** The transceiver. */
	private DummyTransceiver transceiver;
	
	/**
	 * Sets the up.
	 */
	@Before
	public void setUp() {
		// setting up a dummy transceiver with no listeners nor sender
		this.transceiver = new DummyTransceiver(ID, null, null);
		
		
	}
	
	/**
	 * Clean up.
	 */
	@After
	public void cleanUp() {
		this.transceiver.close();
		this.sensorComponent.interrupt();
	}
	
	/**
	 * Test measurement count.
	 */
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
	
	/**
	 * Test measurement count.
	 */
	@Test
	public void testChangeThr() {
		
		final int HUGE_THR = 100;
		
		// very high thr - should generate DAT
		this.sensorComponent = new SensorComponent(ID, this.transceiver, PERIOD, HUGE_THR);
		
		// changing the thr immediately - should now generate ALM
		this.sensorComponent.setThreshold(-HUGE_THR);
		
		try {
			Thread.sleep(PERIOD/2*3);
		} catch (InterruptedException e) {
			Assert.fail();
			e.printStackTrace();
		}
		
		Assert.assertEquals(1, this.transceiver.getCallCounter());
		
		Assert.assertEquals(PacketType.ALM, this.transceiver.getLastPacket().getType());
		
	}
	
	/**
	 * Test measurement values.
	 */
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
