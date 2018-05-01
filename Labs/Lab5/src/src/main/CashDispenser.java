package src.main;

/**
 * CashDispenser object to dispense the cash that the user has 
 * withdrawn from the ATM. Would map to a physical ATM dispenser 
 * if it was a real ATM. Holds a total cash amount that 
 * cannot be exceeded.
 */
public class CashDispenser {
	
	private int totalCash;
	
	/**
	 * CashDispenser constructor assigns the amount
	 * of cash that is in the dispenser
	 */
	public CashDispenser() {
		this.totalCash = 100000;
	}
	
	/**
	 * Dispense cash from atm
	 * @param val amount of cash
	 * @return true if there is enough cash
	 */
	public boolean dispense(int val) { 
		if (totalCash - val < 0) return false;
		totalCash = totalCash - val;  
		return true;
	}

	
	
}
