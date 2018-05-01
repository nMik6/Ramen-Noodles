	package main.racing;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import main.Time;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


/**
* NOTE: can't assign a bib number to the currently finishing racer
* unless the racers that have finished already have bib numbers
* "NUM" command can assign bib numbers to the already finished racers
*/
public class Group implements Race{
	private Time groupStart;
	private List<Racer> finished;
	private int autoNum;
	private int tmpBib;
	private boolean raceEnded;
	
	
	public Group() {
		this.finished = new LinkedList<Racer>();
		autoNum = 0;
		raceEnded = false;
		tmpBib = 0;
	}
	
	/**
	 * Does not add Racer r to the queue of ready racers because all racers have same start time 
	 * Racers are determined after they are finished by assigning them bib numbers through setBib() method
	 * @param r
	 * @return false
	 */
	public boolean addReady(Racer r) {
		return false;
	}
	
	/**
	 * No queue of ready racers used in Group race. Method vestige of Race interface.
	 * @return null
	 */
	public Queue<Racer> getReadyRacers() {
		return null;
	}
	
	
	/**
	 * No running queue is used in Group as the racers are created dynamically when finish() is called
	 * @return null
	 */
	public Queue<Racer> getCurrentRacers() {
		return null;
	}
	
	/**
	 * Returns a list of all racers who have finished a race.
	 * @return list of finished racer
	 */
	public List<Racer> getFinishedRacers() {
		return finished;
	}
	
	/**
	 * Returns a list of all racers who did not finish the race.
	 * @return list of dnf racers
	 */
	public List<Racer> getDNFRacers() {
		List<Racer> out = new ArrayList<Racer>();
		for(Iterator<Racer> it = finished.iterator();it.hasNext();) {
			Racer r = it.next();
			if(r.getDnf())out.add(r);
		}
		return out;
	}
	
	/**
	 * Assigns the DNF flag to the next racer to finish
	 * @param racer
	 */
	public void dnf() {
		Racer dnfRacer = new Racer(-1);
		dnfRacer.setDnf();
		finished.add(dnfRacer);
	}
	
	/**
	 * Removes a racer from the ready position
	 * @param racer to remove
	 * @return true if racer was removed from ready position, else false
	 */
	public boolean cancel(Racer racer) {
		return false;
	}
	
	/**
	 * Over loaded start method calls two parameter start method with channel 1 as default
	 * All Racers start at the same time for Group races
	 * This time will be added to each racer as they finish 
	 */
	public void start(Time time) {
		this.start(1, time);
	}
	
	/**
	 * Adds all racers in ready to running
	 * @param time at which the race starts
	 */
	public void start(int channel, Time time) {
		groupStart = time;
	}
	

	/**
	 * Assigns the racers finishing time and removes them from the
	 * current racers and adds them to finishers.
	 * @param time that the racer finishes at
	 */
	public boolean finish(int channel, Time time) {
		if(raceEnded) 
			return false;
		
		Racer ending = new Racer(autoNum+1);	//default set anonymous racer bib number to -1
		++autoNum;
		ending.start(groupStart);
		ending.finish(time);
		finished.add(ending);
		System.out.printf("Racer: %d,\tStart: %s,\tFinish: %s,\tTotal: %s\n", 
				ending.getName(), ending.getStart().printTime(), ending.getFinish().printTime(), ending.getTotal().printTime());
		
		return true;
	}
	
	/**
	 * End race by setting boolean flag 
	 */
	public void end() {
		raceEnded = true;
	}
	
	/**
	 * Called by Events for the 'NUM' command to change the bib numbers of the finished racers 
	 * @param bib
	 */
	public void setBib(int bib){
		if(finished.isEmpty()) 
			return;
		if (finished.get(tmpBib) != null)
		{
			finished.get(tmpBib).setName(bib);
			++tmpBib;
		}
		
	}
	
	/**
	 * Return true if finished contains racer with same bib (racer already finished)
	 * @param bib 
	 */
	public boolean containsBib(int bib) {
		Iterator<Racer> it;
		Racer r;

		for(it = finished.iterator();it.hasNext();) {
			r = it.next();
			if(r.getName() == bib)return true;
		}
		return false;
	}
	
	public boolean getFinish() {
		return this.raceEnded;
	}
	
	public void setFinished(boolean raceEnded) {
		this.raceEnded = raceEnded;
	}
	
	@Override
	public String getDisplay() {
		
		String ret = "";
		
		if(finished.size()!= 0)ret += finished.get(finished.size()-1).toString(new Time());
		return ret;
	}
	
}
