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
		if(account == null)throw new IllegalArgumentException();
		if(account.validate(pin))return account;
		else throw new IllegalArgumentException();
	}
	
	

}
