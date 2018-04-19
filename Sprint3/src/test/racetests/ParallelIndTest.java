package test.racetests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.racing.Racer;
import main.racing.ParallelIndividual;
import main.Time;

class ParallelIndTest {
	Racer racer1, racer2, racer3, racer4;
	ParallelIndividual paraIndRace; 
	
	@BeforeEach
	void setUp(){
		paraIndRace = new ParallelIndividual();
		racer1 = new Racer(1);
		racer2 = new Racer(2);
		racer3 = new Racer(3);
		racer4 = new Racer(4);
	}

	/**
	 * Testing Use Case: add racers, start racers, finish racers (normal race)
	 */
	@Test
	void test0() {
		assertEquals(paraIndRace.getReadyRacers().size(), 0);
		assertTrue(paraIndRace.getReadyRacersCh1().size() == 0);
		assertTrue(paraIndRace.getReadyRacersCh3().size() == 0);
		assertEquals(paraIndRace.getCurrentRacers().size(), 0);
		assertTrue(paraIndRace.getCurrentRacersCh1().size() == 0);
		assertTrue(paraIndRace.getCurrentRacersCh3().size() == 0);
		assertEquals(paraIndRace.getFinishedRacers().size(), 0);

		assertFalse(paraIndRace.containsBib(1));
		assertFalse(paraIndRace.addReady(null));
		assertTrue(paraIndRace.addReady(racer1));
		assertFalse(paraIndRace.addReady(racer1));
		assertFalse(paraIndRace.addReady(new Racer (1)));
		assertTrue(paraIndRace.containsBib(1));

		assertEquals(paraIndRace.getReadyRacers().size(), 1);
		assertTrue(paraIndRace.getReadyRacers().peek().equals(racer1));
		assertTrue(paraIndRace.getReadyRacersCh1().size() == 1);
		assertTrue(paraIndRace.getReadyRacersCh3().size() == 0);
		assertEquals(paraIndRace.getCurrentRacers().size(), 0);
		assertEquals(paraIndRace.getFinishedRacers().size(), 0);
		
		assertTrue(paraIndRace.addReady(racer2));

		assertTrue(paraIndRace.getReadyRacersCh1().size() == 1);
		assertTrue(paraIndRace.getReadyRacersCh1().peek().equals(racer1));
		assertTrue(paraIndRace.getReadyRacersCh3().size() == 1);
		assertTrue(paraIndRace.getReadyRacersCh3().peek().equals(racer2));
		assertEquals(paraIndRace.getReadyRacers().size(), 2);
		assertEquals(paraIndRace.getCurrentRacers().size(), 0);
		assertEquals(paraIndRace.getFinishedRacers().size(), 0);
		
		assertTrue(paraIndRace.addReady(racer3));
		
		assertEquals(paraIndRace.getReadyRacers().size(), 3);
		assertTrue(paraIndRace.getReadyRacersCh1().size() == 2);
		assertTrue(paraIndRace.getReadyRacersCh3().size() == 1);
		assertEquals(paraIndRace.getCurrentRacers().size(), 0);
		assertEquals(paraIndRace.getFinishedRacers().size(), 0);
		
		assertTrue(paraIndRace.addReady(racer4));
		
		assertEquals(paraIndRace.getReadyRacers().size(), 4);
		assertTrue(paraIndRace.getReadyRacersCh1().size() == 2);
		assertTrue(paraIndRace.getReadyRacersCh3().size() == 2);
		assertEquals(paraIndRace.getCurrentRacers().size(), 0);
		assertEquals(paraIndRace.getFinishedRacers().size(), 0);
		
		paraIndRace.start(1, new Time());

		assertEquals(paraIndRace.getReadyRacers().size(), 3);
		assertTrue(paraIndRace.getReadyRacersCh1().size() == 1);
		assertTrue(paraIndRace.getReadyRacersCh1().peek().equals(racer3));
		assertTrue(paraIndRace.getReadyRacersCh3().size() == 2);
		assertTrue(paraIndRace.getReadyRacersCh3().peek().equals(racer2));
		assertEquals(paraIndRace.getCurrentRacers().size(), 1);
		assertTrue(paraIndRace.getCurrentRacersCh1().size() == 1);
		assertTrue(paraIndRace.getCurrentRacersCh1().peek().equals(racer1));
		assertTrue(paraIndRace.getCurrentRacersCh3().size() == 0);
		assertEquals(paraIndRace.getFinishedRacers().size(), 0);
		
		paraIndRace.start(3, new Time());

		assertEquals(paraIndRace.getReadyRacers().size(), 2);
		assertTrue(paraIndRace.getReadyRacersCh1().size() == 1);
		assertTrue(paraIndRace.getReadyRacersCh1().peek().equals(racer3));
		assertTrue(paraIndRace.getReadyRacersCh3().size() == 1);
		assertTrue(paraIndRace.getReadyRacersCh3().peek().equals(racer4));
		assertEquals(paraIndRace.getCurrentRacers().size(), 2);
		assertTrue(paraIndRace.getCurrentRacersCh1().size() == 1);
		assertTrue(paraIndRace.getCurrentRacersCh1().peek().equals(racer1));
		assertTrue(paraIndRace.getCurrentRacersCh3().size() == 1);
		assertTrue(paraIndRace.getCurrentRacersCh3().peek().equals(racer2));
		assertEquals(paraIndRace.getFinishedRacers().size(), 0);

		paraIndRace.start(3, new Time());
		
		assertEquals(paraIndRace.getReadyRacers().size(), 1);
		assertTrue(paraIndRace.getReadyRacersCh1().size() == 1);
		assertTrue(paraIndRace.getReadyRacersCh1().peek().equals(racer3));
		assertTrue(paraIndRace.getReadyRacersCh3().size() == 0);
		assertEquals(paraIndRace.getCurrentRacers().size(), 3);
		assertTrue(paraIndRace.getCurrentRacersCh1().size() == 1);
		assertTrue(paraIndRace.getCurrentRacersCh1().peek().equals(racer1));
		assertTrue(paraIndRace.getCurrentRacersCh3().size() == 2);
		assertTrue(paraIndRace.getCurrentRacersCh3().peek().equals(racer2));
		assertEquals(paraIndRace.getFinishedRacers().size(), 0);
		
		paraIndRace.start(1, new Time());
		
		assertEquals(paraIndRace.getReadyRacers().size(), 0);
		assertTrue(paraIndRace.getReadyRacersCh1().size() == 0);
		assertTrue(paraIndRace.getReadyRacersCh3().size() == 0);
		assertEquals(paraIndRace.getCurrentRacers().size(), 4);
		assertTrue(paraIndRace.getCurrentRacersCh1().size() == 2);
		assertTrue(paraIndRace.getCurrentRacersCh1().peek().equals(racer1));
		assertTrue(paraIndRace.getCurrentRacersCh3().size() == 2);
		assertTrue(paraIndRace.getCurrentRacersCh3().peek().equals(racer2));
		assertEquals(paraIndRace.getFinishedRacers().size(), 0);
		
		paraIndRace.finish(2, new Time());
		paraIndRace.finish(4, new Time());
		
		assertEquals(paraIndRace.getReadyRacers().size(), 0);
		assertEquals(paraIndRace.getCurrentRacers().size(), 2);
		assertEquals(paraIndRace.getFinishedRacers().size(), 2);
		
		paraIndRace.finish(2, new Time());
		paraIndRace.finish(4, new Time());
		
		assertEquals(paraIndRace.getReadyRacers().size(), 0);
		assertEquals(paraIndRace.getCurrentRacers().size(), 0);
		assertEquals(paraIndRace.getFinishedRacers().size(), 4);
	}
	
	/**
	 * Testing Use Case: add null, add racer twice, end race, set dnf
	 */
	@Test
	void test1() {
		assertFalse(paraIndRace.addReady(null));
		assertTrue(paraIndRace.addReady(racer1));
		assertFalse(paraIndRace.addReady(racer1));
	}
}
