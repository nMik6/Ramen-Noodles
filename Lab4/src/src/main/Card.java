package src.main;

/*Written by: Nathan Mikelonis

* Edited by:
* Verbal Input: Skyler Keough (discussed how card should be used in the program)
*/

/*
 * Class for virtual card. Stores account number only. 
 */
public class Card {
	
	private int accountNumber;
	
	public Card(int accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	public int getAccountNumber() {
		return accountNumber;
	}

}
