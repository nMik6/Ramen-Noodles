package src.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import src.main.Racer;
import src.main.Time;

class RacerTest {

	Racer testRacer;
	Time testTime;
	
	@Before
	public void setup() {
		testRacer = new Racer(123);
		testTime = new Time();		 
	}
	
	@Test
	void activeRacer() {
		assertFalse(testRacer.isRacing(), "Racing without start");
		assertEquals(1, testRacer.start(testTime),"Racer has started");
		assertTrue(testRacer.isRacing(), "Racer should be racing");
		
	}

}
