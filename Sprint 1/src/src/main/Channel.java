package src.main;

public class Channel {
	private boolean state;
	private String sensor;
	
	
	public void conn(String s) {
		sensor = s;
	}
	
	public void toggle() {
		state = !state;
	}
	
	public boolean getState() {
		return state;
	}
	
}
