package src.main;


/**
 * Printer class that prints out information from
 * a users interation at an ATM
 */
public class Printer {
	
	public Printer(){}
	
	/**
	 * 
	 * @param time the time at which the event occured
	 * @param transactionNumber id associated with the transaction
	 * @param amount that was asked for by the user
	 * @return 0
	 */
	public int print(String time, String transactionNumber, String amount) {
		System.out.println(time + " " + transactionNumber + " " + amount);
		return 0;
	}
	
	/**
	 * 
	 * @param str
	 * @return 0
	 */
	public int print(String str) {
		System.out.println(str);
		return 0;
	}

}
