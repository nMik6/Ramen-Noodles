package main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;
import java.util.LinkedList;
import java.util.List;


public class Race {
	
	private Queue<Racer> ready;
	private Queue<Racer> alsoReady;
	private Queue<Racer> running;
	private Queue<Racer> alsoRunning;
	private List<Racer> finished;
	//private Channel[] channels;
	private boolean paraInd;//for use when more than one race type being handled later
	private int autoNum;
	
	
	public Race() {
		this.ready = new LinkedList<Racer>() ;
		//this.channels = new boolean[2];			?? There's more than 2 channels
		//this.channels[0] = this.channels[1] = false;
		this.running = new LinkedList<Racer>();
		this.finished = new ArrayList<Racer>();
		paraInd = false;
		autoNum = 0;
	}
	
	/**
	 * Sets the type of race
	 * @param s the race type
	 */
	public void setType(String s) {
		if (s.equals("PARIND")) {
			this.alsoRunning = new LinkedList<Racer>();
			this.alsoReady = new LinkedList<Racer>();
			this.paraInd = true;
		}
		else paraInd = false;
	}
	
	/**
	 * Returns the race type of Race
	 * @return true is type is parallel individual, false otherwise
	 */
	public boolean getType() {
		return paraInd;
	}
	
	/**
	 * Adds Racer r to the queue of ready racers
	 * @param r
	 * @return true if successfully added, else false
	 */
	public boolean addReady(Racer r) {
		if( r == null || this.containsBib(r.getName())) return false;
		if (paraInd) {
			if (ready.size() > alsoReady.size()) {
				alsoReady.add(r);
			}else ready.add(r);
		}else ready.add(r);
		return true;
	}

	/**
	 * Returns a queue containing all racers that are ready to begin a race.
	 * @return queue of ready racers
	 */
	public Queue<Racer> getReadyRacers() {
		if(paraInd) {
			Queue<Racer> out = new LinkedList<Racer>();
			out.addAll(ready);
			out.addAll(alsoReady);
			return out;
		}else return ready;
	}
	
	/**
	 * Returns a queue containing all racers who are currently running a race.
	 * @return queue of current racers
	 */
	public Queue<Racer> getCurrentRacers() {
		if(paraInd){
			Queue<Racer> out = new LinkedList<Racer>();
			out.addAll(running);
			out.addAll(alsoRunning);
			return out;
		}else {
			return running;
		}
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
	 * Assigns the start time to the racer and adds them to the 
	 * current racers.
	 * If paraInd, will check the size of the queue before picking which queue to pull the starting racer from.
	 * @param time at which the racer starts
	 */
	public void start(int channel, Time time) {
		Racer starting;
		if (paraInd && (ready.size() <= alsoReady.size())) {
			 starting = alsoReady.poll();
			
		}
		else
		{
			 starting = ready.poll();
		}
		
		if (starting == null) 
			starting = new Racer(autoNum++);
		starting.start(time);
		if(paraInd && channel == 3) alsoRunning.add(starting);
		else running.add(starting);
	}
	
	/**
	 * Assigns the racers finishing time and removes them from the
	 * current racers and adds them to finishers.
	 * @param time that the racer finishes at
	 */
	public boolean finish(int channel, Time time) {
		Racer ending;
		if(paraInd && channel == 4) ending = alsoRunning.poll();
		else ending = running.poll();
		
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
		if(paraInd) {
			while(!alsoRunning.isEmpty()) {
				Racer temp = running.poll();
				temp.dnf();
				System.out.printf("Racer: %d,\tStart: %s \tDid not finish!", 
						temp.getName(), temp.getStart().printTime() );
				finished.add(temp);
			}
		}
	}
	
	private boolean containsBib(int bib) {
		Iterator<Racer> it;
		Racer r;
		for(it = ready.iterator();it.hasNext();) {
			r = it.next();
			if(r.getName() == bib)return true;
		}
		if(alsoReady != null)for(it = alsoReady.iterator();it.hasNext();) {
			r = it.next();
			if(r.getName() == bib)return true;
		}
		for(it = running.iterator();it.hasNext();) {
			r = it.next();
			if(r.getName() == bib)return true;
		}
		if(alsoRunning != null)for(it = alsoRunning.iterator();it.hasNext();) {
			r = it.next();
			if(r.getName() == bib)return true;
		}
		for(it = finished.iterator();it.hasNext();) {
			r = it.next();
			if(r.getName() == bib)return true;
		}
		return false;
	}

}
