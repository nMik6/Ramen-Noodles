package src.main;
//Created by: Skyler
//Cash Dispenser object to dispense the cash that the user has withdrawn from the ATM. Would map to a physical ATM dispenser if it was a real ATM
//A physical dispenser could actually fail due to mechanical error or being out of cash, but our simulated ATM cannot fail.

//CashDispenser object to dispense cash.
public class CashDispenser {
	int amount; // amount to be dispensed
	public CashDispenser(int amount) {
		this.amount = amount;
	}
	
	public boolean Dispense() { //If this is called, will always return true that cash was successfully dispensed.
		return true;
	}

	
	
}
