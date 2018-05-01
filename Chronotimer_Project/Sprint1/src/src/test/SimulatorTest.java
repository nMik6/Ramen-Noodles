package src.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.main.Simulator;
import src.main.Time;

class SimulatorTest {
	
	Simulator testSim;
	
	
	@BeforeEach
	public void setup(){
		testSim = new Simulator();
	}

	@Test
	void powerOn() {
		assertFalse(testSim.getPower());
		testSim.power();
		assertTrue(testSim.getPower());
		testSim.power();
		assertFalse(testSim.getPower());
	}
	
	@Test
	void time() {
		Time time1 = new Time("12:23:34.1");
		Time time2 = new Time();
		testSim.time(time1.printTime());
		assertEquals(time2.difference(time1).printTime(),testSim.getOffset().printTime());
	}

}
