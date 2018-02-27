package src.main;

import java.util.ArrayList;
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
	Queue<Race> races;
	Channel[] channels;
	ArrayList<Race> finishedRaces;
	Time time;
	
	private Scanner stdin = new Scanner(System.in);
	
	public void start() {
			power = false;
			races = null;
			finishedRaces = null;
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
		
		switch(commandLine[0]) {
			case "exit":
				exit();
				
			case "reset":
				reset();
			
			case "time":
				//verifyLength(length, 2);	
				time = new Time(commandLine[2]);
				break;
			
			case "power":
				return power();
			
			case "conn":
				//verifyLength(length, 3);
			
			case "tog":
				//verifyLength(length, 2);	
				tog(commandLine[1]);
			
			case "trig":
				//verifyLength(length, 2);	
				trig(commandLine[1]);
			
		}

		return 0;
	}
	
	/** Turn the power on and off (but stay in the simulator)*/
	private int power() {
		power = !power;
		if(races.isEmpty())
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
	
	/** Set channel's sensor type. */
	private void conn(String sensor, int channel) {
		if(!power) 
			return;
		channels[channel].conn(sensor);
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
