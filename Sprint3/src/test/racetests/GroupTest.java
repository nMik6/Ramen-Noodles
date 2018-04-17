package test;

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
	
	@Before
	public void setup() {
		racer1 = new Racer(1);
		racer2 = new Racer(2);
		racer3 = new Racer(3);
		racer4 = new Racer(4);
		groupRace = new Group();
	}
	
	@Test 
	public void test1() {
		groupRace.addReady(racer1);
		groupRace.addReady(racer2);
		groupRace.addReady(racer3);
		groupRace.addReady(racer4);
		System.out.println("Ready Racers: " + groupRace.getReadyRacers());
		groupRace.start(new Time());
		System.out.println("Current Racers: " + groupRace.getCurrentRacers());
		
		try {
		    Thread.sleep(1000);
		} 
		catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		
		groupRace.finish(1, new Time());
		
		try {
		    Thread.sleep(1000);
		} 
		catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		
		groupRace.finish(3, new Time());
		System.out.println("Finished Racers: " + groupRace.getFinishedRacers());
		assertTrue(groupRace.containsBib(4));
	}
}


