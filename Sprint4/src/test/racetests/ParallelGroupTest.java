package test.racetests;
import static org.junit.Assert.*;
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

		assertTrue(pargroupRace.getFinishedRacers().size() == 0);

		pargroupRace.addReady(new Racer(111));
		pargroupRace.addReady(new Racer(222));
		pargroupRace.addReady(new Racer(333));
		pargroupRace.addReady(new Racer(444));
		pargroupRace.addReady(new Racer(555));
		pargroupRace.addReady(new Racer(666));
		pargroupRace.addReady(new Racer(777));
		pargroupRace.addReady(new Racer(888));
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
