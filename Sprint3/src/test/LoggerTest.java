package test;

import static org.junit.jupiter.api.Assertions.*;


import java.time.LocalTime;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.Time;
import main.racing.Racer;
import main.Logger;
import java.io.File;
import java.time.LocalTime;

class LoggerTest {
	Logger log;
	Racer r1, r2, r3;
	ArrayList<Racer> list;
	int raceNum;
	
	@BeforeEach
	public void setup() {
		log = new Logger();
		list = new ArrayList<>();
		raceNum = 123;
		
		r1 = new Racer(123);
		r2 = new Racer(133);
		r3 = new Racer(143);
		
		r1.start(new Time(LocalTime.now()));
		r2.start(new Time(LocalTime.now()));
		r3.start(new Time(LocalTime.now()));
		

	}
	
	@Test
	public void testAllFinish() {

		r1.finish(new Time(LocalTime.now()));
		r2.finish(new Time(LocalTime.now()));
		r3.finish(new Time(LocalTime.now()));
		
		list.add(r1);
		list.add(r2);
		list.add(r3);
		
		log.export(list, raceNum);
		
		System.out.println("raceNum = " + raceNum);
		assertTrue(fileFound(raceNum) != null);
	}
	
//	@Test
//	public void testOverWriteExistingLog() {
//		
//		r1.finish(new Time(LocalTime.now()));
//		r2.finish(new Time(LocalTime.now()));
//		r3.finish(new Time(LocalTime.now()));
//		
//		list.add(r1);
//		list.add(r2);
//		list.add(r3);
//		
//		System.out.println("raceNum = " + raceNum);
//		File f1 = fileFound(raceNum);
//		assertTrue(f1 != null);
//		
//		System.out.println("raceNum = " + raceNum);
//		log.export(list, raceNum);
//		File f2 = fileFound(raceNum);
//		assertTrue(!f2.equals(f1));
//	}
	
	/*
	 * helper method iterates through current working directory to find file named: "RUN<raceNum>.txt"
	 * returns File object if found, null if not.  
	 */
	private File fileFound(int raceNum) {
		String curUserDir = System.getProperty("user.dir");
		String filename = curUserDir + File.separator + "RUN" + raceNum + ".txt";
		
		
		File[] files = new File(curUserDir).listFiles();
		
		for(File file: files) {
			if(file.isFile()) {	
				if(file.getAbsolutePath().equals(filename)) {
					return file;
				}
				System.out.println(file.getAbsolutePath() + "  " + filename);
			}
		}
		
		return null;
	}
}

