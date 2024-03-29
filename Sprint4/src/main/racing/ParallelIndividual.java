package main.racing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import main.Time;

public class ParallelIndividual implements Race {
	private Queue<Racer> ready1;	
	private Queue<Racer> ready3;
	private Queue<Racer> running1;
	private Queue<Racer> running3;
	private List<Racer> finished;
	private int autoNum;
	private boolean raceEnded;
	
	
	public ParallelIndividual() {
		this.ready1 = new LinkedList<Racer>() ;
		this.running1 = new LinkedList<Racer>();
		this.ready3 = new LinkedList<Racer>();
		this.running3 = new LinkedList<Racer>();
		this.finished = new ArrayList<Racer>();
		autoNum = 0;
	}
	
	/**
	 * 
	 * @return type of current race in string format
	 */
	public String getType() {
		return "parallelindividual";
	}
	
	/**
	 * @return true if race finished / End() called 
	 */
	public boolean isFinished() {
		return raceEnded;
	}
	
	/**
	 * Adds Racer r to the queue of ready racers
	 * @param r
	 * @return true if successfully added, else false
	 */
	public boolean addReady(Racer r) {
		if( r == null || this.containsBib(r.getName())) return false;
		if (ready1.size() > ready3.size()) {
			ready3.add(r);
		}else ready1.add(r);
		return true;
	}

	/**
	 * Returns a queue containing all racers that are ready to begin a race.
	 * @return queue of ready racers
	 */
	public Queue<Racer> getReadyRacers() {
		Queue<Racer> out = new LinkedList<Racer>();
		out.addAll(ready1);
		out.addAll(ready3);
		return out;
	}

	/**
	 * Returns a queue containing all racers that are ready to begin a race from channel 1.
	 * @return queue of ready racers queued for channel 1
	 */
	public Queue<Racer> getReadyRacersCh1() {
		return ready1;
	}

	/**
	 * Returns a queue containing all racers that are ready to begin a race from channel 3.
	 * @return queue of ready racers queued for channel 3
	 */
	public Queue<Racer> getReadyRacersCh3() {
		return ready3;
	}
	
	/**
	 * Returns a queue containing all racers who are currently running a race.
	 * @return queue of current racers
	 */
	public Queue<Racer> getCurrentRacers() {
		Queue<Racer> out = new LinkedList<Racer>();
		out.addAll(running1);
		out.addAll(running3);
		return out;
	}
	
	/**
	 * Returns a queue containing all racers who are currently running a race from channel 1.
	 * @return queue of current racers queued for channel 1
	 */
	public Queue<Racer> getCurrentRacersCh1() {
		return running1;
	}
	
	/**
	 * Returns a queue containing all racers who are currently running a race from channel 3.
	 * @return queue of current racers queued for channel 3
	 */
	public Queue<Racer> getCurrentRacersCh3() {
		return running3;
	}
	
	/**
	 * Returns a list of all racers who have finished a race. Includes DNF racers.
	 * @return list of finished racers
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
	 * Removes a racer from the ready position
	 * @param racer to remove
	 * @return true if racer was removed from ready position, else false
	 */
	public boolean cancel(int bib) {
		Queue<Racer> newReady = new LinkedList<Racer>();
		
		boolean canceled = false;
		
		//check both ready queues
		while(!ready1.isEmpty()) {
			Racer tmp = ready1.poll();
			if(tmp.getName() != bib) {
				newReady.add(tmp);
			} else canceled = true;
		}
		ready1 = newReady;
		
		while(!ready3.isEmpty()) {
			Racer tmp = ready3.poll();
			if(tmp.getName() != bib) {
				newReady.add(tmp);
			} else canceled = true;
		}
		ready3 = newReady;
		
		return canceled;
	}
	
	/**
	 * Assigns the DNF flag to the next racer
	 * 
	 */
	public boolean dnf() {
		Racer dnfRacer = null;
		if(running1.peek() != null && running3.peek() != null) {
			if(running1.peek().getStart().difference(new Time()).isBefore(running3.peek().getStart().difference(new Time()))) {
				dnfRacer = running1.poll();
			}
			else dnfRacer = running3.poll();
		}
		else if(running1.peek() != null) dnfRacer = running1.poll();
		else if(running3.peek() != null) dnfRacer = running1.poll();
		
		if(dnfRacer == null) return false;
		dnfRacer.setDnf();
		finished.add(dnfRacer);
		return true;
	}
	
	@Override
	/**
	 * Assigns the start time to the racer and adds them to the 
	 * current racers.
	 * If paraInd, will use the channel variable to determine which queue to pull the starting racer from.
	 * @param time at which the racer starts
	 * @return true if race successfully started, else false
	 */
	public void start(int channel, Time time) {
		Racer starting;
		/*if(ready1.size() < ready3.size()) {
			starting = ready3.poll();
		} else {
			starting = ready1.poll();
		}/**/
		if(channel == 3) {
			starting = ready3.poll();
		} else {
			starting = ready1.poll();
		}
		
		if (starting == null)
			starting = new Racer(autoNum++);
		
		starting.start(time);
		
		if(channel == 3) {
			running3.add(starting);
		} else {
			running1.add(starting);	
		}		
	}
	
	/**
	 * Assigns the racers finishing time and removes them from the
	 * current racers and adds them to finishers.
	 * @param time that the racer finishes at
	 */
	public boolean finish(int channel, Time time) {
		Racer ending;
		
		if(channel == 4) {
			ending = running3.poll();
		} else {
			ending = running1.poll();
		}
		
		if (ending == null) {
			return false;
		}

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
		while(!running1.isEmpty()) {
			Racer temp = running1.poll();
			temp.setDnf();
			System.out.printf("Racer: %d,\tStart: %s,\tDid not finish! ",
					temp.getName(), temp.getStart().printTime());
			finished.add(temp);
		}
		
		while(!running3.isEmpty()) {
			Racer temp = running3.poll();
			temp.setDnf();
			System.out.printf("Racer: %d,\tStart: %s,\tDid not finish! ",
					temp.getName(), temp.getStart().printTime());
			finished.add(temp);
		}
		
		raceEnded = true;
	}
	
	public boolean containsBib(int bib) {
		Iterator<Racer> it;
		Racer r;
		for(it = ready1.iterator();it.hasNext();) {
			r = it.next();
			if(r.getName() == bib)return true;
		}
		
		for(it = ready3.iterator();it.hasNext();) {
			r = it.next();
			if(r.getName() == bib)return true;
		}
		
		for(it = running1.iterator();it.hasNext();) {
			r = it.next();
			if(r.getName() == bib)return true;
		}
		
		for(it = running3.iterator();it.hasNext();) {
			r = it.next();
			if(r.getName() == bib)return true;
		}
		
		for(it = finished.iterator();it.hasNext();) {
			r = it.next();
			if(r.getName() == bib)return true;
		}
		
		return false;
	}

	@Override
	public String getDisplay() {
		//adds ready portion
		String ret = "Ready Racers: \n";
		Racer first = ready1.peek();
		Racer second = ready3.peek();
		Time t = new Time();			//time issue?
		if(first != null) ret += first.toString(t) + "\n";
		if(second != null) ret += second.toString(t)+ "\n";
		
		//adds current portion
		ret += "\nCurrently Running:\n";
		for (Racer r : running1)	ret += r.toString(t) + "\n";
		for (Racer r : running3)	ret += r.toString(t) + "\n";
		
		ret += "\nLast Finish:\n";
		if(finished.size()!= 0)ret += finished.get(finished.size()-1).toString(t);
		return ret;
	}
}
