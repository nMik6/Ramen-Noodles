import java.util.HashMap;
import java.util.Map;


public class Bank {
	
	private Map<Account, Integer> accountList = new HashMap<>();
	
	public Bank() {
		
	}
	
	public void addAccount(Account account) {
		accountList.put(account, account.getAccountNumber());
	}
	
	public static Account validate(int accountNumber) {
		return null;
	}

}
