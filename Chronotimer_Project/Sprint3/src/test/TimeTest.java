package test;
//Not sure how to test this, will ask group.
import static org.junit.jupiter.api.Assertions.*;

import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Test;

import main.Time;


class TimeTest {



	@Test
	void badTime() {
		Time badTime;
		try {
			badTime = new Time("99:99:99.9");
		} catch(DateTimeParseException ex) {
			//ex.printStackTrace();	
			badTime = null;
			System.out.println("Exception threw successfully");
		}
		assertTrue(badTime == null);
	}
	@Test	
	void goodTime() {
		Time noon=null;
		try {
			noon = new Time("12:00:00.0");
		} catch(DateTimeParseException ex) {
			ex.printStackTrace();
			fail("This shouldn't have triggered");
		}
		assertFalse(noon == null);
		System.out.println(noon.printTime());
		assertEquals("12:00:00.0", noon.printTime());
	}

	@Test void testRanges() {
		Time midnight=null;
		Time preMidnight=null;
		Time justOver = null;
		try {midnight = new Time("00:00:00.0");} catch(DateTimeParseException ex) {	midnight = null;}
		try {preMidnight = new Time("23:59:59.9");} catch(DateTimeParseException ex) {	preMidnight = null;}
		try {justOver = new Time("24:60:60.9");} catch(DateTimeParseException ex) {	justOver = null;}
		assertFalse(midnight == null);
		assertFalse(preMidnight == null);
		assertTrue(justOver == null);
		assertEquals("00:00:00.0", midnight.printTime());
		assertEquals("23:59:59.9", preMidnight.printTime());
	}

	@Test
	void testBefore() {
		Time first = new Time("10:00:00.0");
		Time after = new Time("10:00:00.1");

		assertTrue(first.isBefore(after));
		assertFalse(after.isBefore(first));
	}

	@Test
	void testDifference1() {
		Time noon = new Time("12:00:00.0");
		Time twoPM = new Time("14:00:00.0");
		Time twoAM = twoPM.difference(noon);
		Time twoAMTest = new Time("02:00:00.0");
		String twoAMString = twoAM.printTime();
		String twoAMTestString = twoAMTest.printTime();
		assertTrue(twoAMString.equals(twoAMTestString));
	}







}
