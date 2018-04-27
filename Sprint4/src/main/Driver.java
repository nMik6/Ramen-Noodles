package main;

import java.util.Scanner;

public class Driver {
	public static void main(String[] args) {
		String command;
		Scanner stdin = new Scanner(System.in);
		do {
			System.out.println("To use the Simulator enter 's'. To use the GUI enter 'g'.");
			command = stdin.nextLine();
		} while (!(command.equals("s")||command.equals("g")));

		if (command.equalsIgnoreCase("s")) {
			Simulator ourSim = new Simulator();
			ourSim.start();
		} else if (command.equalsIgnoreCase("g")) {
			ClientPanel client = new ClientPanel();
			client.load();
		}

	}
}
