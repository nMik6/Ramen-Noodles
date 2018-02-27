package src.main;

/*
 * Account class virtualizes accounts. 
 */
public class Account {
	private int accountNumber;
	private int pin;
	private double balance;
	
	public Account() {
		this.accountNumber = 0;
		this.pin = 0;
		this.balance = 0;
	}
	
	public Account(int accountNumber,int pin) {
		this.accountNumber = accountNumber;
		this.pin = pin;
		this.balance = 0;
	}
	
	public Account(int accountNumber,int pin,double balance) {
		this.accountNumber = accountNumber;
		this.pin = pin;
		this.balance = balance;
	}
	
	/**
	 * Validates the pin number of the account.
	 * If the input pin is the pin number of the account, the account is validated, and the input pin is considered valid.
	 * @param pin
	 * @return true if pin is valid, otherwise false
	 */
	public boolean validate(int pin) {
		return this.pin == pin;
	}
	
	/**
	 * Get the value of the account's balance
	 * @return balance
	 */
	public double getBalance() {
		return balance;
	}
	
	/**
	 * Changes the account's balance to the value of input
	 * @param balance
	 */
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	/**
	 * Get the value of the account number
	 * @return accountNumber
	 */
	public int getAccountNumber(){
		return accountNumber;
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Account))
			return false;
		Account acct = (Account) o;
		return (this.accountNumber == acct.accountNumber && this.pin == acct.pin) && this.balance == acct.balance;
	}
}

