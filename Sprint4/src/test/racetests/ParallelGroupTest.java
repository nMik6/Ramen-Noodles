package test.racetests;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import main.Time;
import main.racing.ParallelGroup;
import main.racing.Racer;

public class ParallelGroupTest {
	
	static ParallelGroup pargroupRace;
	
	@Before
	public void setup()	{
		pargroupRace = new ParallelGroup();
	}
	
	@Test
	/**
	 * Test Usecase: Race started and finished normally, see if server runs at end command
	 */
	public void test1() {
		pargroupRace.addReady(new Racer(111));
		pargroupRace.addReady(new Racer(222));
		pargroupRace.addReady(new Racer(333));
		pargroupRace.addReady(new Racer(444));
		pargroupRace.addReady(new Racer(555));
		pargroupRace.addReady(new Racer(666));
		pargroupRace.addReady(new Racer(777));
		pargroupRace.addReady(new Racer(888));
		
		pargroupRace.start(new Time());
		
		sleep(.2);
		pargroupRace.finish(1, new Time());
		sleep(.2);
		pargroupRace.finish(2, new Time());
		sleep(.2);
		pargroupRace.finish(3, new Time());
		sleep(.2);
		pargroupRace.finish(4, new Time());
		sleep(.2);
		pargroupRace.finish(5, new Time());
		sleep(.2);
		pargroupRace.finish(6, new Time());
		sleep(.2);
		pargroupRace.finish(7, new Time());
		sleep(.2);
		pargroupRace.finish(8, new Time());
		
		assertTrue(pargroupRace.getFinishedRacers().size() == 8);
		
		pargroupRace.end();
	}
	
	@Test
	/**
	 * Test Usecase: Start and end race with no racers entered
	 */
	public void test2() {
		pargroupRace.start(new Time());
		sleep(.2);
		pargroupRace.finish(1, new Time());
		sleep(.2);
		pargroupRace.finish(2, new Time());
		sleep(.2);
		pargroupRace.finish(3, new Time());
		pargroupRace.end();
		assertTrue(pargroupRace.getFinishedRacers().size() == 0);
	}
	
	@Test
	/**
	 * Test Usecase: Add ready racers and cancel ready racers
	 */
	public void test3() {
		pargroupRace.addReady(new Racer(111));
		pargroupRace.addReady(new Racer(222));
		pargroupRace.addReady(new Racer(333));
		pargroupRace.addReady(new Racer(444));
		pargroupRace.addReady(new Racer(555));
		pargroupRace.addReady(new Racer(666));
		pargroupRace.addReady(new Racer(777));
		pargroupRace.addReady(new Racer(888));

		assertFalse(pargroupRace.addReady(new Racer(111)));
		
		pargroupRace.addReady(new Racer(123));
		assertFalse(pargroupRace.containsBib(123));
		
		pargroupRace.cancel(111);
		assertFalse(pargroupRace.containsBib(111));
		//
		pargroupRace.start(new Time());
		sleep(.2);
		pargroupRace.finish(1, new Time());
		sleep(.2);
		pargroupRace.finish(2, new Time());
		sleep(.2);
		pargroupRace.finish(3, new Time());
		sleep(.2);
		pargroupRace.finish(4, new Time());
		sleep(.2);
		pargroupRace.finish(5, new Time());
		sleep(.2);
		pargroupRace.finish(6, new Time());
		sleep(.2);
		pargroupRace.finish(7, new Time());
		sleep(.2);
		pargroupRace.finish(8, new Time());
		pargroupRace.end();
		
		assertTrue(pargroupRace.getFinishedRacers().size() == 7);
	}
	
	@Test
	/**
	 * Tests DNF case.
	 */
	public void DNFtest() {
		pargroupRace.dnf();
		pargroupRace.finish(1, new Time());

		assertEquals(0, pargroupRace.getFinishedRacers().size());

		pargroupRace.addReady(new Racer(111));
		pargroupRace.addReady(new Racer(222));
		pargroupRace.addReady(new Racer(333));
		pargroupRace.addReady(new Racer(444));
		pargroupRace.addReady(new Racer(555));
		pargroupRace.addReady(new Racer(666));
		pargroupRace.addReady(new Racer(777));
		pargroupRace.addReady(new Racer(888));
		
		pargroupRace.finish(3, new Time());

		assertEquals(0, pargroupRace.getFinishedRacers().size());
		
		pargroupRace.start(1, new Time());
		
		pargroupRace.finish(3, new Time());

		assertEquals(1, pargroupRace.getFinishedRacers().size());
		assertEquals(1, pargroupRace.getDNFRacers().size());
		assertEquals(333, pargroupRace.getDNFRacers().get(0).getName());
		
		assertTrue(pargroupRace.finish(2, new Time()));
		pargroupRace.dnf();
		assertTrue(pargroupRace.finish(1, new Time()));
		assertFalse(pargroupRace.finish(1, new Time()));
		assertFalse(pargroupRace.finish(3, new Time()));
		assertTrue(pargroupRace.finish(4, new Time()));
		assertTrue(pargroupRace.finish(8, new Time()));
		pargroupRace.dnf();
		assertFalse(pargroupRace.finish(-1, new Time()));
		assertFalse(pargroupRace.finish(9, new Time()));
		assertTrue(pargroupRace.finish(7, new Time()));
		assertTrue(pargroupRace.finish(5, new Time()));
		pargroupRace.end();
		assertFalse(pargroupRace.finish(6, new Time()));
		
		//List<Racer> l = pargroupRace.getCurrentRacers();

		assertEquals(8, pargroupRace.getFinishedRacers().size());
		assertEquals(4, pargroupRace.getDNFRacers().size());
		
		assertEquals(333, pargroupRace.getDNFRacers().get(0).getName());
		assertEquals(111, pargroupRace.getDNFRacers().get(1).getName());
		assertEquals(777, pargroupRace.getDNFRacers().get(2).getName());
		assertEquals(666, pargroupRace.getDNFRacers().get(3).getName());

		assertEquals(333, pargroupRace.getFinishedRacers().get(0).getName());
		assertEquals(222, pargroupRace.getFinishedRacers().get(1).getName());
		assertEquals(111, pargroupRace.getFinishedRacers().get(2).getName());
		assertEquals(444, pargroupRace.getFinishedRacers().get(3).getName());
		assertEquals(888, pargroupRace.getFinishedRacers().get(4).getName());
		assertEquals(777, pargroupRace.getFinishedRacers().get(5).getName());
		assertEquals(555, pargroupRace.getFinishedRacers().get(6).getName());
		assertEquals(666, pargroupRace.getFinishedRacers().get(7).getName());
	}
	
	public static void sleep(double seconds) {
		try {
		    Thread.sleep((int)(seconds * 1000));
		} 
		catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
	}
	
}
