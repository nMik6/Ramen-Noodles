package src.main;
//Created by: Skyler
//Cash Dispenser object to dispense the cash that the user has withdrawn from the ATM. Would map to a physical ATM dispenser if it was a real ATM
//Our ATM has a total cash amount that cannot be exceeded.

//CashDispenser object to dispense cash.
public class CashDispenser {
	int amount; // amount to be dispensed
	int totalCash = 100000; // totalCash in dispenser
	public CashDispenser(int amount) {
		this.amount = amount;
	}
	
	public boolean Dispense(int val) { //If this is called, will return true if there is enough cash so that money can be withdrawn. If false, not enough money.
		if (amount - val < 0)
		{
			return false;
		}
		amount = amount - val;  
		return true;
	}

	
	
}
