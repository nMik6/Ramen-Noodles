package test.racetests;

import static org.junit.jupiter.api.Assertions.*;

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
		racer1 = new Racer(-1);

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
	 * Testing Use Case: start and end race without racer finishing
	 */
	@Test
	void test1() {
		
	}
	
	/**
	 * Testing Use Case: Start race and dnf racer
	 */
	@Test
	void test2() {
		
	}
	
	/** 
	 * Testing Use Case: Add multiple racers to ready queue
	 */
	@Test
	void test3() {
		
	}
}
