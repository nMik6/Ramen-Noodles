
public class Account {
	int accountNumber;
	int PIN;
	double balance;
	
	public Account() {
		accountNumber = 0;
		PIN = 0;
		balance = 0;
	}
	
	public Account(int a,int p) {
		accountNumber = a;
		PIN = p;
		balance = 0;
	}
	
	public Account(int a,int p, double b) {
		accountNumber = a;
		PIN = p;
		balance = b;
	}
}
