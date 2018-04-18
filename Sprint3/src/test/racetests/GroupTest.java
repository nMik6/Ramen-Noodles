package test.racetests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import main.Time;
import main.racing.Group;
import main.racing.Racer;


public class GroupTest {

	Group groupRace;
	Racer racer1, racer2, racer3, racer4;
	Time groupStart;
	int finishChannel = 2;
	
	@Before
	public void setup() {
		racer1 = new Racer(-1);
		racer2 = new Racer(-1);
		racer3 = new Racer(-1);
		racer4 = new Racer(-1);
		groupRace = new Group();
	}
	
	@Test 
	/**
	* Test Usecase: No Race numbers are entered 
	*/
	public void test1() {
		groupRace.start(new Time());
		
		try {
		    Thread.sleep(1000);
		} 
		catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		
		groupRace.finish(finishChannel, new Time());
		
		try {
		    Thread.sleep(1000);
		} 
		catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		
		groupRace.finish(finishChannel, new Time());
		
		assertEquals(groupRace.getFinishedRacers().size(), 2);
		
		groupRace.finish(finishChannel, new Time());
		groupRace.finish(finishChannel, new Time());
		groupRace.finish(finishChannel, new Time());
		
		assertEquals(groupRace.getFinishedRacers().size(), 5);
		
		groupRace.setBib(123);
		groupRace.setBib(345);
		groupRace.setBib(567);
		
		//The below methods should be a valid statements because the overridden equals method for Racer class only compares the bib number for equivalence
		assertTrue(groupRace.getFinishedRacers().contains(new Racer(123)));	
		assertTrue(groupRace.getFinishedRacers().contains(new Racer(345)));
		assertTrue(groupRace.getFinishedRacers().contains(new Racer(567)));
		
		groupRace.setBib(246);
		groupRace.setBib(468);
		
		assertFalse(groupRace.getFinishedRacers().contains(new Racer(-1)));
	}

	@Test
	/**
	* Test Usecase: End race, try to call finish
	*/
	public void test2() {
		
		
		
	}

}


