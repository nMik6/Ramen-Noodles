package src.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import com.google.gson.*;

import src.main.Racer;
import src.main.Time;

class LoggerTest {

		
		
	@Test
	void test() {

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		List<Racer> list = new ArrayList<>();
		Racer r = new Racer(234);
		Racer r1 = new Racer(133);
		Racer r2 = new Racer(174);
		r.start(new Time(LocalTime.now()));
		
		list.add(r);
		list.add(r1);
		list.add(r2);
	
		try (Writer writer = new FileWriter("test.txt")) {
			gson.toJson(list, writer);
			assertTrue(writer != null);
		} catch (IOException e) {e.printStackTrace(); }
		
		
	}

}

