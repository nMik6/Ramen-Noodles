import java.util.Scanner;


public class ATM {
	
	private Account account;
	private int accountNumber;
	
	public ATM() {
		
	}
	
	public ATM(Card card) {
		this.accountNumber = card.getAccountNumber();
		this.account = Bank.validate(accountNumber);
		start();
	}
	
	public void start() {
		Scanner input = new Scanner(System.in);
		System.out.println("Please enter your pin...");
		int pin = input.nextInt();
		
		if (pin != account.getPin()) {
			System.out.println("You have entered an incorrect pin.");
		} else {
			System.out.println("What would you like to do? Withdraw = W, Deposit = D");
			String choice = input.next();
		}
		
	}
	
	public void withdraw(int amount) {
		
	}
	
	public void deposit(int amount) {
		
	}
}