package src.main;
import java.util.Date;
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
	private Bank bank;			//all modifications to current account done through bank
	private int accnt_num = -1;
	private String timestamp;
	private String curButton;
	
	
	/*
	 * Constructor takes an existing Bank object to set bank var. 
	 */
	public ATM(Bank b) {
		bank = b;
		
		this.cashDispenser = new CashDispenser();
	}
	
	public int start(String cmd, String val) {
		timestamp = new SimpleDateFormat("HH.mm.ss").format(System.currentTimeMillis());
		return start(timestamp, cmd, val);
	}
	
	/*
	 * Start executes the commands by calling their respective command methods
	 * The command methods called will return a status integer.
	 * If a command fails start() will return a -1.
	 */
	public int start(String tmstmp, String cmd, String val) {
		timestamp = new SimpleDateFormat("HH.mm.ss").format(tmstmp);
		
		switch (cmd) {
			case "cardread":
				return readCard(val);
				
			case "num":
				return readNum(val);
			
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
	
	/*
	 * Reads in a card value and sets it to current card local variable
	 * @return 0 if account exists for card num else -1
	 */
	private int readCard(String val) {
		int card_num = Integer.parseInt(val);
		
		if(bank.validateAccount(card_num)) {
			accnt_num = card_num;
			curButton = "pin";
			return 0;
		}
		
		return -1;
	}
	
	/*
	 * readNum() method takes in the cmd param toLowerCase()
	 */
	private int readNum(String val) {
		int val_int = Integer.parseInt(val);
		
		if(val_int < 0)
			return -1;
		
		switch (curButton) {
			case "w":
				if(cur_accnt) {
					bank.withdraw(accnt_num, cur_pin, val_int);
					
				} else {
					return -1;
				}
				break;
				
			case "d":
				if(cur_accnt) {
					bank.deposit(accnt_num, cur_pin, val_int);
					if(cashDispenser.dispense(val_int))		//if the cashDispenser has the money for the withdrawl, prints success.
					{
						System.out.print("Successful withdrawl!");
					}
					else
					{
						System.out.print("Out of cash! Sorry!");
					}
				} else {
					return -1;
				}
				break;
				
			case "pin":
				if(bank.validate(accnt_num, val_int) != null) {
					cur_accnt = true;
					cur_pin = val_int;					
				}
				break;
				
			case "cb":
				//call the printer to print out to console formatted balance 
				break;
				
			default:
				return -1;
		}
		 
		curButton = ""; //reset the current Button to empty string
		
		return 0;
	}
	
	private int button(String val) {
		
		if(!cur_accnt)
			return -1;
		
		switch (val) {
			case "w":
				curButton = "w";
				break;
				
			case "d":
				curButton = "d";
				break;
				
			case "cb":
				curButton = "cb";
				break;
				
			case "cancel":	//does "Button cancel" just return to the command prompt and nullify the current transaction process? 
				cur_accnt = false;
				accnt_num = -1;
				break;
				
			default:
				return -1;	//invalid option
		}
		
		return 0;
	}

}
