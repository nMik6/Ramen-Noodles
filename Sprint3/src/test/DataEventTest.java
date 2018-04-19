package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.RaceData;
import main.Time;
import main.events.Event;
import main.events.EventHandler;

class DataEventTest {

	Time time;
	Event event;
	EventHandler evHan;
	RaceData raceData;

	@BeforeEach
	public void setup() {
		time = new Time("12:00:00.0");
		raceData = new RaceData();
		event = new Event(raceData);
		evHan = new EventHandler(raceData, time);
	}
	
	@Test
	public void testCurrentRaceEvent() {
		assertTrue(!raceData.isPower());
		event.power();
		assertTrue(raceData.getCurrentRace() == null);
		event.event("PARA");
		event.newrun();
		event.tog("1");
		event.tog("2");
		event.tog("3");
		event.tog("4");
		event.num("1");
		event.num("1");
		assertEquals("That bib number already exists", raceData.getLog().getLastMsg());
		event.num("2");
		event.num("3");
		event.num("4");
		event.trig("1", time);
		event.trig("3", time);
		event.trig("2", time);
		event.trig("2", new Time());
		event.trig("4", time);
		event.trig("1", new Time());
		event.trig("3", new Time());
		event.trig("4", new Time());
		event.print();
		event.endrun();
		assertTrue(raceData.getCurrentRace() == null);
		assertTrue(raceData.getFinishedRaces().size() == 1);	
		assertEquals(0, raceData.getRaceNum());
	}
	
	@Test
	public void testPowerEvent() {
		assertTrue(!raceData.isPower());
		event.power();
		assertTrue(raceData.isPower());
		event.reset();
		assertTrue(raceData.isPower());
	}
	
	@Test
	public void testFinishedRacesEvent() {
		assertEquals(0, raceData.getFinishedRaces().size());
		event.power();
		event.event("IND");
		event.newrun();
		event.tog("1");
		event.tog("2");
		event.num("1");
		event.num("2");
		event.trig("1", time);
		event.trig("3", time);
		event.trig("2", new Time());
		event.trig("4", new Time());
		event.endrun();
		assertEquals(1, raceData.getFinishedRaces().size());
		assertTrue(raceData.getFinishedRaces().get(0).containsBib(2));
		event.event("IND");
		event.newrun();
		event.endrun();
		assertEquals(2, raceData.getFinishedRaces().size());
		event.event("IND");
		event.newrun();
		event.endrun();
		assertEquals(2, raceData.getRaceNum());
		event.event("IND");
		event.newrun();
		event.endrun();
		event.event("IND");
		event.newrun();
		event.endrun();
		assertEquals(5, raceData.getFinishedRaces().size());
		raceData.getFinishedRaces().remove(0);
		assertEquals(4, raceData.getFinishedRaces().size());
	}
	
	@Test
	public void testCurrentRaceEventHandler() {
		assertTrue(!raceData.isPower());
		evHan.handle(new String[]{"power"});
		assertTrue(raceData.getCurrentRace() == null);
		evHan.handle(new String[]{"event", "PARA"});
		evHan.handle(new String[]{"newrun"});
		evHan.handle(new String[]{"tog","1"});
		evHan.handle(new String[]{"tog","2"});
		evHan.handle(new String[]{"tog","3"});
		evHan.handle(new String[]{"tog","4"});
		evHan.handle(new String[]{"num","1"});
		evHan.handle(new String[]{"num","1"});
		assertEquals("That bib number already exists", raceData.getLog().getLastMsg());
		evHan.handle(new String[]{"num","2"});
		evHan.handle(new String[]{"num","3"});
		evHan.handle(new String[]{"num","4"});
		evHan.handle(new String[]{"trig","1"});
		evHan.handle(new String[]{"trig","3"});
		evHan.handle(new String[]{"trig","2"});
		evHan.handle(new String[]{"trig","1"});
		evHan.handle(new String[]{"trig","2"});
		evHan.handle(new String[]{"trig","4"});
		evHan.handle(new String[]{"trig","3"});
		evHan.handle(new String[]{"trig","4"});
		evHan.handle(new String[]{"print"});
		evHan.handle(new String[]{"endrun"});
		assertTrue(raceData.getCurrentRace() == null);
		assertTrue(raceData.getFinishedRaces().size() == 1);	
		assertEquals(0, raceData.getRaceNum());
	}
	
	@Test
	public void testPowerEventHandler() {
		assertTrue(!raceData.isPower());
		evHan.handle(new String[]{"power"});
		assertTrue(raceData.isPower());
		evHan.handle(new String[]{"reset"});
		assertTrue(raceData.isPower());
	}
	
	@Test
	public void testFinishedRacesEventHandler() {
		assertEquals(0, raceData.getFinishedRaces().size());
		evHan.handle(new String[]{"power"});
		evHan.handle(new String[]{"event", "IND"});
		evHan.handle(new String[]{"newrun"});
		evHan.handle(new String[]{"tog","1"});
		evHan.handle(new String[]{"tog","2"});
		evHan.handle(new String[]{"num","1"});
		evHan.handle(new String[]{"num","2"});
		evHan.handle(new String[]{"trig","1"});
		evHan.handle(new String[]{"trig","2"});
		evHan.handle(new String[]{"trig","3"});
		evHan.handle(new String[]{"trig","4"});
		evHan.handle(new String[]{"endrun"});
		assertEquals(1, raceData.getFinishedRaces().size());
		assertTrue(raceData.getFinishedRaces().get(0).containsBib(2));
		evHan.handle(new String[]{"event", "IND"});
		evHan.handle(new String[]{"newrun"});
		evHan.handle(new String[]{"endrun"});
		assertEquals(2, raceData.getFinishedRaces().size());
		evHan.handle(new String[]{"event", "IND"});
		evHan.handle(new String[]{"newrun"});
		evHan.handle(new String[]{"endrun"});
		assertEquals(2, raceData.getRaceNum());
		evHan.handle(new String[]{"event", "IND"});
		evHan.handle(new String[]{"newrun"});
		evHan.handle(new String[]{"endrun"});
		evHan.handle(new String[]{"event", "IND"});
		evHan.handle(new String[]{"newrun"});
		evHan.handle(new String[]{"endrun"});
		assertEquals(5, raceData.getFinishedRaces().size());
		raceData.getFinishedRaces().remove(0);
		assertEquals(4, raceData.getFinishedRaces().size());
	}
}
