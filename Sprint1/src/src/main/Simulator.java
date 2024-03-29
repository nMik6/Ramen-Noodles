package src.main;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class Simulator {
	String command;
	Boolean power;
	Race cur_race;
	Channel[] channels;
	private List<Race> finished;
	Time timeOffset;
	boolean offsetPos;
	Logger log;
	private Scanner stdin = new Scanner(System.in);

	public Simulator() {
		power = false;
		cur_race = new Race();
		finished = new ArrayList<Race>();
		command = null;
		offsetPos = false;
		channels = new Channel[8];	//eight available channels
		for(int i = 0; i<8; i++) channels[i] = new Channel();
	}

	/**
	 * Starts the simulator prompting the user to input
	 * information or read from a file
	 */
	public void start() {
		do {
			System.out.print("Enter command: ('f' from file, 'c' from console)> ");
			command = stdin.nextLine();
		}while(!(command.equals("c")||command.equals("f")));

		if(command.equalsIgnoreCase("f"))
			readFromFile();
		else if(command.equalsIgnoreCase("c"))
			readFromConsole();

	}

	/**
	* Reads input from the user
	*/
	private void readFromConsole() {
		String[] cmds;

		do {
			System.out.print("Enter command: ");
			command = stdin.nextLine();
			cmds = command.split(" ");
			parse(cmds);
		}while(!(command.equals("exit")));
	}

	/**
	* Grabs commands from a file and executes them
	*/
	private void readFromFile() {
		String[] fileCommands = null;

		System.out.print("Enter the filename: ");
		String filename = stdin.nextLine();

		if(!(new File(filename).exists())) {
			System.out.println("Missing file"+filename);
			return;
		}

		try (Stream<String> stream = Files.lines(Paths.get(filename))){
			fileCommands = stream.toArray(size -> new String[size]);
		} catch (IOException e) { e.printStackTrace();}

		for(int i = 0; i < fileCommands.length; ++i) {
			String[] command = fileCommands[i].split("\\s+");
			parse(command);
		}
	}

	/**
	* Parses the commands that are entered and executes them
	* @param commandLine the commands entered
	*/
	public void parse(String[] commandLine) {
		int length = commandLine.length;
		Time passedTime = null;
		LocalTime toCheck;

		//Checks if first arg is a time. If it is, it is removed from the command line array and the commandLine array is shortened
		if (commandLine[0] != null) {
			final DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss.S");

			try { toCheck = LocalTime.parse(commandLine[0], format); }
			catch (Exception e) { toCheck = null; }

			if(toCheck != null && length > 1) {
				String[] cmdLineNew = new String[length-1];
				passedTime = new Time(toCheck);

				for(int i = 1; i< length; ++i) 
					cmdLineNew[i-1] = commandLine[i];

				commandLine = cmdLineNew;
				length--;
			}
		}

		if(length == 1) {
			switch(commandLine[0].toLowerCase()) {
			case "power":
				power();
				break;
			case "exit":
				exit();
				break;
			case "reset":
				reset();
				break;
			case "print":
				print();
				break;
			case "newrun":
				newrun();
				break;
			case "endrun":
				endrun();
				break;
			default:
				error();
			}
		}

		else if(length == 2) {
			switch(commandLine[0].toLowerCase()) {
			case "event":
				event(commandLine[1]);
				break;
			case "num":
				num(commandLine[1]);
				break;
			case "trig":
				if(passedTime != null) trig(commandLine[1], passedTime);
				else {
					if(timeOffset == null)trig(commandLine[1], new Time());
					else if(offsetPos)trig(commandLine[1], new Time().add(timeOffset));
					else trig(commandLine[1], new Time().difference(timeOffset));
				}
				break;
			case "time":
				time(commandLine[1]);
				break;
			case "tog":
				tog(commandLine[1]);
				break;
			default:	
				error();

			}
		}

		else if(length == 3) {
			if(commandLine[0].equalsIgnoreCase("conn"))
				conn(commandLine[1], commandLine[2]);
			else
				error();
		} 

		else { 	//Error
			error();
		}
	}

	/** Turn the power on and off (but stay in the simulator)*/
	public void power() {
		power = !power;
		if(!power) {
			cur_race = new Race();
			finished = new ArrayList<Race>();
			offsetPos = false;
			channels = new Channel[8];	//eight available channels
			for(int i = 0; i<8; i++) channels[i] = new Channel();
		}
		String log = "(log) Power is ";
		if(power) log += "on";
		else log += "off";
		System.out.println(log);
	}

	/** exit the simulator, no more commands processed */
	public void exit() {
		System.out.println("(log) system exit");
		System.exit(0);
	}

	/**
    * Resets the simulator
	*/
	public void reset() {
		if(!power) 
			return;

		System.out.println("(log) system reset");
		start();
	}

	/**
	* Prints the finished racers data, name, start time, finish time, and total time
	*/
	public void print() {
		if(!power)
			return;
		System.out.println("(log) print called");
		for(Racer r: cur_race.getFinishedRacers()) {
			System.out.printf("Racer: %d,\tStart: %s,\tFinish: %s,\tTotal: %s\n", 
					r.getName(), r.getStart().printTime(), r.getFinish().printTime(), r.getTotal().printTime());
		}
	}
	
	/** Set channel's sensor type. */
	public void conn(String sensor, String channel) {
		if(!power) 
			return;
		channels[Integer.parseInt(channel)].conn(sensor);
		System.out.println("(log) Sensor connected Type: " + sensor +" Num: " + channel);

	}

	/** Set channel's sensor type. */
	public void num(String bib) {
		if(!power) 
			return;
		try {
			cur_race.addReady(new Racer(Integer.parseInt(bib)));
			System.out.println("(log) Racer #" + bib + " added to race");
		} catch(Exception e){}

	}

	/** Set timeOffset*/
	public void time(String t) {
		Time sysTime = new Time();
		Time passedTime;
		LocalTime toCheck;

		final DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss.S");

		try { toCheck = LocalTime.parse(t, format); }
		catch (Exception e) { toCheck = null; } 
		//For now, if an invalid time is passed it defaults to the system time. will change later

		if(toCheck!= null) {
			passedTime = new Time(toCheck);
			System.out.println("(log) Time set to " + passedTime.printTime());
			//the passed time is after the current system time
			offsetPos = sysTime.isBefore(passedTime);
			timeOffset = passedTime.difference(sysTime);
		}
	}

	/*
	 * Verify that channel state is "true" then trigger. 
	 * @return nothing
	 */
	public void trig(String channel, Time t) {
		if(!power) return; 	
		try{
			int channelInt = Integer.parseInt(channel);
			Channel temp = channels[channelInt];

			if(temp.getState()) {
				System.out.println("(log) Trigger on channel #" + channel);
				if(cur_race == null) cur_race = new Race();
				if(channelInt % 2 != 0) cur_race.start(t);
				else cur_race.finish(t);
			}
		}catch(Exception e) {}
	}


	/** Toggle the state of the channel at string converted to integer index of channels[]*/
	public void tog(String channel) {
		if(!power) 
			return;
		try{
			int intchan = Integer.parseInt(channel);
			channels[intchan].toggle();
			System.out.println("(log) Channel #" + channel + " toggled");
		}catch(Exception e) {}
	}
	
	/**
	* Sets the type of event running
	* @param type of the event
	*/
	public void event(String type) {
		if(!power)
			return;
		if(cur_race == null) cur_race = new Race();
		cur_race.setType(type);
		System.out.println("(log) Race type set to: " + type);
	}

	/**
	* Starts a new race event
	*/
	public void newrun() {
		if(!power)
			return;
		if(cur_race != null) return;
		cur_race = new Race();
		System.out.println("(log) New race created");
	}

	/**
	* Ends the current race event and moves it to the list of finished races
	*/
	public void endrun() {
		if(!power)
			return;
		cur_race.end();
		finished.add(cur_race);
		cur_race = null;
		System.out.println("(log) Race ended");
	}

	/**
	* Reports that an invalid command was attempted in the console
	*/
	public void error() {
		if(!power)
			return;
		System.out.println("Invalid command");	//Logger should be able to handle string input

	}

	//for testing purposes only
	public String getCommand() { return command;}
	public boolean getPower() {return power;}
	public Race getRace() {return cur_race;}
	public Channel[] getChannels() {return channels;}
	public List<Race> getFinished() {return finished;}
	public Time getOffset() {return timeOffset;}
	//log not currently being used

}
