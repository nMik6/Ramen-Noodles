package main.racing;

import main.Time;

public class Racer implements Comparable<Racer>{
	
	private int bib;
	private Time start;
	private Time finish;
	private Time total;
	private boolean dnf;
	
	public Racer(int bib) {
	
		this.bib = bib;
		dnf = false;
	}
	
	/**
	 * Sets the start time of the racer to input Time t.
	 * @param t
	 * @return 1
	 */
	public int start(Time t) {	//Is 1 what we should return if correct?
	
		start = t;
		return 1;
	}

	
	/**
	 * Sets the finish time of the racer to input Time t.
	 * @param t
	 * @return 1
	 */
	public int finish(Time t) {
	
		finish = t;
		total = finish.difference(start);
		return 1;
		
	}
	
	/**
	 * Returns whether the racer is currently running a race.
	 * @return true if racer is racinf, else false
	 */
	public boolean isRacing() {
	
		return this.start != null && this.finish == null && !dnf; //If the race has started, but hasn't finished and wasn't DQ'd, then they must be racing.
	}
	
	//Stephen - added setter for dnf
	/**
	 * Sets the racer as having not finished the race.
	 * @return 1
	 */
	public int setDnf() {
		
		dnf = true;
		return 1;
		
	}
	
	/**
	 * Returns whether the racer has finished or not.
	 * @return true if racer did not finish, else false
	 */
	public boolean getDnf() {
		return dnf;
	}
	
	/**
	 * Returns when the racer started their race.
	 * @return the time at which the racer started the race, null if the racer has not yet started their race.
	 */
	public Time getStart() {
		return this.start;
	}

	/**
	 * Returns when the racer finished their race.
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
	/**
	 * Calculates and returns the total time it took the racer to finish their race.
	 * @return a Time object which is the amount of time it took to finish the race.
	 */
	public Time getTotal() {
	
		if (this.start == null || this.finish == null || getDnf()) {
			System.out.println("Racer did not start or did not finish! Cannot get total time!");
			return null;
		}
		total = new Time();
		
		total = start.difference(finish);
		return this.total;
	}
	
	/**
	 * Sets the name (bib number) of the current racer
	 */
	public void setName(int bibNum) {
		this.bib = bibNum;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Racer) {
			Racer r = (Racer) o;
			return this.bib == r.bib;
		}
		return false;
	}
	
	public String toString(Time t) {
		String ret = "  " +bib;
		if(total != null) ret += "  " + total.printTime() + "  F";
		else if(start != null) ret += "  " +t.difference(start).printTime() + "  R";
		return ret;
	}
	
	public int compareTo(Racer r) {
		if(this.getTotal().isBefore(r.getTotal())) return -1;
		else if(!this.getTotal().isBefore(r.getTotal())) return 1;
		else return 0;
	}
}
