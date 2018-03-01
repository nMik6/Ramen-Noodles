package src.main;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
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
	List<Race> finishedRaces;
	//int bib_count;
	//List<Integer> used_bibs;
	Time timeOffset;
	boolean offsetPos;
	Logger log;
	private Scanner stdin = new Scanner(System.in);
	
	public void start() {
			power = false;
			timeOffset = new Time();
			cur_race = new Race();
			finishedRaces = new ArrayList<>();
			command = null;
			//bib_count = 100;
			//used_bibs = new ArrayList<>();
			channels = new Channel[8];	//eight available channels
			
			do {
				System.out.print("Enter command: ('f' from file, 'c' from console)> ");
				command = stdin.nextLine();
			}while(!(command.equals("c")||command.equals("f")));
			
			if(command.equalsIgnoreCase("f"))
				readFromFile();
			else if(command.equalsIgnoreCase("c"))
				readFromConsole();

	}
	
	private void readFromConsole() {
		String[] cmds;
		
		do {
			System.out.print("Enter command: ");
			command = stdin.nextLine();
			cmds = command.split(" ");
			parse(cmds);
		}while(!(command.equals("exit")));
	}
	
	private void readFromFile() {
		String[] fileCommands = null;
		
		System.out.print("Enter the filename: ");
		String filename = stdin.nextLine();
		
		if(!(new File(filename).exists())) {
			System.out.println("Missing file"+filename);
			return;
		}
		
		try (Stream<String> stream = Files.lines(Paths.get(command))){
			fileCommands = stream.toArray(size -> new String[size]);
		} catch (IOException e) { e.printStackTrace();}
		
		for(int i = 0; i < fileCommands.length; ++i) {
			String[] command = fileCommands[i].split(" ");
			parse(command);
		}
	}
	
	private int parse(String[] commandLine) {
		int length = commandLine.length;
		Time passedTime;
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
			switch(commandLine[0]) {
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
			switch(commandLine[0]) {
				case "event":
					event(commandLine[1]);
					break;
				case "num":
					num(commandLine[1]);
					break;
				case "trig":
					if(passedTime != null) trig(commandLine[1], passedTime);
					else trig(commandLine[1], );
					break;
				case "time":
					time(commandLine[1]);
					break;
				default:	
					error();
			}
		}
		
		else if(length == 3) {
			if(commandLine[0].equals("conn"))
				conn(commandLine[1], commandLine[2]);
			else
				error();
		} 
		
		else { 	//Error
			error();
		}

		return 0;
	}
	
	/** Turn the power on and off (but stay in the simulator)*/
	private void power() {
		power = !power;
		cur_race = null;
		timeOffset = null;
		finishedRaces = null;
	}
	
	/** exit the simulator, no more commands processed */
	private void exit() {
		System.exit(0);
	}
	
	private void reset() {
		if(!power) 
			return;
		start();
	}
	
	private void print() {
		if(!power)
			return;
		for(Racer r: cur_race.getFinishedRacers()) {
			System.out.printf("Racer: %d,\tStart: %s,\tFinish: %s,\tTotal: %s\n", 
					r.getName(), r.getStart(), r.getFinish(), r.getTotal());
		}
	}
	/** Set channel's sensor type. */
	private void conn(String sensor, String channel) {
		if(!power) 
			return;
		channels[Integer.parseInt(channel)].conn(sensor);
	}
	
	/** Set timeOffset*/
	private void time(String t) {
//		LocalTime passedTime;
//		final DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss.S");
//		
//		try { passedTime = LocalTime.parse(t, format); }
//		catch (Exception e) { passedTime = null; }
//						
//		if(passedTime != null) {
//			sysTime = new Time(passedTime);
//			setAt = new Time();
//		}
			
	}
	
	/*
	 * Verify that channel state is "true" then trigger. 
	 * @return nothing
	 */
<<<<<<< HEAD
	private void trig(String channel, Time t) {
		if(!power) return; 	
		int channelInt = Integer.parseInt(channel);
		Channel temp = channels[channelInt];
=======
	private int trig(String channel, Time t) {
		if(power) { 	
			int channelInt = Integer.parseInt(channel);
			Channel temp = channels[channelInt];
			
			if(temp.getState()) {
				
				if(channelInt % 2 != 0) { //odd channel are start channels
					
					if(cur_race.getReadyRacers().isEmpty()) {
						//generate bib number between [100, 999] not already in use
						boolean in_use = true;
						
						do {
							bib_count = ++bib_count;
							if(!used_bibs.contains(bib_count))
								in_use = false;
						}while(in_use);
						
						Racer r = new Racer(bib_count);
						cur_race.addReady(r);
					}
					
					cur_race.start();	//what time do I use? passed in val or currentsys?
					return 0;
				} else { //even channels are finish channels
					if(!cur_race.getCurrentRacers().isEmpty()) {
						cur_race.finish();	//same as start above what time do I give for finish?
					}
					return 0;
				}
				
>>>>>>> branch 'master' of https://github.com/nMik6/Ramen-Noodles.git

		if(temp.getState()) {
			if(cur_race == null) cur_race = new Race();
			if(channelInt % 2 != 0) cur_race.start(t);
			else cur_race.finish(t);
		}
	}

	
	/** Toggle the state of the channel at string converted to integer index of channels[]*/
	private void tog(String channel) {
		if(!power) 
			return;
		int intchan = Integer.parseInt(channel);
		channels[intchan].toggle();
	}
	
	private void event(String type) {
		if(!power)
			return;
		cur_race.setType(type);
	}
	
	//TODO not sure what is happening here with dnf and finish...Is that if a race is occuring already?
	private void newrun() {
		if(!power)
			return;
		for(Racer r: cur_race.getCurrentRacers()) {
			r.dnf();
			cur_race.finish(null);
		}
		finishedRaces.add(cur_race);
		cur_race = new Race();
	}
	
	//TODO may be cleaner to have this call a separate method in race that can dequeue everyone and give dnfs
	private void endrun() {
		if(!power)
			return;
		
		for(Racer r: cur_race.getCurrentRacers()) {
			r.dnf();
			cur_race.finish(null);
		}
		
		finishedRaces.add(cur_race);
		cur_race = null;
	}
	
	private void error() {
		if(!power)
			return;
		System.out.println("Invalid command");	//Logger should be able to handle string input
		
	}
	
	private void num(int bib) {
		if(!power || cur_race == null)
			return;
		cur_race.addReady(new Racer(bib));
	}
}
