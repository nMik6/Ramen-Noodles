package src.test;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import src.main.Account;
import src.main.ATM;
import src.main.Bank;
import src.main.CardReader;
import src.main.CashDispenser;
import src.main.Printer;
import src.main.Simulator;

/*
 * Written by: Thomas Crownover
 * 
 */
public class SimulatorTest {

	Account acct1, acct2, acct3, acct4, acct5, acct6;
	ATM atm;
	Bank bank;
	
	@Before
	public void setup() {
		acct1 = new Account(1234,5678);
		acct2 = new Account(9876,5432,80);
		acct3 = new Account(6789,4321,60);
		acct4 = new Account(1234,5678,0);
		acct5 = new Account(1234,5678,60);
		acct6 = new Account(9876,5432);
		
		bank = new Bank();
		bank.addAccount(acct1);
		bank.addAccount(acct2);
		bank.addAccount(acct3);
		
		atm = new ATM(bank);
	}
	
	/*
	 * Tests the equals() method with Bank and Account classes.
	 */
	@Test
	public void testEquals() {

		Bank bank1 = new Bank();
		Bank bank2 = new Bank();
		
		assertTrue("equals() returned false on identical banks", bank1.equals(bank2));
		assertFalse("equals() returned true on differing banks", bank.equals(bank1));

		bank1.addAccount(acct1);
		bank1.addAccount(acct2);
		bank1.addAccount(acct3);

		assertTrue("equals() returned false on identical banks", bank.equals(bank1));
		assertFalse("equals() returned true on differing banks", bank.equals(bank2));
		assertFalse("equals() returned true on differing banks", bank1.equals(bank2));
		
		Account testAcct1 = new Account(1234,5678);
		Account testAcct2 = new Account(9876,5432,80);
		Account testAcct3 = new Account(6789,4321,60);

		bank2.addAccount(testAcct1);
		bank2.addAccount(testAcct2);
		bank2.addAccount(testAcct3);
		
		assertTrue("equals() returned false on identical banks", bank.equals(bank2));
		assertTrue("equals() returned false on identical banks", bank1.equals(bank2));

		assertTrue("equals() returned false on identical accounts", acct1.equals(acct4));
		assertFalse("equals() returned true on differing accounts", acct1.equals(acct2));
		assertFalse("equals() returned true on differing accounts", acct1.equals(acct5));
		assertFalse("equals() returned true on differing accounts", acct2.equals(acct6));
		
		acct1.setBalance(60);
		acct2.setBalance(0);
		acct4.setBalance(60);

		assertTrue("equals() returned false on identical accounts", acct1.equals(acct4));
		assertTrue("equals() returned false on identical accounts", acct1.equals(acct5));
		assertTrue("equals() returned false on identical accounts", acct2.equals(acct6));

		assertTrue("equals() returned false on identical banks", bank.equals(bank1));
		assertFalse("equals() returned true on differing banks", bank.equals(bank2));
		assertFalse("equals() returned true on differing banks", bank1.equals(bank2));
		
	}
	
	@Test
	public void testSimulatorParse() {
		
	}
	
	/*
	 * Tests public Bank class methods
	 */
	@Test
	public void testBank() {

		assertTrue("Bank account failed to validate", bank.validateAccount(1234));
		assertTrue("Bank account failed to validate", bank.validateAccount(9876));
		assertTrue("Bank account failed to validate", bank.validateAccount(6789));
		assertFalse("Invalid bank account validated", bank.validateAccount(5678));
		assertFalse("Invalid bank account validated", bank.validateAccount(5432));
		assertFalse("Invalid bank account validated", bank.validateAccount(4321));

		assertTrue("Account, PIN failed to validate", acct1.equals(bank.validate(1234, 5678)));
		assertTrue("Account, PIN failed to validate", acct2.equals(bank.validate(9876, 5432)));
		assertTrue("Account, PIN failed to validate", acct3.equals(bank.validate(6789, 4321)));

		assertFalse("Account with invalid PIN validated", acct1.equals(bank.validate(1234, 5432)));
		assertFalse("Account with invalid PIN validated", acct2.equals(bank.validate(9876, 4321)));
		assertFalse("Account with invalid PIN validated", acct3.equals(bank.validate(6789, 5678)));

		assertFalse("Invalid account, PIN validated", acct1.equals(bank.validate(5678, 1234)));
		assertFalse("Invalid account, PIN validated", acct2.equals(bank.validate(5432, 9876)));
		assertFalse("Invalid account, PIN validated", acct3.equals(bank.validate(4321, 6789)));

		assertTrue("Returned incorrect balance", bank.getBalance(acct1) == 0);
		assertTrue("Returned incorrect balance", bank.getBalance(acct2) == 80);
		assertTrue("Returned incorrect balance", bank.getBalance(acct3) == 60);
		assertFalse("Returned incorrect balance", bank.getBalance(acct4) == 40);

		assertFalse("Withdrew more money than account held", bank.withdraw(acct2, 100));
		assertTrue("Withdraw failed for unknown reason", bank.withdraw(acct2, 10));
		assertTrue("Returned incorrect balance", bank.getBalance(acct2) == 70);

		assertFalse("Withdrew more money than account held", bank.withdraw(9876, 5432, 100));
		assertTrue("Withdraw failed for unknown reason", bank.withdraw(9876, 5432, 10));
		assertTrue("Returned incorrect balance", bank.getBalance(acct2) == 60);
		
		assertFalse("Withdraw made from nonexistant account", bank.withdraw(0, 0, 100));
		assertFalse("Withdraw made from nonexistant account", bank.withdraw(null, 100));
		
		assertTrue("Deposit failed for unknown reason", bank.deposit(acct1, 100));
		assertFalse("Returned unupdated balance", bank.getBalance(acct1) == 0);
		assertTrue("Returned incorrec balance", bank.getBalance(acct1) == 100);
		
		assertTrue("Deposit failed for unknown reason", bank.deposit(1234, 5678, 100));
		assertFalse("Returned unupdated balance", bank.getBalance(acct1) == 100);
		assertTrue("Returned incorrec balance", bank.getBalance(acct1) == 200);

		assertFalse("Deposit made to nonexistant account", bank.deposit(0, 0, 100));
		assertFalse("Deposit made to nonexistant account", bank.deposit(null, 100));

		assertFalse("Nonexistant account validated", bank.validateAccount(0));
		bank.addAccount(new Account());
		assertTrue("Newly added account failed to validate", bank.validateAccount(0));
		
		assertFalse("Withdrew more money than account held", bank.withdraw(0, 0, 100));
		assertTrue("Deposit failed for unknown reason", bank.deposit(0, 0, 100));
		assertTrue("Withdraw failed for unknown reason", bank.withdraw(0, 0, 100));
		
		Account tmp = bank.validate(9876, 5432);
		
		assertTrue("Returned incorrect balance", tmp.getBalance() == 60);
		
		bank.addAccount(acct6);
		
		tmp = bank.validate(9876, 5432);

		assertFalse("Returned incorrect balance", tmp.getBalance() == 60);
		assertTrue("Returned incorrect balance", tmp.getBalance() == 0);
		
	}
	
	/*
	 * Tests public ATM class methods
	 */
	@Test
	public void testATM() {

		assertTrue("Button press worked without active account", atm.start("button", "w") == -1);
		assertTrue("Num command worked without active account", atm.start("num", "10") == -1);

		assertTrue("Invalid card validated", atm.start("cardread", "0") == -1);
		assertTrue("Card failed to validate", atm.start("cardread", "1234") == 0);

		assertTrue("Button press accepted before account validation", atm.start("button", "w") == -1);

		assertTrue("Invalid PIN number validated", atm.start("num", "0") == -1);
		assertTrue("Account validation failed", atm.start("num", "5678") == 0);
		
	}
	
	/*
	 * Tests Account class methods
	 */
	@Test
	public void testAccount() {

		assertTrue("PIN number failed to validate", acct1.validate(5678));
		assertTrue("PIN number failed to validate", acct2.validate(5432));
		assertTrue("PIN number failed to validate", acct3.validate(4321));
		assertFalse("Invalid PIN number validated", acct2.validate(5678));

		assertTrue("Returned incorrect balance", acct1.getBalance() == 0);
		assertTrue("Returned incorrect balance", acct2.getBalance() == 80);
		assertTrue("Returned incorrect balance", acct3.getBalance() == 60);
		assertTrue("Returned incorrect balance", acct4.getBalance() == 0);
		assertFalse("Returned incorrect balance", acct1.getBalance() == 80);
		assertFalse("Returned incorrect balance", acct2.getBalance() == 60);
		assertFalse("Returned incorrect balance", acct3.getBalance() == 0);

		assertTrue("Returned incorrect account number", acct1.getAccountNumber() == 1234);
		assertTrue("Returned incorrect account number", acct2.getAccountNumber() == 9876);
		assertTrue("Returned incorrect account number", acct3.getAccountNumber() == 6789);
		assertTrue("Returned incorrect account number", acct5.getAccountNumber() == 1234);
		assertTrue("Returned incorrect account number", acct6.getAccountNumber() == 9876);
		
		acct1.setBalance(80);
		acct2.setBalance(60);
		acct3.setBalance(0);
		acct4.setBalance(40);

		assertTrue("Returned incorrect balance", acct1.getBalance() == 80);
		assertTrue("Returned incorrect balance", acct2.getBalance() == 60);
		assertTrue("Returned incorrect balance", acct3.getBalance() == 0);
		assertTrue("Returned incorrect balance", acct4.getBalance() == 40);

		assertTrue("PIN number failed to validate", acct1.validate(5678));
		assertTrue("PIN number failed to validate", acct2.validate(5432));
		assertTrue("PIN number failed to validate", acct3.validate(4321));
		assertTrue("PIN number failed to validate", acct4.validate(5678));

		assertTrue("Returned incorrect account number", acct1.getAccountNumber() == 1234);
		assertTrue("Returned incorrect account number", acct2.getAccountNumber() == 9876);
		assertTrue("Returned incorrect account number", acct3.getAccountNumber() == 6789);
		assertTrue("Returned incorrect account number", acct1.getAccountNumber() == 1234);
		
	}
	
}
