	package main.racing;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import main.Time;

import java.util.Collection;
import java.util.HashMap;

public class Group implements Race{
	private Time groupStart;
	private Queue<Racer> ready;
	private HashMap<Integer, Racer> running; //Map bib number to racer object
	private List<Racer> finished;
	private int autoNum;
	
	public Group() {
		this.ready = new LinkedList<Racer>();
		this.running = new HashMap<Integer,Racer>();
		this.finished = new LinkedList<Racer>();
		autoNum = 0;
	}
	
	/**
	 * Adds Racer r to the queue of ready racers
	 * @param r
	 * @return true if successfully added, else false
	 */
	public boolean addReady(Racer r) {
		if( r == null || this.containsBib(r.getName())) return false;
		r.start(groupStart); //all references to 
		ready.add(r);
		return true;
	}
	

	/**
	 * Returns a queue containing all racers that are ready to begin a race.
	 * @return queue of ready racers
	 */
	public Queue<Racer> getReadyRacers() {
		return ready;
	}
	
	
	/**
	 * Returns a queue containing all racers who are currently running a race.
	 * @return queue of current racers
	 */
	public Queue<Racer> getCurrentRacers() {
		Queue<Racer> runningQueue = new LinkedList<Racer>();
		Collection<Racer> run_set = running.values();
		
		for(Racer r: run_set) {
			runningQueue.add(r);
		}
		
		return runningQueue;
	}
	
	/**
	 * Returns a list of all racers who have finished a race.
	 * @return list of finished racer
	 */
	public List<Racer> getFinishedRacers() {
		return finished;
	}
	
	
	/**
	 * Assigns the DNF flag to the racer
	 * @param racer
	 * @return 1 if the assignment is successful, and -1 otherwise
	 */
	public int dnf(Racer racer) {
		return racer.dnf() == 1 ? 1 : -1;
	}
	
	/**
	 * Removes a racer from the ready position
	 * @param racer to remove
	 * @return true if racer was removed from ready position, else false
	 */
	public boolean cancel(Racer racer) {
		Queue<Racer> newReady = new LinkedList<Racer>();
		//newReady.add(racer);
		boolean canceled = false;
		while(!ready.isEmpty()) {
			Racer tmp = ready.poll();
			if(!tmp.equals(racer)) {
				newReady.add(tmp);
			}else canceled = true;
		}
		ready = newReady;
		return canceled;
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
		
		for(Racer r: ready) {
			running.put(r.getName(), r);
		}
	}
	

	/**
	 * Assigns the racers finishing time and removes them from the
	 * current racers and adds them to finishers.
	 * @param time that the racer finishes at
	 */
	public boolean finish(int bib, Time time) {
		Racer ending = running.get(bib);
		
		if (ending == null) return false;
		ending.start(groupStart);
		ending.finish(time);
		finished.add(ending);
		System.out.printf("Racer: %d,\tStart: %s,\tFinish: %s,\tTotal: %s\n", 
				ending.getName(), ending.getStart().printTime(), ending.getFinish().printTime(), ending.getTotal().printTime());
		
		return true;
	}
	
	/**
	 * removes all running racers, marking them dnf and moving them to the finished list.
	 * @param time that the racer finishes at
	 */
	public void end() {
		Collection<Racer> run_set = running.values();
		
		for(Racer r: run_set) {
			r.dnf();
			System.out.printf("Racer: %d,\tStart: %s,\tDid not finish! ",
					r.getName(), r.getStart().printTime());
			finished.add(r);
		}
	}
	
	public boolean containsBib(int bib) {
		Iterator<Racer> it;
		Racer r;
		for(it = ready.iterator();it.hasNext();) {
			r = it.next();
			if(r.getName() == bib)return true;
		}
		
		Collection<Racer> run_set = running.values();
		 
		for(it = run_set.iterator();it.hasNext();) {
			r = it.next();
			if(r.getName() == bib)return true;
		}

		for(it = finished.iterator();it.hasNext();) {
			r = it.next();
			if(r.getName() == bib)return true;
		}
		return false;
	}
	
	//Using HashMap, MUST HAVE EQUALS AND HASHCODE METHOD
	
}
