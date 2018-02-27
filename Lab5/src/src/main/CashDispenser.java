package src.main;
//Created by: Skyler
//Cash Dispenser object to dispense the cash that the user has withdrawn from the ATM. Would map to a physical ATM dispenser if it was a real ATM
//Our ATM has a total cash amount that cannot be exceeded.

//CashDispenser object to dispense cash.
public class CashDispenser {
	
	private int totalCash;
	public CashDispenser() {
		this.totalCash = 100000;  //Total cash in the cash dispenser.
	}
	
	public boolean dispense(int val) { //If this is called, will return true if there is enough cash so that money can be withdrawn. If false, not enough money.
		if (totalCash - val < 0)
		{
			return false;
		}
		totalCash = totalCash - val;  
		return true;
	}

	
	
}
