package main;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.io.Writer;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import java.io.File;
import java.util.concurrent.TimeUnit;



/**
 * 
 * @author swkeough<br>
 * Takes a list of racers and writes their data members to a file for logging purposes. 
 */
public class Logger {	
	
	
	private Gson gson;
	private File racerf;

	
	public Logger() {
		this.gson = new GsonBuilder().setPrettyPrinting().create();
	}
	
	/*
	 * Print to console the racers finished from a list of racers
	 */
	public void print(List<Racer> racers) {
		for(Racer r: racers) {
			System.out.printf("Name: %d\tStart: %s, Finish: %s, Total: %s", r.getName(), r.getStart(), r.getFinish(), r.getTotal());
		}
	}
	
	/**
	 * Prints the race data of each of the racers in the passed-in list to a file
	 * titled "RUN<raceNum>.txt" in the current users home directory 
	 * @param racers
	 * 
	 */
	public void export(List<Racer> racers, int raceNum) {
		String currentUsersHomeDir = System.getProperty("user.dir");
		String raceFile = currentUsersHomeDir + File.separator + "RUN" + raceNum + ".txt";
		//System.out.println(raceFile);
		File file = new File(raceFile);
		
		try {
			file.createNewFile();
		} catch(IOException e) {e.printStackTrace();};
		
		try(Writer writer = new FileWriter(file)) {
			//Type type = new TypeToken<List<Racer>>() {}.getType();
			
			gson.toJson(racers, writer);
			
		} catch(IOException e) {e.printStackTrace();}
	}

	public static void main(String[] args) {
		ArrayList<Racer> list = new ArrayList<>();
		Racer r1 = new Racer(123);
		Racer r2 = new Racer(124);
		Logger log = new Logger();
		
		r1.start(new Time(LocalTime.now()));
		r2.start(new Time(LocalTime.now()));
		
		r1.finish(new Time(LocalTime.now()));
		r2.finish(new Time(LocalTime.now()));
		
		log.print(list);
		log.export(list, 111);
	}
}


