
public class Driver {
	
	
	public static void main() {
	Bank bank = new Bank();
	
	Account firstAccount = new Account(1234, 6789, 80);
	Account secondAccount = new Account(6789, 4321, 20);
	
	bank.addAccount(firstAccount);
	bank.addAccount(secondAccount);
	
	Card firstCard = new Card(1234);
	Card secondCard = new Card(6789);
	
	ATM firstATM = new ATM(firstCard);
	ATM secondATM = new ATM(secondCard);
	
	firstATM.start();
	
	secondATM.start();
	
	}

}
