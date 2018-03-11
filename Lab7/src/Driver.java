import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;

public class Driver {
	
	private static boolean empParsing = false;
	private static String list = "";
	private static DirectoryProxy proxy = new DirectoryProxy(new MainDirectory());
	private static ArrayList<Employee> empList = new ArrayList<Employee>();
	private static Gson g = new Gson();
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		String cmd = "";
		System.out.println("Please enter a command: (exit to leave)");
		while (!(cmd.equalsIgnoreCase("exit"))) {
			cmd = in.nextLine();
			if (!parser(cmd)) System.out.println("invalid command");
		}
		in.close();
	}
	
	/**
	 * Parses the users input to the correct commands
	 * @param cmd the command the user has entered
	 * @return true if the command is real and executed properly
	 */
	public static boolean parser(String cmd) {
		String[] set = cmd.split(" ");
		if (set.length < 1) return false;
		
		if (set.length == 1)
			return cmdParse(set[0]);
		else if (set.length == 4 && empParsing)
			return empParse(set);
		
		return false;
	}
	
	/**
	 * Handles the parsing for commands that are entered
	 * @param cmd the command
	 * @return true if the command is valid and executed properly
	 */
	public static boolean cmdParse(String cmd) {
		switch (cmd.toLowerCase()) {
		case "add":
			empParsing = true;
			return true;
		case "clr":
			proxy.clear();
			return true;
		case "print":
			proxy.print();
			return true;
		case "end":
			list = g.toJson(empList);
			proxy.add(list);
			empParsing = false;
			return true;
		case "exit":
			return true;
		default:
			return false;
			
		}
	}
	
	/**
	 * Parser for an employee's information
	 * @param emp the employee to add
	 * @return true if the employee information is entered correctly
	 */
	public static boolean empParse(String[] emp) {
		Pattern numP = Pattern.compile("([0-9])");
		Pattern letP = Pattern.compile(("[a-zA-Z]+"));
		Matcher m;
		
		for (int i = 0; i < emp.length; ++ i) {
			if (i != 2) {
				m = numP.matcher(emp[i]);
				if (m.find()) {
					System.out.println("Numbers are not allowed here.");
					return false;
				}
			} else {
				m = letP.matcher(emp[i]);
				if (m.find()) {
					System.out.println("Letters are not allowed here.");
					return false;
				}
			}
		}
		Employee e = new Employee(emp[0], emp[1], emp[2], emp[3]);
		empList.add(e);
		
		return true;
	}

}
