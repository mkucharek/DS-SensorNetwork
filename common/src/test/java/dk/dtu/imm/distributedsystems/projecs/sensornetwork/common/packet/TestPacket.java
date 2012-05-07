package dk.dtu.imm.distributedsystems.projecs.sensornetwork.common.packet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import junit.framework.Assert;

import org.junit.Test;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.Packet;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.PacketGroup;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.PacketType;

public class TestPacket {

	@Test
	public void testVariousPacketsSize() {
		
		String dummyId = "0";
		
		this.testPacketSize(new Packet(dummyId, PacketType.ACK));
		this.testPacketSize(new Packet(dummyId, PacketType.ALM));
		
		this.testPacketSize(new Packet(dummyId, PacketType.THR, "15"));
		
		this.testPacketSize(new Packet(dummyId, PacketType.DAT, "very long data value"));
		this.testPacketSize(new Packet(dummyId, PacketType.DAT, "very, very long data value"));
		this.testPacketSize(new Packet(dummyId, PacketType.DAT, "very, very, veeeeeeeeeeeeeeeeeeeeery long data value"));
		
		
	}
	
	@Test
	public void testPacketIntegrity() {
		
		String dummyId = "11";
		
		Packet p = new Packet(dummyId, PacketType.DAT);
		
		Assert.assertEquals("11", p.getSrcNodeId());
		Assert.assertEquals("", p.getValue());
		Assert.assertEquals(PacketType.DAT, p.getType());
		Assert.assertEquals(PacketGroup.SENSOR_DATA, p.getGroup());
	}
	
	private void testPacketSize(Packet packet) {
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(baos);
			
			oos.writeObject(packet);
			oos.flush();
			
			Assert.assertTrue("The Packet size is " + baos.size(), baos.size() < 512);
			
			oos.close();
			baos.close();
			
		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail("Caught IO exception");
		}
	}

}
