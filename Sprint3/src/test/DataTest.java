package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.RaceData;
import main.Time;
import main.events.Event;
import main.racing.Individual;
import main.racing.Race;
import main.racing.Racer;

class DataTest {


	Race indRace;
	Racer racer1, racer2, racer3, racer4, racer5;
	Time time;
	RaceData raceData;

	@BeforeEach
	public void setup() {
		indRace = new Individual();
		time = new Time();
		racer1 = new Racer(1);
		racer2 = new Racer(2);
		racer3 = new Racer(4);
		racer4 = new Racer(4);
		racer5 = new Racer(5);
		indRace.addReady(racer1);
		indRace.addReady(racer2);
		indRace.addReady(racer3);
		indRace.addReady(racer4);
		indRace.addReady(racer5);
		raceData = new RaceData();
	}
	
	@Test
	public void testCurrentRace() {
		assertTrue(raceData.getCurrentRace() == null);
		raceData.setCurrentRace(indRace);
		assertEquals(raceData.getCurrentRace(), indRace);
	}
	
	@Test
	public void testPower() {
		assertTrue(!raceData.isPower());
		new Event(raceData).power();
		assertTrue(raceData.isPower());
		new Event(raceData).reset();
		assertTrue(!raceData.isPower());
	}
	
	@Test
	public void testFinishedRace() {
		assertTrue(raceData.getCurrentRace() == null);
		raceData.setCurrentRace(indRace);
		assertEquals(raceData.getCurrentRace(), indRace);
		indRace.end();
		assertTrue(indRace.getCurrentRacers().size() == 0);
		raceData.addFinishedRace(indRace);
		assertTrue(raceData.getFinishedRaces().size() == 1);
	}

}
