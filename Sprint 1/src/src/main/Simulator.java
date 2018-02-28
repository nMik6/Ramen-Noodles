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


public class Simulator {
	String command;
	Boolean power;
	Race cur_race;
	Channel[] channels;
	List<Race> finishedRaces;
	Time time;
	
	private Scanner stdin = new Scanner(System.in);
	
	public void start() {
			power = false;
			time = new Time();
			cur_race = new Race();
			finishedRaces = new ArrayList<>();
			command = null;
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
		String input;
		String[] cmds;
		
		do {
			System.out.print("Enter command: ");
			command = stdin.nextLine();
			cmds = command.split(" ");
			parse(cmds);
		}while(!(command.equals("exit")||(command.equals("reset"))));
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
					trig(commandLine[1]);
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
	private int power() {
		power = !power;
		if(cur_race.getCurrentRacers().isEmpty())
			return 0;
		return -1;
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
	
	/** Set current system time (I guess you can do that according to the tested input?)*/
	private void time(String t) {
		
	}
	
	/*
	 * Verify that channel state is "true" then trigger. 
	 * @return 0 if channel is allowed to be triggered, -1 if not
	 */
	private int trig(String channel) {
		if(power) { 		
			Channel temp = channels[Integer.parseInt(channel)];
			
			if(temp.getState()) {
				/*
				 * If the channel triggered state is true
				 * create racer in new race
				 * add race  to queue<race>
				 * ...
				 */
				return 0;
			}
		}
		return -1;
	}
	
	/** Toggle the state of the channel at string converted to integer index of channels[]*/
	private void tog(String channel) {
		if(!power) 
			return;
		int intchan = Integer.parseInt(channel);
		channels[intchan].toggle();
	}
	
}
