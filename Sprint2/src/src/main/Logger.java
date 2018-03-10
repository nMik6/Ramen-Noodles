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



/**
 * 
 * @author swkeough<br>
 * Takes a list of racers and writes their data members to a file for logging purposes. 
 */
public class Logger {	
	
	
	private Gson gson;
	private String racerFile;
	private String debugFile;
	
	
	public Logger(String debugFile, String racerFile) {
		this.racerFile = racerFile;
		this.debugFile = debugFile;
		this.gson = new GsonBuilder().setPrettyPrinting().create();
	}
	

	/**
	 * Prints the race data of each of the racers in the passed-in list.
	 * @param racers
	 * 
	 */
	public void print(List<Racer> racers) {

		try(Writer writer = new FileWriter(racerFile)) {
			Type type = new TypeToken<List<Racer>>() {}.getType();
			
			gson.toJson(racers, writer);
			
		} catch(IOException e) {e.printStackTrace();}
	}
	
	public static void main(String[] args) {
		ArrayList<Racer> list = new ArrayList<>();
		Racer r = new Racer(234);
		Racer r1 = new Racer(133);
		Racer r2 = new Racer(174);
		r.start(new Time(LocalTime.now()));
		r.finish(new Time(LocalTime.now()));
		r.getTotal();
		
		list.add(r);
		list.add(r1);
		list.add(r2);
		

		try (Writer writer = new FileWriter("test.txt")) {
			Type type = new TypeToken<List<Racer>>() {}.getType();

		try (Writer writerjs = new FileWriter("test.json")) {

			gson.toJson(list, writerjs);
			String json = gson.toJson(list, type);
			System.out.println(json);
			
		} catch (IOException e) {e.printStackTrace(); }
	} 
	
	

}


