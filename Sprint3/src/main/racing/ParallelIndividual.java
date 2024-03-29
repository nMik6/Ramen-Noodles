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
	
	
	public ParallelIndividual() {
		this.ready1 = new LinkedList<Racer>() ;
		this.running1 = new LinkedList<Racer>();
		this.ready3 = new LinkedList<Racer>();
		this.running3 = new LinkedList<Racer>();
		this.finished = new ArrayList<Racer>();
		autoNum = 0;
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
			if(r.didNotFinish())out.add(r);
		}
		return out;
	}
	
	/**
	 * Removes a racer from the ready position
	 * @param racer to remove
	 * @return true if racer was removed from ready position, else false
	 */
	public boolean cancel(Racer racer) {
		Queue<Racer> newReady = new LinkedList<Racer>();
		
		boolean canceled = false;
		
		//check both ready queues
		if(ready1.contains(racer)) { 
			while(!ready1.isEmpty()) {
				Racer tmp = ready1.poll();
				if(!tmp.equals(racer)) {
					newReady.add(tmp);
				} else canceled = true;
			}
			ready1 = newReady;
		} else if(ready3.contains(racer)) {
			while(!ready3.isEmpty()) {
				Racer tmp = ready3.poll();
				if(!tmp.equals(racer)) {
					newReady.add(tmp);
				} else canceled = true;
			}
			ready3 = newReady;
		}

		
		return canceled;
	}
	
	/**
	 * Assigns the DNF flag to the racer
	 * 
	 */
	public void dnf(Racer racer) {
		if(running1.contains(racer)) {
			running1.remove(racer);
			racer.dnf();
			finished.add(racer);
		}
		if(running3.contains(racer)) {
			running3.remove(racer);
			racer.dnf();
			finished.add(racer);
		}
		if(ready1.contains(racer)) {
			ready1.remove(racer);
			racer.dnf();
			finished.add(racer);
		}
		if(ready3.contains(racer)) {
			ready3.remove(racer);
			racer.dnf();
			finished.add(racer);
		}
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
			temp.dnf();
			System.out.printf("Racer: %d,\tStart: %s,\tDid not finish! ",
					temp.getName(), temp.getStart().printTime());
			finished.add(temp);
		}
		
		while(!running3.isEmpty()) {
			Racer temp = running3.poll();
			temp.dnf();
			System.out.printf("Racer: %d,\tStart: %s,\tDid not finish! ",
					temp.getName(), temp.getStart().printTime());
			finished.add(temp);
		}
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
		String ret = "";
		//adds ready portion
		Queue<Racer> tmp = new LinkedList<Racer>();
		Racer first = ready1.peek();
		Racer second = ready3.peek();
		Time t = new Time();
		if(first != null) ret += first.toString(t) + "\n";
		if(ready3 != null) ret += second.toString(t)+ "\n";
		ret += "\n";
		
		//adds current portion
		for (Racer r : running1)	ret += r.toString(t) + "\n";
		for (Racer r : running3)	ret += r.toString(t) + "\n";
		
		if(finished.size()!= 0)ret += finished.get(finished.size()-1).toString(t);
		return ret;
	}
}
