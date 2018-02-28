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




public class Logger {	
	//BufferedWriter's take a writer of some sort as a parameter, so I chose the FileWriter. We can change that if people would like.
	//Still need to implement how exactly the gson will be written, but I'm not sure if this is done in this class or the race class -- Skyler
	
	
	private BufferedWriter raceBuffWriter;
	private BufferedWriter debugBuffWriter;
	private Writer raceFileWriter;
	private Writer debugFileWriter;
	private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	
	public Logger(String debugFileName, String raceJsonFileName)
	{
		try {	//Not sure if this is necessary or the best way to do this but It's probably necessary.
			debugFileWriter = new FileWriter(debugFileName);
			raceFileWriter = new FileWriter(raceJsonFileName);
			
			debugBuffWriter = new BufferedWriter(debugFileWriter);
			raceBuffWriter = new BufferedWriter(raceBuffWriter);
			
		} catch(IOException e)
		{
			e.printStackTrace();
		}
		
		
			
	}
	
	public static void main(String[] args) {
		List<Racer> list = new ArrayList<>();
		Racer r = new Racer(234);
		Racer r1 = new Racer(133);
		Racer r2 = new Racer(174);
		r.start(new Time(LocalTime.now()));
		
		list.add(r);
		list.add(r1);
		list.add(r2);
		
		try (Writer writer = new FileWriter("test.txt")) {
			Type type = new TypeToken<List<Racer>>() {}.getType();
			gson.toJson(list, writer);
			String json = gson.toJson(list, type);
			System.out.println(json);
			
		} catch (IOException e) {e.printStackTrace(); }
	}
	

}


