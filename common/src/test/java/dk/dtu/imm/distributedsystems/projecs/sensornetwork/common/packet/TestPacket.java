package dk.dtu.imm.distributedsystems.projecs.sensornetwork.common.packet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import junit.framework.Assert;

import org.junit.Test;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.Packet;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.packet.PacketType;

public class TestPacket {

	@Test
	public void testVariousPacketsSize() {
		
		this.testPacketSize(new Packet(PacketType.ACK));
		this.testPacketSize(new Packet(PacketType.ALM));
		
		this.testPacketSize(new Packet(PacketType.THR, "15"));
		
		this.testPacketSize(new Packet(PacketType.DAT, "very long data value"));
		this.testPacketSize(new Packet(PacketType.DAT, "very, very long data value"));
		this.testPacketSize(new Packet(PacketType.DAT, "very, very, veeeeeeeeeeeeeeeeeeeeery long data value"));
		
		
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
