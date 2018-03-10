package src.main;

public class Channel {
	private boolean state;
	private String sensor;
	
	public Channel() {
		state = false;
	}
	
	public void conn(String s) {
		sensor = s;
	}
	
	/**
	 * Toggles the state of the channel.
	 * If channel is open, closes channel.
	 * If channel is closed, opens channel.
	 */
	public void toggle() {
		state = !state;
	}
	
	/**
	 * Returns the state of the channel (whether it is open or not)
	 * @return true if open, false if closed
	 */
	public boolean getState() {
		return state;
	}
	
	public String getSensor() {
		return sensor; 
	}
	
}
