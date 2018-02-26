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
	public void simulatorParseTest() {
		
	}
	
	@Test
	public void bankTest() {
		
	}
	
	@Test
	public void atmTest() {
		
	}
	
	@Test
	public void accountTest() {
		
	}
	
}
