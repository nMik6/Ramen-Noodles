import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class AtmTest {

	Bank myBank;
	Card card1, card2;
	Account acc1, acc2;
	
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
	}
	
	@Test
	public void testWithdrawalAcc1() {
		
		int acc1_num = acc1.getAccountNumber();
		
		assertEquals("Account number for Account1 is incorrect", acc1_num, 1234);
		assertTrue("Account pin for Account1 is incorrect", myBank.validate(acc1_num,6789).equals(acc1));
		assertTrue("Account1 balance is incorrect", acc1.getBalance() == 80.0);
		
		
	}
}
