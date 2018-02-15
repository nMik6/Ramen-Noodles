package src.main;

public class Account {
	private int accountNumber;
	private int pin;
	private double balance;
	
	public Account() {
		accountNumber = 0;
		pin = 0;
		balance = 0;
	}
	
	public Account(int accountNumber,int pin) {
		this.accountNumber = accountNumber;
		this.pin = pin;
		balance = 0;
	}
	
	public Account(int accountNumber,int pin,double balance) {
		this.accountNumber = accountNumber;
		this.pin = pin;
		this.balance = balance;
	}
	
	public boolean validate(int pin) {
		return this.pin == pin;
	}
	
	public double getBalance() {
		return balance;
	}
	
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	public int getAccountNumber(){
		return accountNumber;
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Account))
			return false;
		Account acct = (Account) o;
		return this.accountNumber == acct.accountNumber && this.pin == acct.pin;
	}
	
	/*public void setPin(int newPin, int oldPin) {
		if(this.validate(oldPin))pin = newPin;
		else throw new IllegalArgumentException();
	}/**///Not actually necessary
}
