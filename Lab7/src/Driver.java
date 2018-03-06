import java.util.Scanner;

public class Driver {
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		String cmd = "";
		
		while (!(cmd.equalsIgnoreCase("exit"))) {
			cmd = in.nextLine();
			if (parser(cmd));
		}
		
	}
	
	public boolean parser(String cmd) {
		
		return false;
	}

}
