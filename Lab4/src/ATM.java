import java.util.Scanner;


public class ATM {
	
	private Account account;
	private Bank bank; 	//convert to arraylist to support more than one bank?
	private int accountNumber;
	
	private Scanner input = new Scanner(System.in);
	
	public ATM() {
		
	}
	
	public ATM(Bank bank) {
		this.bank = bank;
		start();
	}
	
	public void start() {
		System.out.println("Input Card number: ");
		int accountNumber = input.nextInt();
		
		System.out.println("Please enter your pin: ");
		int pin = input.nextInt();
		
		account = bank.validate(accountNumber, pin);
		if (account == null) {
			System.out.println("Account does not exist for this Card Number and Pin combination");
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
		System.out.println("How much would you like to deposit?");
		double amount = input.nextDouble();
		deposit(amount);
		System.out.println("You deposited: $" + amount);
		System.out.println("Your new balance is: $" + account.getBalance());
	}

	private void startWithdraw() {
		System.out.println("How much would you like to withdraw?");
		double amount = input.nextDouble();
		
		if (withdraw(amount)) {
			System.out.println("You withdrew: $" + amount);
			System.out.println("Your new balance is: $" + account.getBalance());
		} else {
			System.out.println("Insufficient funds");
		}
	}

	protected boolean withdraw(double amount) {
		double balance = account.getBalance();
		if (amount > balance) return false;	
		account.setBalance(balance - amount);		
		return true;
	}
	
	/*
	 * Overloaded withdraw() method for atm testing purposes. 
	 */
	protected boolean withdraw(int accountNum, int pin, double amount) {
		account = bank.validate(accountNum, pin);
		return withdraw(amount);
	}
	
	protected boolean deposit(double amount) {
		double balance = account.getBalance();
		account.setBalance(balance + amount);
		return true;
	}
	
	/*
	 * Overloaded withdraw() method for atm testing purposes. 
	 */
	protected boolean deposit(int accountNum, int pin, double amount) {
		account = bank.validate(accountNum, pin);
		return deposit(amount);
	}
	
}
