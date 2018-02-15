package src.test;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import src.main.ATM;
import src.main.Account;
import src.main.Bank;
import src.main.Card;

public class AtmTest {

	Bank myBank;
	Card card1, card2;
	Account acc1, acc2;
	ATM atm;
	
	@Before
	public void setup() {
		acc1 = new Account(1234,6789);
		acc2 = new Account(6789,4321);
		
		acc1.setBalance(80);
		acc2.setBalance(60);
		
		myBank = new Bank();
		myBank.addAccount(acc1);
		myBank.addAccount(acc2);
		
		card1 = new Card(acc1.getAccountNumber());
		card2 = new Card(acc1.getAccountNumber());
		
		
		atm = new ATM(myBank);
		 
	}
	

	@Test
	public void testWithdrawal20Acc1() {
		
		int acc1_num = acc1.getAccountNumber();
		
		assertEquals("Account number for Account 1 is incorrect", acc1_num, 1234);
		assertTrue("Account pin for Account 1 is incorrect", myBank.validate(acc1_num,6789).equals(acc1));
		assertTrue("Account 1 initial balance is incorrect", acc1.getBalance() == 80.0);

		
		myBank.withdraw(acc1_num,6789, 20);
		assertTrue("Withdrawal of $20 failed for Account 1", acc1.getBalance() == 60);
	}
	
	@Test
	public void testWithdrawal80Acc1() {
		
		
		int acc1_num = acc1.getAccountNumber();
		
		assertEquals("Account number for Account 1 is incorrect", acc1_num, 1234);
		assertTrue("Account pin for Account 1 is incorrect", myBank.validate(acc1_num,6789).equals(acc1));
		assertTrue("Account 1 initial balance is incorrect", acc1.getBalance() == 80.0);
		
		myBank.withdraw(acc1_num,6789, 80);
		assertTrue("Withdrawal of $80 failed for Account 1", acc1.getBalance() == 0);
	}
	
	@Test
	public void testIncorrectValidationAcc2() {
		
		int acc2_false = 5647;
		assertTrue("Account number for account 2 is not"+acc2_false, acc2_false != acc2.getAccountNumber());
		
		try {
			myBank.validate(acc2_false, 4321);
			fail("Improper validation of Account 2 did not throw expected Exception");
		} catch (IllegalArgumentException e) {	
		}
	}
	
	@Test
	public void testDeposit20Acc2() {
		int acc2_num = acc2.getAccountNumber();
		
		myBank.deposit(acc2_num, 4321, 20);
		assertTrue("Deposit of $20 failed for Account 2", acc2.getBalance() == 80);
	}
}
