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
		
		assertEquals(paraIndRace.getReadyRacers().size(), 0);
		assertEquals(paraIndRace.getCurrentRacers().size(), 3);
		assertTrue(paraIndRace.getCurrentRacersCh1().size() == 1);
		assertTrue(paraIndRace.getCurrentRacersCh1().peek().equals(racer3));
		assertTrue(paraIndRace.getCurrentRacersCh3().size() == 2);
		assertTrue(paraIndRace.getCurrentRacersCh3().peek().equals(racer2));
		assertEquals(paraIndRace.getFinishedRacers().size(), 1);
		assertTrue(paraIndRace.getFinishedRacers().get(0).equals(racer1));
		
		paraIndRace.finish(4, new Time());
		
		assertEquals(paraIndRace.getReadyRacers().size(), 0);
		assertEquals(paraIndRace.getCurrentRacers().size(), 2);
		assertTrue(paraIndRace.getCurrentRacersCh1().size() == 1);
		assertTrue(paraIndRace.getCurrentRacersCh1().peek().equals(racer3));
		assertTrue(paraIndRace.getCurrentRacersCh3().size() == 1);
		assertTrue(paraIndRace.getCurrentRacersCh3().peek().equals(racer4));
		assertEquals(paraIndRace.getFinishedRacers().size(), 2);
		assertTrue(paraIndRace.getFinishedRacers().get(0).equals(racer1));
		assertTrue(paraIndRace.getFinishedRacers().get(1).equals(racer2));

		paraIndRace.finish(4, new Time());
		
		assertEquals(paraIndRace.getReadyRacers().size(), 0);
		assertEquals(paraIndRace.getCurrentRacers().size(), 1);
		assertTrue(paraIndRace.getCurrentRacersCh1().size() == 1);
		assertTrue(paraIndRace.getCurrentRacersCh1().peek().equals(racer3));
		assertTrue(paraIndRace.getCurrentRacersCh3().size() == 0);
		assertEquals(paraIndRace.getFinishedRacers().size(), 3);
		assertTrue(paraIndRace.getFinishedRacers().get(0).equals(racer1));
		assertTrue(paraIndRace.getFinishedRacers().get(1).equals(racer2));
		assertTrue(paraIndRace.getFinishedRacers().get(2).equals(racer4));

		paraIndRace.finish(2, new Time());
		
		assertEquals(paraIndRace.getReadyRacers().size(), 0);
		assertEquals(paraIndRace.getCurrentRacers().size(), 0);
		assertTrue(paraIndRace.getCurrentRacersCh1().size() == 0);
		assertTrue(paraIndRace.getCurrentRacersCh3().size() == 0);
		assertEquals(paraIndRace.getFinishedRacers().size(), 4);
		assertTrue(paraIndRace.getFinishedRacers().get(0).equals(racer1));
		assertTrue(paraIndRace.getFinishedRacers().get(1).equals(racer2));
		assertTrue(paraIndRace.getFinishedRacers().get(2).equals(racer4));
		assertTrue(paraIndRace.getFinishedRacers().get(3).equals(racer3));
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
	
	/**
	 * Testing Use Case: set dnf
	 */
	@Test
	void testDNF() {
		assertTrue(paraIndRace.addReady(racer1));
		assertTrue(paraIndRace.addReady(racer2));
		assertTrue(paraIndRace.addReady(racer3));
		assertTrue(paraIndRace.addReady(racer4));
		assertEquals(paraIndRace.getReadyRacers().size(), 4);
		assertEquals(paraIndRace.getCurrentRacers().size(), 0);
		assertEquals(paraIndRace.getFinishedRacers().size(), 0);
		assertEquals(paraIndRace.getDNFRacers().size(), 0);
		assertTrue(paraIndRace.getReadyRacers().peek().equals(racer1));
		
		paraIndRace.dnf(racer4);
		assertEquals(paraIndRace.getReadyRacers().size(), 3);
		assertEquals(paraIndRace.getCurrentRacers().size(), 0);
		assertEquals(paraIndRace.getFinishedRacers().size(), 1);
		assertEquals(paraIndRace.getDNFRacers().size(), 1);
		assertTrue(paraIndRace.getReadyRacers().peek().equals(racer1));
		
		paraIndRace.start(1, new Time());
		assertEquals(paraIndRace.getCurrentRacers().size(), 1);
		assertEquals(paraIndRace.getFinishedRacers().size(), 1);
		assertEquals(paraIndRace.getDNFRacers().size(), 1);
		assertTrue(paraIndRace.getCurrentRacers().peek().equals(racer1));
		
		paraIndRace.dnf(null);
		assertEquals(paraIndRace.getCurrentRacers().size(), 1);
		assertEquals(paraIndRace.getFinishedRacers().size(), 1);
		assertEquals(paraIndRace.getDNFRacers().size(), 1);
		assertTrue(paraIndRace.getCurrentRacers().peek().equals(racer1));

		paraIndRace.dnf(new Racer(1));
		assertEquals(paraIndRace.getCurrentRacers().size(), 0);
		assertEquals(paraIndRace.getFinishedRacers().size(), 2);
		assertEquals(paraIndRace.getDNFRacers().size(), 2);

		paraIndRace.start(1, new Time());
		paraIndRace.start(3, new Time());
		assertEquals(paraIndRace.getCurrentRacers().size(), 2);
		assertEquals(paraIndRace.getFinishedRacers().size(), 2);
		assertEquals(paraIndRace.getDNFRacers().size(), 2);
		
		paraIndRace.finish(2, new Time());
		assertEquals(paraIndRace.getCurrentRacers().size(), 1);
		assertEquals(paraIndRace.getFinishedRacers().size(), 3);
		assertEquals(paraIndRace.getDNFRacers().size(), 2);
		assertTrue(paraIndRace.getCurrentRacers().peek().equals(racer2));
		assertTrue(paraIndRace.getFinishedRacers().get(2).equals(racer3));

		paraIndRace.dnf(racer3);
		assertEquals(paraIndRace.getCurrentRacers().size(), 1);
		assertEquals(paraIndRace.getFinishedRacers().size(), 3);
		assertEquals(paraIndRace.getDNFRacers().size(), 2);
		assertTrue(paraIndRace.getCurrentRacers().peek().equals(racer2));
		assertTrue(paraIndRace.getFinishedRacers().get(2).equals(racer3));
		
		paraIndRace.dnf(racer2);
		assertEquals(paraIndRace.getCurrentRacers().size(), 0);
		assertEquals(paraIndRace.getFinishedRacers().size(), 4);
		assertEquals(paraIndRace.getDNFRacers().size(), 3);
		assertTrue(paraIndRace.getFinishedRacers().get(2).equals(racer3));
		
		assertFalse(paraIndRace.finish(4, new Time()));
	}
}
