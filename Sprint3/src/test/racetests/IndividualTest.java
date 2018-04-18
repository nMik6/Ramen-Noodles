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
			assertTrue(r.didNotFinish());
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
		
		List<Racer> finished = indRace.getFinishedRacers();
		for(Racer r: finished) {
			assertTrue(r.didNotFinish());
		}
		
		indRace.start(1, new Time());
		
		assertEquals(indRace.getReadyRacers().size(),0);
		assertEquals(indRace.getCurrentRacers().size(), 1);
		assertEquals(indRace.getFinishedRacers().size(), 1);
		
		indRace.finish(2, new Time());
		
		assertEquals(indRace.getReadyRacers().size(), 0);
		assertEquals(indRace.getCurrentRacers().size(), 0);
		assertEquals(indRace.getFinishedRacers().size(), 2);
		
	}
	
	/** 
	 * Testing Use Case: Try adding dnf racer to ready queue
	 */
	@Test
	void test3() {
		racer1.dnf();
		assertTrue(racer1.didNotFinish());
		
		assertFalse(indRace.addReady(racer1));
	}
	
	/**
	 * Testing Use Case: Cancel Racers
	 */
	@Test
	void test4() {
		indRace.addReady(racer1);
		indRace.addReady(racer2);
		
		assertTrue(indRace.cancel(racer1));
		assertFalse(indRace.cancel(racer3));
	}
}