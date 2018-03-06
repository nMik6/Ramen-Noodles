import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Driver {
	
	private static boolean empParsing = false;
	private static String list = "";
	private static DirectoryProxy proxy;
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		String cmd = "";
		
		while (!(cmd.equalsIgnoreCase("exit"))) {
			System.out.println("Please enter a command: ");
			cmd = in.nextLine();
			if (!parser(cmd)) System.out.println("invalid command");
		}
		in.close();
	}
	
	public static boolean parser(String cmd) {
		String[] set = cmd.split(" ");
		if (set.length < 1) return false;
		
		if (set.length == 1)
			return cmdParse(set[0]);
		else if (set.length == 4 && empParsing)
			return empParse(set);
		
		return false;
	}
	
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
			proxy.add(list);
			empParsing = false;
			return true;
		case "exit":
			return true;
		default:
			return false;
		}
	}
	
	public static boolean empParse(String[] emp) {
		Pattern numP = Pattern.compile("([0-9])");
		Pattern letP = Pattern.compile(("[a-zA-Z]+"));

		Matcher m;
		
		for (int i = 0; i < emp.length; ++ i) {
			if (i != 2) {
				m = numP.matcher(emp[i]);
				if (m.find()) return false;
			} else {
				m = letP.matcher(emp[i]);
				if (m.find()) return false;
			}
		}
		
		list += emp[0] + " " + emp[1] + " " + emp[2] + " " + emp[3] + " ";
		
		return true;
	}

}
