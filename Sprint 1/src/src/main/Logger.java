package src.main;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import org.json.JSONObject;		//Not sure how this works yet, working on it.
import com.google.gson.*;
import java.io.Writer;




public class Logger {	
	//BufferedWriter's take a writer of some sort as a parameter, so I chose the FileWriter. We can change that if people would like.
	//Still need to implement how exactly the gson will be written, but I'm not sure if this is done in this class or the race class -- Skyler
	
	
	private BufferedWriter raceBuffWriter;
	private BufferedWriter debugBuffWriter;
	private Writer raceFileWriter;
	private Writer debugFileWriter;
	
	private Gson gsonRace;
	
	
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
		
		gsonRace = new GsonBuilder().create();
			
	}
	

	}


