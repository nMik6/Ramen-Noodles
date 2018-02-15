package src.main;
import java.util.HashMap;
import java.util.Map;


public class Bank {
	
	private Map<Integer, Account> accountList = new HashMap<>();
	
	public Bank() {
		
	}
	
	public void addAccount(Account account) {
		accountList.put(account.getAccountNumber(), account);
	}
	
	public Account validate(int accountNumber, int pin) {
		Account account = accountList.get(accountNumber);
		if (account == null) throw new IllegalArgumentException();
		if (account.validate(pin)) return account;
		else throw new IllegalArgumentException();
	}
	
	public boolean withdraw(Account account, double amount) {
		double balance = account.getBalance();
		if (amount > balance) return false;	
		account.setBalance(balance - amount);		
		return true;
	}
	
	/*
	 * Overloaded withdraw() method for atm testing purposes. 
	 */
	public boolean withdraw(int accountNum, int pin, double amount) {
		Account account = validate(accountNum, pin);
		return withdraw(account, amount);
	}
	
	public boolean deposit(Account account, double amount) {
		double balance = account.getBalance();
		account.setBalance(balance + amount);
		return true;
	}
	
	/*
	 * Overloaded withdraw() method for atm testing purposes. 
	 */
	public boolean deposit(int accountNum, int pin, double amount) {
		Account account = validate(accountNum, pin);
		return deposit(account, amount);
	}

}
