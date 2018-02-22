package src.main;


//printer takes a time, the transaction Number, and the amount from an ATM transaction.
//Returns the data in a string in the format <time> <transaction> <amount>
public class Printer {
	
	private int time;
	private int transactionNumber;
	private int amount;
	
	
	public Printer(int time, int transactionNumber, int amount)
	{
		this.time = time;
		this.transactionNumber = transactionNumber;
		this.amount = amount;
		
	}
	
	public String print() {
		return time + " " + transactionNumber + " " + amount;
	}

}
