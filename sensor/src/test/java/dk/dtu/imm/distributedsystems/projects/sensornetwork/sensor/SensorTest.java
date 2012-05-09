package dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor;

import static org.junit.Assert.assertEquals;
import junit.framework.Assert;

import org.junit.Test;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.exceptions.NodeInitializationException;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor.sender.UdpPortSender;

/**
 * The Class SensorTest.
 *
 * @author Maciej Kucharek <a href="mailto:s091828 (at) student.dtu.dk">s091828 (at) student.dtu.dk</a>
 */
public class SensorTest {
	
	/**
	 * Test properties sensor11.
	 */
	@Test
	public void testPropertiesSensor11() {
		
		Sensor sensor11 = null;
		try {
			sensor11 = SensorUtility.getSensorInstance("sensor11-test.properties", true);
		} catch (NodeInitializationException e) {
			Assert.fail();
		}
		
		assertEquals("11", sensor11.getId());
		assertEquals(10000, sensor11.getSensorComponent().getPeriod());
		assertEquals(40, sensor11.getSensorComponent().getThreshold());
		
		assertEquals("0", (((UdpPortSender) sensor11.getTransceiverComponent().getPortSender()).getLeftChannels()[0].getId()));
		
		assertEquals("127.0.0.1", (((UdpPortSender) sensor11.getTransceiverComponent().getPortSender()).getLeftChannels()[0].getIpAddress()));
		
		assertEquals(9901, (((UdpPortSender) sensor11.getTransceiverComponent().getPortSender()).getLeftChannels()[0].getPortNumber()));
		
		assertEquals("21", (((UdpPortSender) sensor11.getTransceiverComponent().getPortSender()).getRightChannels()[0].getId()));
		assertEquals("22", (((UdpPortSender) sensor11.getTransceiverComponent().getPortSender()).getRightChannels()[1].getId()));
		assertEquals("23", (((UdpPortSender) sensor11.getTransceiverComponent().getPortSender()).getRightChannels()[2].getId()));
		
		assertEquals("127.0.0.1", (((UdpPortSender) sensor11.getTransceiverComponent().getPortSender()).getRightChannels()[0].getIpAddress()));
		assertEquals("127.0.0.1", (((UdpPortSender) sensor11.getTransceiverComponent().getPortSender()).getRightChannels()[1].getIpAddress()));
		assertEquals("127.0.0.1", (((UdpPortSender) sensor11.getTransceiverComponent().getPortSender()).getRightChannels()[2].getIpAddress()));
		
		assertEquals(9921, (((UdpPortSender) sensor11.getTransceiverComponent().getPortSender()).getRightChannels()[0].getPortNumber()));
		assertEquals(9922, (((UdpPortSender) sensor11.getTransceiverComponent().getPortSender()).getRightChannels()[1].getPortNumber()));
		assertEquals(9923, (((UdpPortSender) sensor11.getTransceiverComponent().getPortSender()).getRightChannels()[2].getPortNumber()));

	}
	
}
