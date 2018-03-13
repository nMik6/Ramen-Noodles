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
	
	

	public void print() {
		stdout
	}
	
	/**
	 * Prints the race data of each of the racers in the passed-in list to a file
	 * titled "RUN<raceNum>.txt" in the current users home directory 
	 * @param racers
	 * 
	 */
	public void export(List<Racer> racers, int raceNum) {
		String currentUsersHomeDir = System.getProperty("user.home");
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

}


