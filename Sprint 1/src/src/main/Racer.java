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
	/**
	 * Sets the racer as having not finished the race.
	 * @return 1
	 */
	public int dnf() {
		
		dnf = true;
		return 1;
		
	}
	
	/**
	 * Returns whether the racer has finished or not.
	 * @return true if racer did not finish, else false
	 */
	public boolean didNotFinish() {
		return dnf;
	}
	
	/**
	 * Retruns when the racer started their race.
	 * @return the time at which the racer started the race, null if the racer has not yet started their race.
	 */
	public Time getStart() {
		return this.start;
	}

	/**
	 * Retruns when the racer finished their race.
	 * @return the time at which the racer finished the race, null if the racer has not yet finished their race.
	 */
	public Time getFinish() {
		return this.finish;
	}
	
	/**
	 * Returns the bib number used to identify the racer.
	 * @return bib number
	 */
	public int getName() {
		return this.bib;
	}
	
	public Time getTotal() {
	//getTotal calculates the difference between start and finish, then returns it as a time.
		if (this.start == null || this.finish == null || didNotFinish()) {
			System.out.println("Racer did not start or did not finish! Cannot get total time!");
			return null;
		}
		total = new Time();
		
		total = start.difference(finish);
		return this.total;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Racer) {
			Racer r = (Racer) o;
			return this.bib == r.bib;
		}
		return false;
	}

}
