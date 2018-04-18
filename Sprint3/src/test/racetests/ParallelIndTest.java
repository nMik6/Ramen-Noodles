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
		assertEquals(paraIndRace.getCurrentRacers().size(), 0);
		assertEquals(paraIndRace.getFinishedRacers().size(), 0);
		
		assertTrue(paraIndRace.addReady(racer1));
		assertTrue(paraIndRace.addReady(racer2));
		assertTrue(paraIndRace.addReady(racer3));
		assertTrue(paraIndRace.addReady(racer4));
		
		assertEquals(paraIndRace.getReadyRacers().size(), 4);
		assertEquals(paraIndRace.getCurrentRacers().size(), 0);
		assertEquals(paraIndRace.getFinishedRacers().size(), 0);
		
		
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