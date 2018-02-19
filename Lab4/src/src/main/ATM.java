package src.main;
import java.util.Scanner;


	/*Written by:
	* Edited by: Stephen Staudt
	* Verbal Input: Stephen Staudt
	*/
public class ATM {

	private Account account;
	private Bank bank; 	
	private int accountNumber;

	private Scanner input = new Scanner(System.in);

	public ATM() {

	}

	/*
	 * Constructor takes bank from which to store and retrieve accounts
	 */
	public ATM(Bank bank) {
		this.bank = bank;
	}

	/*
	 * User console interface with text output and input for atm operation
	 */
	public int start() {
		System.out.println("Input Card number: ");
		this.accountNumber = input.nextInt();
		if(accountNumber <0) return -1; //exit case
		
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
				if (!choice.equalsIgnoreCase("W") && !choice.equalsIgnoreCase("D"))
					System.out.println("Invalid input...");
			} while (!(choice.equalsIgnoreCase("W") || choice.equalsIgnoreCase("D")));

			if (choice.equalsIgnoreCase("W")) 
				startWithdraw();
			else if (choice.equalsIgnoreCase("D")) 
				startDeposit();
		
		}
		return 0;
	}

	/*
	 * Prompt user for deposit amount and carry out operation
	 */
	private void startDeposit() {
		System.out.println("How much would you like to deposit?");
		double amount = input.nextDouble();
		if(amount > 0) {
			bank.deposit(account, amount);
			System.out.println("You deposited: $" + amount);
			System.out.println("Your new balance is: $" + account.getBalance());
		}

	}

	/*
	 * Prompt user for withdraw amount and carry out operation
	 */
	private void startWithdraw() {
		System.out.println("How much would you like to withdraw?");
		double amount = input.nextDouble();

		if (bank.withdraw(account, amount)) {
			System.out.println("You withdrew: $" + amount);
			System.out.println("Your new balance is: $" + account.getBalance());
		} else {
			System.out.println("Insufficient funds");
		}
	}

}
