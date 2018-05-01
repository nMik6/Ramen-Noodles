package src.main;
import java.text.SimpleDateFormat;

/*
 * Simulator Class ATM receives a command from the simulator and 
 * the command arguments. The ATM class then validates values and executes
 * the command if valid.  
 */

public class ATM {
	
	private boolean cur_accnt; 	
	private CashDispenser cashDispenser;
	private int cur_pin;
	private Bank bank;
	private int accnt_num = -1;
	private Printer atmPrint = new Printer();
	private CardReader cardRead = new CardReader();
	private String curButton = "";
	private String timestamp;
	
	/**
	 * ATM constructor
	 * @param b the bank the atm will work with
	 */
	public ATM(Bank b) {
		bank = b;
		
		this.cashDispenser = new CashDispenser();
	}
	
	/**
	 * Generates the timestamp and starts the command reading
	 * @param cmd command to be read and executed
	 * @param val input from user/file
	 * @return 0 if the values are valid, -1 otherwise
	 */
	public int start(String cmd, String val) {
		timestamp = new SimpleDateFormat("HH:mm:ss").format(System.currentTimeMillis());
		return start(timestamp, cmd, val);
	}
	
	/**
	 * Executes the given command on the ATM to give the user
	 * access to their bank account
	 * @param tmstmp timestamp sent in by user or file
	 * @param cmd command to be read and executed
	 * @param val input from user/file
	 * @return 0 if the values are valid, -1 otherwise
	 */
	public int start(String tmstmp, String cmd, String val) {
		timestamp = tmstmp; //assumes timestamp is correctly formatted
		if(tmstmp == null || cmd == null || val == null)return -1;
		cmd = cmd.toLowerCase();
		
		switch (cmd) {
			case "cardread":
				return readCard(val);
				
			case "num":
				return readNum(val);
			
			case "dis":
				System.out.println(val);
				return 0;
				
			case "print":
				return atmPrint.print(val);
			
			case "button":
				return button(val);
				
			default:
				return -1;
				/*
				 * simulator.parse should take care of any improperly formatted commands
				 * but if it fails default integer value of -1 is returned  
				 */
		}
	}
	
	/**
	 * Reads in a card value and sets it to current card local variable
	 * @param val the card id
	 * @return 0 if account exists for card num else -1
	 */
	private int readCard(String val) {
		int card_num = cardRead.readCard(val);
		
		if(bank.validateAccount(card_num)) {
			accnt_num = card_num;
			curButton = "pin";
			System.out.println("Enter Pin");
			return 0;
		}
		
		return -1;
	}
	

	/**
	 * Reads the users pin or withdraw number
	 * @param val the amount/pin
	 * @return 0 if the balance/pin are valid, -1 otherwise
	 */
	private int readNum(String val) {
		int val_int;
		
		try{
			val_int = Integer.parseInt(val);
		}catch(NumberFormatException e) {
			return -1;
		}

		if(val_int < 0)
			return -1;
		
		switch (curButton) {
			case "w":
				if(cur_accnt) {
					double balance = bank.getBalance(bank.validate(accnt_num, cur_pin));
					
					if(val_int > balance) val_int = (int) balance;
					if(bank.withdraw(accnt_num, cur_pin, val_int)) {
						if(cashDispenser.dispense(val_int))	
							atmPrint.print(timestamp, "W", "$"+bank.getBalance(bank.validate(accnt_num, cur_pin)));
						else System.out.print("Insufficient Funds in ATM");
						System.out.println("Choose Transaction");
					}
				}
				else return -1;
				break;
				
			case "pin":
				if(bank.validate(accnt_num, val_int) != null) {
					cur_accnt = true;
					cur_pin = val_int;
					System.out.println("Choose Transaction");
				}
				else {
					System.out.println("Enter Pin");
					return -1;
				}
				break;
					
			default:
				System.out.println("-1");
				return -1;
		}
		
		return 0;
	}
	
	/**
	 * Takes the users choice of withdrawing money,
	 * checking their balance, or canceling the transaction
	 * @param val the action the user wants to perform
	 * @return 0 if valid action, -1 otherwise
	 */
	private int button(String val) {
		val = val.toLowerCase();
		
		switch (val) {
			case "w":
				if(!cur_accnt)
					return -1;
				curButton = "w";
				System.out.println("Amount?");
				break;
				
			case "cb":
				if(!cur_accnt)
					return -1;
				atmPrint.print(timestamp, "BAL", "$"+bank.getBalance(bank.validate(accnt_num, cur_pin)));
				break;
				
			case "cancel":	//does "Button cancel" just return to the command prompt and nullify the current transaction process? 
				cur_accnt = false;
				accnt_num = -1;
				System.out.println("EJECT CARD");
				break;
				
			default:
				return -1;	//invalid option
		}
		
		return 0;
	}

}
