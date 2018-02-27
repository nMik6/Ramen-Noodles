package src.main;

public class Channel {
	boolean state;
	String sensor;
	
	public void toggle() {
		state = !state;
	}
	
	public boolean getState() {
		return state;
	}
}
