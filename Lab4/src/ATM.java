import java.util.Scanner;


public class ATM {
	
	private Account account;
	private int accountNumber;
	
	private Scanner input = new Scanner(System.in);
	
	public ATM() {
		
	}
	
	public ATM(Card card) {
		this.accountNumber = card.getAccountNumber();
		this.account = Bank.validate(accountNumber);
		start();
	}
	
	public void start() {
		System.out.println("Please enter your pin...");
		int pin = input.nextInt();
		
		if (account.validate(pin)) {
			System.out.println("You have entered an incorrect pin.");
		} else {
			String choice;
			do {
				System.out.println("What would you like to do? Withdraw = W, Deposit = D");
				choice = input.next();
				if (!choice.equalsIgnoreCase("W") || !choice.equalsIgnoreCase("D"))
					System.out.println("Invalid input...");
			} while (!choice.equalsIgnoreCase("W") || !choice.equalsIgnoreCase("D"));
			
			if (choice.equalsIgnoreCase("W")) 
				startWithdraw();
			else if (choice.equalsIgnoreCase("D")) 
				startDeposit();
		}
		
	}
	
	private void startDeposit() {
		
	}

	private void startWithdraw() {
	
	}

	private void withdraw(int amount) {
		
	}
	
	private void deposit(int amount) {
		
	}
}