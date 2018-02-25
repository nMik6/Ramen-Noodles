package src.main;


//printer takes a time, the transaction Number, and the amount from an ATM transaction.
//Returns the data in a string in the format <time> <transaction> <amount>
public class Printer {
	
	/*private int time;
	private int transactionNumber;
	private int amount;
	*/
	
	public Printer(){}
	
	//Returns the to-be-printed data string to the ATM class 
	public int print(String time, String transactionNumber, String amount) {
		System.out.println(time + " " + transactionNumber + " " + amount);
		return 0;
	}
	public int print(String str) {
		System.out.println(str);
		return 0;
	}

}
