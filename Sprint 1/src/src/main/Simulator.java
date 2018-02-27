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
	ArrayList<Race> finishedRaces;
	Time time;
	
	private Scanner stdin = new Scanner(System.in);
	
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
	
	private int readFromConsole() {
		String input;
		String[] cmds;
		int ret_status = 0;
		
		do {
		System.out.print("Enter command: ");
		command = stdin.nextLine();
		}while(!(command.equals("exit")||(command.endsWith("reset"))));
		
		if(command.equals("exit"))
			ret_status = exit();
		else if(command.equals("reset"))
			ret_status = reset();
	
		return ret_status;
	}
	
	private int readFromFile() {
		String[] fileCommands = null;
		int ret_status = 0;
		
		System.out.print("Enter the filename: ");
		String filename = stdin.nextLine();
		
		if(!(new File(filename).exists())) {
			System.out.println("Missing file transactions.txt");
			return -1;
		}
		
		try (Stream<String> stream = Files.lines(Paths.get(command))){
			fileCommands = stream.toArray(size -> new String[size]);
		} catch (IOException e) { e.printStackTrace();}
		
		for(int i = 0; i < fileCommands.length; ++i) {
			String[] command = fileCommands[i].split(" ");
			ret_status = parser(command);
		}
		return ret_status;
	}
	/*
	 * 
	 */
	private int parser(String[] commandLine) {
		int length = commandLine.length;
		
		switch(commandLine[0]) {
			case "exit":
				break;
			case "reset":
				break;
			case "time":
				if(length != 2) {
					//implement log function that send out put to logger and prints to screen;
					System.out.println("Invalid command");
					return -1;
				}	
				time = new Time(commandLine[2]);
				break;
			case "power":
				return power();
			case "conn":
				if(length != 3) {
					//log
					System.out.println("Invalid command");
					return -1;
				}
				
		}

		return 0;
	}
	
	/*
	 * Turn the power on and off (but stay in the simulator)
	 */
	public int power() {
		power = !power;
		if(races.isEmpty())
			return 0;
		return -1;
	}
	
	/*
	 * exit the simulator, no more commands processed except for reset and power?
	 */
	public int exit() {
		races = null;
		if(!races.isEmpty()) {
			return -1;
		}	
		return 0;
	}
	
	/*
	 * reset the sytem to the intial state
	 * how is this different than power
	 */
	public int reset() {
		races = null;
		return 0;
	}
}
