package src.test;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import src.main.Account;
import src.main.ATM;
import src.main.Bank;
import src.main.Card;
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
		atm = new ATM(bank);
	}
	
}
