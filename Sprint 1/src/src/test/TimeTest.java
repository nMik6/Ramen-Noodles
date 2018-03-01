package src.test;
//Not sure how to test this, will ask group.
import static org.junit.jupiter.api.Assertions.*;

import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Test;

import src.main.Time;


class TimeTest {
	

	
	@Test
	void badTime() {
		try {
		Time badTime = new Time("99:99:99.9");
		} catch(DateTimeParseException ex) {
			//ex.printStackTrace();	
			System.out.println("Exception threw successfully");
		}
	
	}
	@Test	
	void goodTime() {
		try {
			Time noon = new Time("12:00:00.0");
			} catch(DateTimeParseException ex) {
				ex.printStackTrace();
				fail("This shouldn't have triggered");
			}
	}
	
	@Test void testRanges() {
		try {
			Time midnight = new Time("00:00:00.0");
			Time justBeforeMidnight = new Time("23:59:59.9");
		} catch(DateTimeParseException ex) {
	
			fail("Ranges failed");
			
		}
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
