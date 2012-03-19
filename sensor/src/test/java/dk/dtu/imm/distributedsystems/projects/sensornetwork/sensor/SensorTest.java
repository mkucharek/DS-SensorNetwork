package dk.dtu.imm.distributedsystems.projects.sensornetwork.sensor;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SensorTest {

	private int x;
	private int y;
	
	@Before
	public void setup() {
		x=4;
		y=3;
				
	}
	@Test
	public void test() {
		assertTrue(x>y);
	}
	
	@Test
	public void test2() {
		assertTrue(x>3);
	}
	

}
