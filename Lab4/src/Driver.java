import src.main.*;

	/*Written by:
	* Edited by: Stephen Staudt 
	* Gave Input:
	*/
public class Driver {


	public static void main(String[] args) {
		Bank bank = new Bank();

		Account firstAccount = new Account(1234, 6789, 80);
		Account secondAccount = new Account(6789, 4321, 20);

		bank.addAccount(firstAccount);
		bank.addAccount(secondAccount);

		Card firstCard = new Card(1234);
		Card secondCard = new Card(6789);

		ATM firstATM = new ATM(bank);

		int x;
		do{	x = firstATM.start(); } while(x==0);

	}

}
