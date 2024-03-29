package main;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import main.racing.Racer;

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
	
	/**
	 * last message for testing in {@link DataEventTest}
	 */
	private ArrayList<String> log = new ArrayList<String>();

	/**
	 * Logger constructor, sets gson
	 */
	public Logger() {
		this.gson = new GsonBuilder().setPrettyPrinting().create();
	}
	
	/**
	 * Simple function to print a line of text to the console for logging
	 * @param msg The message to print
	 */
	public void msg(String msg) {
		System.out.println("Log: " + msg);
		log.add(msg);
	}
	
	/**
	 * Used to test the order of commands in {@link DataEventTest}
	 * @return
	 */
	public ArrayList<String> getMsgs() {
		return log;
	}
	
	/**
	 * Prints a list of racers to the console
	 * @param racers to print
	 */
	public void print(List<Racer> racers) {
		for(Racer r: racers) {
			System.out.printf("Name: %d\tStart: %s, Finish: %s, Total: %s\n",
					r.getName(), r.getStart().printTime(),
					r.getFinish().printTime(), r.getTotal().printTime());
		}
	}
	
	/**
	 * Prints the race data of each of the racers in the passed-in list to a file
	 * titled "RUN<raceNum>.txt" in the current users home directory 
	 * @param racers 
	 * @param num the race number
	 */
	public void export(List<Racer> racers, int raceNum) {
		String currentUsersHomeDir = "data";
		String raceFile = currentUsersHomeDir + File.separator + "RUN" + raceNum + ".json";
		System.out.println("Log: Exporting race to "+ raceFile);
		File file = new File(raceFile);
		
		try {
			file.createNewFile();
		} catch(IOException e) {e.printStackTrace();};
		
		try(Writer writer = new FileWriter(file)) {
			//Type type = new TypeToken<List<Racer>>() {}.getType();
			
			gson.toJson(racers, writer);
			
		} catch(IOException e) {e.printStackTrace();}
	}


}


