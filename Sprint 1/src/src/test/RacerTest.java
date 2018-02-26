package src.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import src.main.Racer;
import src.main.Time;

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
	void activeRacer() {
		assertFalse(testRacer.isRacing(), "Racing without start");
		assertEquals(1, testRacer.start(testTime),"Racer has started");
		assertTrue(testRacer.isRacing(), "Racer should be racing");
		assertEquals(1, testRacer.finish(testTimeFinish), "Racer has finished");
		assertEquals(testRacer.getTotal().getClass(), testTime.getClass(),"Get Total returned a time object"); //Not sure this works, but want to ensure getTotal returns a time object.
		
			
	}
	//Tests did not finish.
	@Test
	void racerDNF() {
		assertFalse(testRacer.isRacing(), "Racing without start");
		assertEquals(1, testRacer.start(testTime),"Racer has started");
		assertTrue(testRacer.isRacing(), "Racer should be racing");
		assertEquals(1, testRacer.dnf(), "Racer did not finish");
		assertTrue(testRacer.didNotFinish(), "Racer should not have finished");
		assertEquals(null, testRacer.getTotal(), "Cannot get total time because Racer did not finish");
		
	}
	
	@Test
	void getTotalEarly() {
		assertEquals(1, testRacer.start(testTime), "Racer starting now");
		assertTrue(testRacer.isRacing(), "Racer should be racing");
		assertEquals(null, testRacer.getTotal(), "Cannot get total time because Racer is still racing!");
		assertEquals(1, testRacer.finish(testTimeFinish), "Racer has finished!");
		assertEquals(testRacer.getTotal().getClass(), testTime.getClass(),"Get Total returned a time object");

}
