package src.main;
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
//	private String racerFile;
//	private String debugFile;
	private File racerf;
//	private File debugf;
	
	public Logger() {
		this.gson = new GsonBuilder().setPrettyPrinting().create();
	}
	
//	public Logger(String debugFile, String racerFile) {
//		
//		this.racerf = new File(racerFile);
//		try {
//			this.racerf.createNewFile();
//		} catch(IOException e) {e.printStackTrace();};
//		
//		this.debugf = new File(debugFile);
//		
//		this.racerFile = racerFile;
//		this.debugFile = debugFile;
//		this.gson = new GsonBuilder().setPrettyPrinting().create();
//	}
	
	
	/**
	 * Prints the race data of each of the racers in the passed-in list.
	 * @param racers
	 * 
	 */
	public void print(List<Racer> racers, int raceNum) {
		String currentUsersHomeDir = System.getProperty("user.home");
		System.out.println("Hello world");
		String raceFile = currentUsersHomeDir + File.separator + "RUN" + raceNum + ".txt";
		System.out.println(raceFile);
		File file = new File(raceFile);
		
		try {
			file.createNewFile();
		} catch(IOException e) {e.printStackTrace();};
		
		try(Writer writer = new FileWriter(file)) {
	//		Type type = new TypeToken<List<Racer>>() {}.getType();
			
			gson.toJson(racers, writer);
			
		} catch(IOException e) {e.printStackTrace();}
	}
	/*
	 * for testing purposes 
	 */
	public static void main(String[] args){
		ArrayList<Racer> list = new ArrayList<>();
		
		Racer r = new Racer(234);
		Racer r1 = new Racer(133);
		Racer r2 = new Racer(174);
		
		r.start(new Time(LocalTime.now()));
		r1.start(new Time(LocalTime.now()));
		r2.start(new Time(LocalTime.now()));
		
		try{
			TimeUnit.SECONDS.sleep(1);
		}catch(Exception e) { e.printStackTrace(); };
		
		r.finish(new Time(LocalTime.now()));
		r1.start(new Time(LocalTime.now()));
		r2.start(new Time(LocalTime.now()));
		r.getTotal();
		
		list.add(r);
		list.add(r1);
		list.add(r2);
		
		System.out.println(list);
		
		Logger log = new Logger();
		log.print(list, 45);
		System.out.println("Logger Done");
//		
//		try (Writer writerjs = new FileWriter("test.json")) {
//			log = new Logger("debug.txt", "racer.txt");
//			gson.toJson(list, writerjs);
//			String json = gson.toJson(list, type);
//			System.out.println(json);
//			log.print(list);
//		} catch (IOException e) {e.printStackTrace(); }
	} 
	
	

}


