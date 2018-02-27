package src.main;
import java.util.HashMap;
import java.util.Map;


public class Bank {
	
	/**
	 * @param accountList HashMap of accounts with account numbers as keys
	 */
	private Map<Integer, Account> accountList = new HashMap<>();
	
	public Bank() {
		
	}
	
	/**
	 * Add account to accountList map with account number as key
	 */
	public void addAccount(Account account) {
		accountList.put(account.getAccountNumber(), account);
	}
	
	/**
	 * Validates the account number and pin. 
	 * Associated account number cannot be null. 
	 * Calls Account class's validate method on pin. 
	 * @return account if valid
	 * @return null if invalid account/pin combo
	 */
	public Account validate(int accountNumber, int pin) {
		Account account = accountList.get(accountNumber);
		if (account == null || !account.validate(pin)) return null;
		return account;
	}
	
	/**
	 * Method for ATM.readCard() to validate an account exist for the corresponding 
	 * card number
	 * @return true if account exists
	 */
	public boolean validateAccount(int accountNumber) {
		return accountList.get(accountNumber) != null;
	}
	
	/**
	 * Withdraws amount from account. 
	 * @Return true if successful withdrawal, false otherwise
	 */
	public boolean withdraw(Account account, double amount) {
		if(account == null) return false;
		double balance = account.getBalance();
		if (amount > balance) return false;	
		account.setBalance(balance - amount);		
		return true;
	}
	
	/**
	 * Overloaded withdraw() method for atm testing purposes. 
	 */
	public boolean withdraw(int accountNum, int pin, double amount) {
		Account account = validate(accountNum, pin);
		return withdraw(account, amount);
	}
	
	public boolean deposit(Account account, double amount) {
		if(account == null) return false;
		double balance = account.getBalance();
		account.setBalance(balance + amount);
		return true;
	}
	
	/**
	 * Overloaded withdraw() method for atm testing purposes. 
	 */
	public boolean deposit(int accountNum, int pin, double amount) {
		Account account = validate(accountNum, pin);
		return deposit(account, amount);
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Bank)) return false;
		Bank bank = (Bank) o;
		return this.accountList.equals(bank.accountList);
	}
	
	
	public double getBalance(Account account) {
		return account.getBalance();
	}
	/*
	* Need to generate hashCode
	*/
	//TODO

}