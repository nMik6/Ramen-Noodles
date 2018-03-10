package src.main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Stream;

public class Simulator {
	
	private String fileName = "transactions.txt";
	private Bank simBank = new Bank();
	private ATM simAtm = new ATM(simBank);
	
	private Scanner in = new Scanner(System.in);

	/**
	 * Starts the ATM simulation. The user is prompted to read from
	 * a file or read from their input (card).
	 */
	public void startSimulator() {
		String input;
		
		Account firstAccount = new Account(1234, 6789, 80);
		Account secondAccount = new Account(6789, 4321, 20);

		simBank.addAccount(firstAccount);
		simBank.addAccount(secondAccount);

		//Card firstCard = new Card(1234);
		//Card secondCard = new Card(6789);
		
		do {
			System.out.println("Enter command: (from file 'f' , from console 'c')");
			input = in.nextLine();
		} while (!(input.equalsIgnoreCase("f") || input.equalsIgnoreCase("c")));
		
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
			parse(command);
		}
	}
	
	/**
	 * Reads information from the users input until their
	 * transaction is over or they cancel.
	 */
	private void readFromConsole() {
		String input;
		String[] cmds;
		
		System.out.println("Enter Card");
		do {
			input = in.nextLine();
			cmds = input.split(" ");
			if (!parse(cmds)) System.out.println("Please try again. ('cancel' to exit transaction.)");
		}  while (!input.equalsIgnoreCase("cancel"));
		System.out.println("You have canceled your transaction.");
	}
	
	/**
	 * Checks that the commands are of proper length prior to sending
	 * them to the atm
	 * @param cmds array of commands to use the atm
	 * @return true if the cmds has the correct bounds
	 */
	private boolean parse(String[] cmds) {
		int length = cmds.length;
		if (length == 2) simAtm.start(cmds[0], cmds[1]);
		else if (length == 3) simAtm.start(cmds[0], cmds[1], cmds[2]);
		else return false;
		
		return true;
	}
}
