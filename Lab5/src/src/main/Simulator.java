package src.main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Stream;

public class Simulator {
	
	private String fileName = "transactions.txt";
	private ATM atm;
	
	private Scanner in = new Scanner(System.in);

	/**
	 * Starts the ATM simulation. The user is prompted to read from
	 * a file or read from their input (card).
	 */
	public void startSimulator() {
		String input;
		do {
			System.out.println("Enter command: (from file 'f' , from console 'c')");
			input = in.nextLine();
		} while (!input.equalsIgnoreCase("f") || !input.equalsIgnoreCase("c"));
		
		if (input.equalsIgnoreCase("f"))
			readFromFile();
		else if (input.equalsIgnoreCase("c"))
			readFromConsole();
	}
	
	/**
	 * Grabs a list of commands directly from the file transactions.txt
	 * to begin a transaction with ATM
	 */
	private void readFromFile() {
		String[] fileCommands = null;
		
		if (!(new File(fileName).exists())) {
			System.out.println("Missing file transactions.txt");
			return;
		}
		
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			fileCommands = stream.toArray(size -> new String[size]);
		} catch (IOException e) { e.printStackTrace(); }
		
		for (int i = 0; i < fileCommands.length; ++ i) {
			String[] command = fileCommands[i].split(" ");
			if (atm.start(command[0], command[1]) != 0)
				return;
		}
	}
	
	/**
	 * Reads information from the users input until their
	 * transaction is over or they cancel.
	 */
	private void readFromConsole() {
		String input;
		String[] cmds;
		int response;
		
		do {
			input = in.nextLine();
			cmds = input.split(" ");
			response = atm.start(cmds[0], cmds[1]);
		} while(!input.equalsIgnoreCase("cancel") || response != 0);
		
		if (response == -1 && !input.equalsIgnoreCase("cancel")) {
			System.out.println("Please try again. ('cancel' to exit transaction.)");
			readFromConsole();
		}
	}
	
}
