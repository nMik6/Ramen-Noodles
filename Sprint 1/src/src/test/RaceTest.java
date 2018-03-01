package src.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import src.main.Race;
import src.main.Racer;
import src.main.Time;

class RaceTest {

	
	Race testRace;
	Racer testRacer1,testRacer2,testRacer3,testRacer4;
	Time testTime;
	
	@Before
	public void setup() {
		testRace = new Race();
		testRacer1 = new Racer(1);
		testRacer2 = new Racer(2);
		testRacer3 = new Racer(3);
		testRacer4 = new Racer(4);
		testTime = new Time();	
		
	}

	@Test
	void testCanceledRacer() {
		assertTrue(testRace.addReady(testRacer1));
		assertTrue(testRace.addReady(testRacer2));
		assertTrue(testRace.addReady(testRacer3));
		assertTrue(testRace.addReady(testRacer4));
		
		assertEquals(1,testRace.start(testTime, testRace.readyRacers.poll()));
		assertEquals(1,testRace.cancel(testRacer1));
		assertEquals(1,testRace.start(testTime, testRace.readyRacers.poll()));
	}
	
	@Test
	void testDnfRacer() {
		assertTrue(testRace.addReady(testRacer1));
		assertTrue(testRace.addReady(testRacer2));
		assertTrue(testRace.addReady(testRacer3));
		assertTrue(testRace.addReady(testRacer4));
		
		assertEquals(1,testRace.start(testTime, testRace.readyRacers.poll()));
		assertEquals(true,testRacer1.isRacing());
		assertEquals(1,testRacer1.dnf());
		assertEquals(false,testRacer1.isRacing());
	}
	
	//Can't start another race if finished (is this the expected behavior?)
	@Test
	void testSecondRun() {
		assertTrue(testRace.addReady(testRacer1));
		assertTrue(testRace.addReady(testRacer2));
		assertTrue(testRace.addReady(testRacer3));
		assertTrue(testRace.addReady(testRacer4));
		
		assertEquals(1,testRace.start(testTime, testRace.readyRacers.poll()));
		assertEquals(1,testRace.finish(testTime, testRace.readyRacers.poll()));
		assertEquals(0,testRace.finish(testTime, testRace.readyRacers.poll()));
	}

}
