package src.main;

public class Channel {
	private boolean state;
	private String sensor;
	private Racer cur;
	
	
	public void setCurRacer(Racer r) {
		cur = r;
	}
	
	public void conn(String s) {
		sensor = s;
	}
	
	public void toggle() {
		state = !state;
	}
	
	public boolean getState() {
		return state;
	}
	
	public void trigger() {
		if(!cur.isRacing()) {
			cur.start();	//create parameterless start 
		}
		else
			cur.finish();	//create parameterless finish
	}
}
