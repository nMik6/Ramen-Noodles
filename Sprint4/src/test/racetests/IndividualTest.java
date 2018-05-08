package test.racetests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.racing.Racer;
import main.racing.Individual;
import main.Time;


class IndividualTest {
	
	Racer racer1, racer2, racer3, racer4;
	Individual indRace;
	
	@BeforeEach
	void setUp(){
		racer1 = new Racer(1);
		racer2 = new Racer(2);
		racer3 = new Racer(3);
		racer3 = new Racer(4);

		indRace = new Individual();
	}
	
	/**
	 * Testing Use Case: start and end racer without interruption
	 */
	@Test
	void test0() {
		assertEquals(indRace.getReadyRacers().size(), 0);
		assertEquals(indRace.getCurrentRacers().size(), 0);
		assertEquals(indRace.getFinishedRacers().size(), 0);
		
		indRace.addReady(racer1);
		
		assertEquals(indRace.getReadyRacers().size(), 1);
		assertEquals(indRace.getCurrentRacers().size(), 0);
		assertEquals(indRace.getFinishedRacers().size(), 0);
		
		indRace.start(1, new Time());
		
		assertEquals(indRace.getReadyRacers().size(), 0);
		assertEquals(indRace.getCurrentRacers().size(), 1);
		assertEquals(indRace.getFinishedRacers().size(), 0);
		
		indRace.finish(2, new Time());
		
		assertEquals(indRace.getReadyRacers().size(), 0);
		assertEquals(indRace.getCurrentRacers().size(), 0);
		assertEquals(indRace.getFinishedRacers().size(), 1);
		
	}

	/**
	 * Testing Use Case: start and end race without racer finishing, check all racers auto dnf
	 */
	@Test
	void test1() {
		indRace.addReady(racer1);
		assertEquals(indRace.getReadyRacers().size(), 1);
		assertEquals(indRace.getCurrentRacers().size(), 0);
		assertEquals(indRace.getFinishedRacers().size(), 0);
		
		indRace.start(1, new Time());
		assertEquals(indRace.getReadyRacers().size(), 0);
		assertEquals(indRace.getCurrentRacers().size(), 1);
		assertEquals(indRace.getFinishedRacers().size(), 0);
		
		indRace.end();
		assertEquals(indRace.getReadyRacers().size(), 0);
		assertEquals(indRace.getCurrentRacers().size(), 0);
	
		List<Racer> finished = indRace.getFinishedRacers();
		
		for(Racer r: finished) {
			assertTrue(r.getDnf());
		}
	}
	
	/**
	 * Testing Use Case: Start race and dnf racer
	 */
	@Test
	void test2() {
		indRace.addReady(racer1);
		assertEquals(indRace.getReadyRacers().size(), 1);
		assertEquals(indRace.getCurrentRacers().size(), 0);
		assertEquals(indRace.getFinishedRacers().size(), 0);
		assertTrue(indRace.getReadyRacers().peek().equals(racer1));

		indRace.dnf();
		
		assertEquals(indRace.getReadyRacers().size(), 1);
		assertEquals(indRace.getCurrentRacers().size(), 0);
		assertEquals(indRace.getFinishedRacers().size(), 0);
		assertTrue(indRace.getReadyRacers().peek().equals(racer1));
		
		indRace.addReady(racer2);
		assertEquals(indRace.getReadyRacers().size(), 2);
		assertEquals(indRace.getCurrentRacers().size(), 0);
		assertEquals(indRace.getFinishedRacers().size(), 0);
		
		indRace.start(1, new Time());
		
		assertEquals(indRace.getReadyRacers().size(), 1);
		assertEquals(indRace.getCurrentRacers().size(), 1);
		assertEquals(indRace.getFinishedRacers().size(), 0);
		
		indRace.dnf();
		
		assertEquals(indRace.getReadyRacers().size(), 1);
		assertEquals(indRace.getCurrentRacers().size(), 0);
		assertEquals(indRace.getFinishedRacers().size(), 1);
		assertEquals(indRace.getDNFRacers().size(), 1);
		
		List<Racer> finished = indRace.getFinishedRacers();
		for(Racer r: finished) {
			assertTrue(r.getDnf());
		}
		
		indRace.start(1, new Time());
		
		assertEquals(indRace.getReadyRacers().size(),0);
		assertEquals(indRace.getCurrentRacers().size(), 1);
		assertEquals(indRace.getFinishedRacers().size(), 1);
		assertEquals(indRace.getDNFRacers().size(), 1);
		
		indRace.finish(2, new Time());
		
		assertEquals(indRace.getReadyRacers().size(), 0);
		assertEquals(indRace.getCurrentRacers().size(), 0);
		assertEquals(indRace.getFinishedRacers().size(), 2);
		assertEquals(indRace.getDNFRacers().size(), 1);
		
	}
	
	/** 
	 * Testing Use Case: Try adding dnf racer to ready queue
	 */
	@Test
	void test3() {
		racer1.setDnf();
		assertTrue(racer1.getDnf());
		
		assertFalse(indRace.addReady(racer1));
	}
	
	/**
	 * Testing Use Case: Cancel Racers
	 */
	@Test
	void test4() {
		indRace.addReady(racer1);
		indRace.addReady(racer2);
		
		assertTrue(indRace.cancel(racer1.getName()));
		assertFalse(indRace.cancel(racer3.getName()));
	}
	
	/**
	 * Tests the end method.
	 */
	void testEnd() {
		indRace.addReady(racer1);
		indRace.addReady(racer2);
		indRace.addReady(racer3);
		indRace.addReady(racer4);

		indRace.start(1, new Time());
		indRace.start(1, new Time());
		indRace.start(1, new Time());
		indRace.start(1, new Time());

		indRace.finish(1, new Time());
		assertEquals(indRace.getFinishedRacers().size(), 1);
		assertEquals(indRace.getDNFRacers().size(), 0);
		assertTrue(indRace.getFinishedRacers().get(0).equals(racer1));

		indRace.dnf();
		assertEquals(indRace.getFinishedRacers().size(), 2);
		assertEquals(indRace.getDNFRacers().size(), 1);
		assertTrue(indRace.getFinishedRacers().get(0).equals(racer1));
		assertTrue(indRace.getFinishedRacers().get(1).equals(racer2));
		assertTrue(indRace.getDNFRacers().get(0).equals(racer2));
		
		indRace.end();
		assertEquals(indRace.getFinishedRacers().size(), 4);
		assertEquals(indRace.getDNFRacers().size(), 3);
		assertTrue(indRace.getDNFRacers().get(0).equals(racer2));
		assertTrue(indRace.getDNFRacers().get(1).equals(racer3));
		assertTrue(indRace.getDNFRacers().get(2).equals(racer4));
	}
}
