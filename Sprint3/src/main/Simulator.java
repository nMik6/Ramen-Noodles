package main;
import main.racing.*;
import main.events.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import main.racing.Race;
import main.racing.Racer;

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
	private ArrayList<Race> finished;
	Time timeOffset;
	boolean offsetPos;
	Logger log;
	private Scanner stdin = new Scanner(System.in);
	String debugfile = "debugfile.txt";
	String racerfile = "racerfile.txt";
	int raceNum;

	/**
	 * Constructor for Simulator
	 */
	public Simulator() {
		power = false;
		cur_race = null;
		finished = new ArrayList<Race>();
		command = null;
		offsetPos = false;
		channels = new Channel[8];
		for (int i = 0; i<8; i++)
			channels[i] = new Channel();
		log = new Logger();
		raceNum = 0;
	}

	/**
	 * Starts the simulator prompting the user to input
	 * information or read from a file
	 */
	public void start() {
		do {
			System.out.print("Enter command: ('f' from file, 'c' from console)> ");
			command = stdin.nextLine();
		} while (!(command.equals("c")||command.equals("f")));

		if (command.equalsIgnoreCase("f"))
			readFromFile();
		else if (command.equalsIgnoreCase("c"))
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
		} while (!(command.equals("exit")));
	}

	/**
	 * Grabs commands from a file and executes them
	 */
	private void readFromFile() {
		String[] fileCommands = null;

		System.out.print("Enter the filename: ");
		String filename = stdin.nextLine();

		if (!(new File(filename).exists())) {
			System.out.println("Missing file"+filename);
			return;
		}

		try (Stream<String> stream = Files.lines(Paths.get(filename))) {
			fileCommands = stream.toArray(size -> new String[size]);
		} catch (IOException e) { e.printStackTrace();}

		for (int i = 0; i < fileCommands.length; ++i) {
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

			if (toCheck != null && length > 1) {
				String[] cmdLineNew = new String[length-1];
				passedTime = new Time(toCheck);

				for (int i = 1; i< length; ++i) 
					cmdLineNew[i-1] = commandLine[i];

				commandLine = cmdLineNew;
				length--;
			}
		}

		if (length == 1) {
			switch (commandLine[0].toLowerCase()) {
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
			case "start":
				if (passedTime != null) trig("1", passedTime);
				else {
					if (timeOffset == null)trig("1", new Time());
					else if (offsetPos)trig("1", new Time().add(timeOffset));
					else trig("1", new Time().difference(timeOffset));
				}
				break;
			case "end":
				if (passedTime != null) trig("2", passedTime);
				else {
					if (timeOffset == null)trig("2", new Time());
					else if (offsetPos)trig("2", new Time().add(timeOffset));
					else trig("2", new Time().difference(timeOffset));
				}
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

		else if (length == 2) {
			switch (commandLine[0].toLowerCase()) {

			case "event":
				event(commandLine[1]);
				break;
			case "num":
				num(commandLine[1]);
				break;
			case "trig":
				if (passedTime != null) trig(commandLine[1], passedTime);
				else {
					if (timeOffset == null)trig(commandLine[1], new Time());
					else if (offsetPos)trig(commandLine[1], new Time().add(timeOffset));
					else trig(commandLine[1], new Time().difference(timeOffset));
				}
				break;
			case "time":
				time(commandLine[1]);
				break;
			case "tog":
				tog(commandLine[1]);
				break;
				//If endrun is called prior to export, an automatic export will have occurred and the race will have been cleared
			case "export":
				try {
					export(Integer.parseInt(commandLine[1]));
				}catch(Exception e) {error();}
				break;
			default:	
				error();

			}
		}

		else if (length == 3) {
			if (commandLine[0].equalsIgnoreCase("conn"))
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
		if (!power) {
			cur_race = null;
			finished = new ArrayList<Race>();
			offsetPos = false;
			channels = new Channel[8];	//eight available channels
			for (int i = 0; i<8; i++) channels[i] = new Channel();
		}
		String log = "(log) Power is ";
		if(power) log += "on";
		else log += "off";
		System.out.println(log);
	}

	/** 
	 * exit the simulator, no more commands processed
	 */
	public void exit() {
		System.out.println("(log) system exit");
		System.exit(0);
	}

	/**
	 * Resets the simulator
	 */
	public void reset() {
		if (!power) return;

		System.out.println("(log) system reset");
		power = true;
		System.out.println("Reseting System!");
		cur_race = null;
		System.out.println("After new race created!");
		command = null;
		offsetPos = false;
		for (int i = 0; i<8; i++) channels[i] = new Channel();
	}

	/**
	 * Prints the finished racers data, name, start time, finish time, and total time
	 */
	public void print() {
		if (!power || cur_race == null)	return;
		System.out.println("(log) print called");
		for (Racer r: cur_race.getFinishedRacers()) {
			if (!r.didNotFinish()) {
			System.out.printf("Racer: %d,\tStart: %s,\tFinish: %s,\tTotal: %s\n", 
					r.getName(), r.getStart().printTime(), r.getFinish().printTime(), r.getTotal().printTime());
			}
			else {
				System.out.printf("Racer: %d,\tStart: %s, \tDid not finish!", 
						r.getName(), r.getStart().printTime());
			}
		}
	}

	/**
	 * Sets the sensor type of the channel
	 * @param sensor
	 * @param channel
	 */
	public void conn(String sensor, String channel) {
		if (!power) return;
		channels[Integer.parseInt(channel)].conn(sensor);
		System.out.println("(log) Sensor connected Type: " + sensor +" Num: " + channel);

	}

	/**
	 * Assigns a new racer to the race
	 * @param bib the number assigned to the racer
	 */
	public void num(String bib) {
		if (!power) return;
		try {
			cur_race.addReady(new Racer(Integer.parseInt(bib)));
			System.out.println("(log) Racer #" + bib + " added to race");
		} catch(Exception e){}

	}

	/**
	 * Creates a new time 
	 * @param t the time
	 */
	public void time(String t) {
		Time sysTime = new Time();
		Time passedTime;
		LocalTime toCheck;

		final DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss.S");

		try { toCheck = LocalTime.parse(t, format); }
		catch (Exception e) { toCheck = null; } 
		//For now, if an invalid time is passed it defaults to the system time. will change later

		if (toCheck!= null) {
			passedTime = new Time(toCheck);
			System.out.println("(log) Time set to " + passedTime.printTime());
			//the passed time is after the current system time
			offsetPos = sysTime.isBefore(passedTime);
			timeOffset = passedTime.difference(sysTime);
		}
	}

	/**
	 * Verifies a channels state and triggers is
	 * @param channel the channel to check
	 * @param t the time
	 */
	public void trig(String channel, Time t) {
		if (!power) return; 	
		try {
			int channelInt = Integer.parseInt(channel);
			Channel temp = channels[channelInt];

			if (temp.getState()) {
				System.out.println("(log) Trigger on channel #" + channel);
				if (channelInt % 2 != 0) cur_race.start(channelInt, t);
				else cur_race.finish(channelInt, t);
			}
		}catch(Exception e) {
			System.out.println("No Current race! Cannot trigger a start!");
		}
	}


	/**
	 * Toggle the state of the channel at string converted to integer index of channels[]
	 * @param channel
	 * @return the channel triggered, -1 if power is off
	 */
	public int tog(String channel) {
		if (power) 	
			try {
				int intchan = Integer.parseInt(channel);
				channels[intchan].toggle();
				System.out.println("(log) Channel #" + channel + " toggled");
				return intchan;
			} catch(Exception e) {}
		return -1;
	}

	/**
	 * Sets the type of event running
	 * @param type of the event
	 */
	public void event(String type) {
		if (!power) return;
		if (type.equals("IND"))
		{
			cur_race = new Individual();
		}
		else if (type.equals("PARA")) 
		{
			cur_race = new ParallelIndividual();
		}
		else if (type.equals("GRP"))
		{
			cur_race = new Group();
		}
		else
		{
			System.out.println("Event type not found!");
		}
		System.out.println("(log) Race type set to: " + type);
	}

	/**
	 * Starts a new race event
	 */
	public void newrun() {
		if (!power)	return;
		if (cur_race != null) return;
		raceNum++;
		System.out.println("(log) New race created");
	}

	/**
	 * Ends the current race event and moves it to the list of finished races
	 */
	public void endrun() {
		if (!power) return;
		cur_race.end();
		log.export(cur_race.getFinishedRacers(), raceNum);
		finished.add(cur_race);
		cur_race = null;
		System.out.println("(log) Race ended");
	}

	/**
	 * Ends the current race event and moves it to the list of finished races
	 */
	public void export(int num) {
		if (!power) return;

		if (cur_race != null) {
			log.export(cur_race.getFinishedRacers(), num);
			System.out.println("(log) Export Occurred");
		}

		else {
			Race ret = finished.get(raceNum);
			if (ret != null) {
				log.export(ret.getFinishedRacers(), num);
				System.out.println("(log) Export Occurred");
			}
		}
	}

	/**
	 * Reports that an invalid command was attempted in the console
	 */
	public void error() {
		if (power) System.out.println("Invalid command");
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
