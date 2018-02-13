
public class Driver {
	
	
	public static void main() {
	Bank bank = new Bank();
	
	Account first = new Account(1234, 6789, 80);
	Account second = new Account(6789, 4321, 20);
	
	bank.addAccount(first);
	bank.addAccount(second);
	
	}

}
