package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import main.Time;
import main.racing.Racer;

class RacerTest {

	Racer testRacer;
	Time testTime;
	Time testTimeFinish;
	
	@Before
	public void setup() {
		testRacer = new Racer(123);
		testTime = new Time();
		testTimeFinish = new Time();
	}
	
	//Creates Racer, starts and finishes the racer, ensuring a basic run will succeed.
	@Test
	public void activeRacer() {
		testRacer = new Racer(123);
		testTime = new Time();
		testTimeFinish = new Time();
		assertFalse(testRacer.isRacing(), "Racing without start");
		assertEquals(1, testRacer.start(testTime),"Racer has started");
		assertTrue(testRacer.isRacing(), "Racer should be racing");
		assertEquals(1, testRacer.finish(testTimeFinish), "Racer has finished");
	
		
			
	}
	//Tests did not finish.
	@Test
	public void racerDNF() {
		testRacer = new Racer(123);
		testTime = new Time();
		testTimeFinish = new Time();
		assertFalse(testRacer.isRacing(), "Racing without start");
		assertEquals(1, testRacer.start(testTime),"Racer has started");
		assertTrue(testRacer.isRacing(), "Racer should be racing");
		assertEquals(1, testRacer.setDnf(), "Racer did not finish");
		assertTrue(testRacer.getDnf(), "Racer should not have finished");
		assertEquals(null, testRacer.getTotal(), "Cannot get total time because Racer did not finish");
		
	}
	
	@Test
	public void getTotalEarly() {
		testRacer = new Racer(123);
		testTime = new Time();
		testTimeFinish = new Time();
		assertEquals(1, testRacer.start(testTime), "Racer starting now");
		assertTrue(testRacer.isRacing(), "Racer should be racing");
		assertTrue(testRacer.getTotal() == null, "Cannot get total time because Racer is still racing!");
		assertEquals(1, testRacer.finish(testTimeFinish), "Racer has finished!");
		
	}
}
