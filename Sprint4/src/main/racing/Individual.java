package main.racing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;

import main.Time;

import java.util.LinkedList;
import java.util.List;

public class Individual implements Race {
	private Queue<Racer> ready;
	private Queue<Racer> running;
	private List<Racer> finished;
	private int autoNum;

	
	public Individual() {
		this.ready = new LinkedList<Racer>();
		this.running = new LinkedList<Racer>();
		this.finished = new ArrayList<Racer>();
		autoNum = 0;
	}
	
	/**
	 * Adds Racer r to the queue of ready racers
	 * @param r
	 * @return true if successfully added, else false
	 */
	public boolean addReady(Racer r) {
		if( r == null || this.containsBib(r.getName()) || r.didNotFinish()) return false;
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
		return running;
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
			if(r.didNotFinish())out.add(r);
		}
		return out;
	}
	
	
	/**
	 * Assigns the DNF flag to the next racer to finish
	 * @param racer
	 */
	public void dnf() {
		Racer dnfRacer = running.poll();
		
		if(dnfRacer == null) return; //if no racers in running to dnf just return
		
		dnfRacer.dnf();
		
		finished.add(dnfRacer);
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
	 * Assigns the start time to the racer and adds them to the 
	 * current racers.
	 * If paraInd, will check the size of the queue before picking which queue to pull the starting racer from.
	 * @param time at which the racer starts
	 */
	public void start(int channel, Time time) {
		Racer starting = ready.poll();
		
		if (starting == null)
			starting = new Racer(autoNum++);
		
		starting.start(time);

		running.add(starting);
	}
	
	/**
	 * Assigns the racers finishing time and removes them from the
	 * current racers and adds them to finishers.
	 * @param time that the racer finishes at
	 */
	public boolean finish(int channel, Time time) {
		Racer ending = running.poll();
		
		if (ending == null) return false;
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
		while(!running.isEmpty()) {
			Racer temp = running.poll();
			temp.dnf();
			System.out.printf("Racer: %d,\tStart: %s,\tDid not finish! ",
					temp.getName(), temp.getStart().printTime());
			finished.add(temp);
		}
	}
	
	public boolean containsBib(int bib) {
		Iterator<Racer> it;
		Racer r;
		for(it = ready.iterator();it.hasNext();) {
			r = it.next();
			if(r.getName() == bib)return true;
		}

		for(it = running.iterator();it.hasNext();) {
			r = it.next();
			if(r.getName() == bib)return true;
		}

		for(it = finished.iterator();it.hasNext();) {
			r = it.next();
			if(r.getName() == bib)return true;
		}
		return false;
	}
	
	public void swap( ) {
		Queue<Racer> tmp = new LinkedList<Racer>();
		Racer toBeSwapped = running.poll();
		Racer toBeFirst = running.poll();
		if(toBeFirst != null )tmp.add(toBeFirst);
		if(toBeSwapped != null )tmp.add(toBeSwapped);
		for (Racer r : running)
		{
			tmp.add(r);
		}
		running = tmp;
	}

	@Override
	public String getDisplay() {
		String ret = "";
		//adds ready portion
		Queue<Racer> tmp = new LinkedList<Racer>();
		Racer first = ready.poll();
		Racer second = ready.poll();
		Racer third = ready.poll();
		Time t = new Time();
		if(first != null ){
			tmp.add(first);
			ret += first.toString(t) + "\n";
		}
		if(second != null ){
			tmp.add(second);
			ret += second.toString(t)+ "\n";
		}
		if(third != null ){
			tmp.add(third);
			ret += third.toString(t)+ "\n";
		}
		
		for (Racer r : running)
		{
			tmp.add(r);
		}
		running = tmp;
		ret += "\n";
		
		//adds current portion
		for (Racer r : running)	ret += r.toString(t) + "\n";
		
		if(finished.size()!= 0)ret += finished.get(finished.size()-1).toString(t);
		return ret;
	}

}
