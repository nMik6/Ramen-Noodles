package src.main;

public class Racer {
	
	private int bib;
	private Time start;
	private Time finish;
	private Time total;
	private boolean dnf;
	
	public Racer(int bib) {
	
		this.bib = bib;
		dnf = false;
	}
	
	public int start(Time t) {	//Is 1 what we should return if correct?
	
		start = t;
		return 1;
	}
	
	public int finish(Time t) {
	
		finish = t;
		return 1;
		
	}
	
	public boolean isRacing() {
	
		if (this.start != null && this.finish == null && !dnf) //If the race has started, but hasn't finished and wasn't DQ'd, then they must be racing.
		{
			return true;
		}
		return false;
	}
	
	public Time getTotal() {
	//This will call Time.diff when it is available. 
		return this.total;
	}

}
