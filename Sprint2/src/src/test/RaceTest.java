package src.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.main.Race;
import src.main.Racer;
import src.main.Time;

class RaceTest {

	
	Race testRace;
	Racer testRacer1,testRacer2,testRacer3,testRacer4;
	Time testTime;
	
	@BeforeEach
	public void setup() {
		testRace = new Race();
		testRacer1 = new Racer(1);
		testRacer2 = new Racer(2);
		testRacer3 = new Racer(3);
		testRacer4 = new Racer(4);
		testTime = new Time();	
		
	}

	@Test
	public void testCanceledRacer() {
		assertFalse(testRace == null);
		
		assertTrue(testRace.addReady(testRacer1));
		assertTrue(testRace.addReady(testRacer2));
		assertTrue(testRace.addReady(testRacer3));
		assertTrue(testRace.addReady(testRacer4));
		
		//assertEquals(1,testRace.start(testTime, testRace.readyRacers.poll()));
		//assertEquals(1,testRace.cancel(testRacer1));
		//assertEquals(1,testRace.start(testTime, testRace.readyRacers.poll()));
		
		assertTrue(testRace.getReadyRacers().size() == 4);
		assertTrue(testRace.getCurrentRacers().size() == 0);
		assertTrue(testRace.getFinishedRacers().size() == 0);
		
		assertTrue(testRace.getReadyRacers().peek().getName() == 1);
		
		testRace.start(1, testTime);
		
		assertTrue(testRace.getReadyRacers().size() == 3);
		assertTrue(testRace.getCurrentRacers().size() == 1);
		assertTrue(testRace.getFinishedRacers().size() == 0);
		
		assertTrue(testRace.getReadyRacers().peek().getName() == 2);
		assertTrue(testRace.getCurrentRacers().peek().getName() == 1);
		
		assertFalse(testRace.cancel(testRacer1));
		
		assertTrue(testRace.getReadyRacers().size() == 3);
		assertTrue(testRace.getCurrentRacers().size() == 1);
		assertTrue(testRace.getFinishedRacers().size() == 0);
		
		assertTrue(testRace.getReadyRacers().peek().getName() == 2);
		assertTrue(testRace.getCurrentRacers().peek().getName() == 1);
		
		assertTrue(testRace.cancel(testRacer2));
		
		assertTrue(testRace.getReadyRacers().size() == 2);
		assertTrue(testRace.getCurrentRacers().size() == 1);
		assertTrue(testRace.getFinishedRacers().size() == 0);
		
		assertTrue(testRace.getReadyRacers().peek().getName() == 3);
		assertTrue(testRace.getCurrentRacers().peek().getName() == 1);
		
		/*testRace.start(1, testTime);
		
		assertTrue(testRace.getReadyRacers().size() == 3);
		assertTrue(testRace.getCurrentRacers().size() == 1);
		assertTrue(testRace.getFinishedRacers().size() == 0);
		
		assertTrue(testRace.getReadyRacers().peek().getName() == 3);
		assertTrue(testRace.getCurrentRacers().peek().getName() == 2);/**/
		
	}
	
	@Test
	public void testDnfRacer() {
		assertFalse(testRace == null);
		
		assertTrue(testRace.addReady(testRacer1));
		assertTrue(testRace.addReady(testRacer2));
		assertTrue(testRace.addReady(testRacer3));
		assertTrue(testRace.addReady(testRacer4));

		testRace.start(1, testTime);
		//assertEquals(1,testRace.start(testTime, testRace.readyRacers.poll()));
		assertEquals(true,testRacer1.isRacing());
		assertEquals(1,testRacer1.dnf());
		assertEquals(false,testRacer1.isRacing());
	}
	
	//Can't start another race if finished (is this the expected behavior?)
	@Test
	public void testSecondRun() {
		assertFalse(testRace == null);
		
		assertTrue(testRace.addReady(testRacer1));
		assertTrue(testRace.addReady(testRacer2));
		assertTrue(testRace.addReady(testRacer3));
		assertTrue(testRace.addReady(testRacer4));

		//TODO fix this code
		//assertEquals(1,testRace.start(testTime, testRace.readyRacers.poll()));
		//assertEquals(1,testRace.finish(testTime, testRace.readyRacers.poll()));
		//assertEquals(0,testRace.finish(testTime, testRace.readyRacers.poll()));
	}
	
	@Test
	public void indParTest() {
		assertFalse(testRace.getType());
		testRace.setType("PARIND");
		assertTrue(testRace.getType());
		
		assertTrue(testRace.getReadyRacers().size() == 0);
		assertTrue(testRace.getCurrentRacers().size() == 0);
		assertTrue(testRace.getFinishedRacers().size() == 0);
		
		assertTrue(testRace.addReady(testRacer1));
		assertTrue(testRace.addReady(testRacer2));
		assertTrue(testRace.addReady(testRacer3));
		assertTrue(testRace.addReady(testRacer4));
		
		assertTrue(testRace.getReadyRacers().size() == 4);
		assertTrue(testRace.getCurrentRacers().size() == 0);
		assertTrue(testRace.getFinishedRacers().size() == 0);

		testRace.start(1, testTime);

		assertTrue(testRace.getReadyRacers().size() == 3);
		assertTrue(testRace.getCurrentRacers().size() == 1);
		assertTrue(testRace.getFinishedRacers().size() == 0);
		
		testRace.start(3, testTime);
		
		assertTrue(testRace.getReadyRacers().size() == 2);
		assertTrue(testRace.getCurrentRacers().size() == 2);
		assertTrue(testRace.getFinishedRacers().size() == 0);
		
		assertTrue(testRace.getCurrentRacers().peek().getName() == 1);
		
		testRace.finish(4, new Time("12:00:00.0"));
		
		assertTrue(testRace.getReadyRacers().size() == 2);
		assertTrue(testRace.getCurrentRacers().size() == 1);
		assertTrue(testRace.getFinishedRacers().size() == 1);
		
		assertTrue(testRace.getCurrentRacers().peek().getName() == 1);
		assertTrue(testRace.getFinishedRacers().get(0).getName() == 2);
		
		testRace.finish(2, new Time("14:00:00.0"));
		
		assertTrue(testRace.getReadyRacers().size() == 2);
		assertTrue(testRace.getCurrentRacers().size() == 0);
		assertTrue(testRace.getFinishedRacers().size() == 2);

		assertTrue(testRace.getFinishedRacers().get(0).getName() == 2);
		assertTrue(testRace.getFinishedRacers().get(1).getName() == 1);

		testRace.start(1, testTime);
		
		assertTrue(testRace.getReadyRacers().size() == 1);
		assertTrue(testRace.getCurrentRacers().size() == 1);
		assertTrue(testRace.getFinishedRacers().size() == 2);
		
		assertTrue(testRace.getCurrentRacers().peek().getName() == 3);

		testRace.start(3, testTime);
		
		assertTrue(testRace.getReadyRacers().size() == 0);
		assertTrue(testRace.getCurrentRacers().size() == 2);
		assertTrue(testRace.getFinishedRacers().size() == 2);
		
		assertTrue(testRace.getCurrentRacers().peek().getName() == 3);
		
		testRace.finish(2, new Time("16:00:00.0"));
		
		assertTrue(testRace.getReadyRacers().size() == 0);
		assertTrue(testRace.getCurrentRacers().size() == 1);
		assertTrue(testRace.getFinishedRacers().size() == 3);

		assertTrue(testRace.getCurrentRacers().peek().getName() == 4);
		assertTrue(testRace.getFinishedRacers().get(2).getName() == 3);
		
		testRace.finish(4, new Time("18:00:00.0"));
		
		assertTrue(testRace.getReadyRacers().size() == 0);
		assertTrue(testRace.getCurrentRacers().size() == 0);
		assertTrue(testRace.getFinishedRacers().size() == 4);

		assertTrue(testRace.getFinishedRacers().get(2).getName() == 3);
		assertTrue(testRace.getFinishedRacers().get(3).getName() == 4);
	}
	
	@Test
	public void indParInvalidTest() {
		assertFalse(testRace.getType());
		testRace.setType("INVALID");
		assertFalse(testRace.getType());
		testRace.setType("PARIND");
		assertTrue(testRace.getType());
		testRace.setType("INVALID");
		assertFalse(testRace.getType());
		testRace.setType("PARIND");
		assertTrue(testRace.getType());

		assertFalse(testRace.addReady(null));
		assertTrue(testRace.addReady(testRacer1));
		assertFalse(testRace.addReady(testRacer1));
		assertTrue(testRace.addReady(testRacer2));
		assertTrue(testRace.addReady(testRacer3));
		assertTrue(testRace.addReady(testRacer4));
		assertFalse(testRace.addReady(testRacer4));
	}

}
