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
	
	//Stephen - added setter for dnf
	public int dnf() {
		
		dnf = true;
		return 1;
		
	}
	
	public boolean didNotFinish() {
		return dnf;
	}
	
	public Time getStart() {
		return this.start;
	}
	
	public Time getFinish() {
		return this.finish;
	}
	
	public int getName() {
		return this.bib;
	}
	
	public Time getTotal() {
	//getTotal calculates the difference between start and finish, then returns it as a time.
		if (this.start == null || didNotFinish()) {
			System.out.println("Racer did not start or did not finish! Cannot get total time!");
			return null;
		}
		total = new Time();
		total = total.difference(start,  finish);
		return this.total;
	}

}
