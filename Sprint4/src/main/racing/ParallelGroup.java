package main.racing;

import java.util.List;
import java.util.Queue;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;


import main.Time;


/**
 * 
 * @author Hart Sinterhauf
 * 
 * Parallel Group is used for races that have a common start but multiple finishes
 * with different finish lanes (such as swimming).  
 * The finish will occur on channels 1-8 inclusive (note that channel 1 serves two purposes). 
 * Up to 8 competitor numbers may be entered.  The first number is assigned to lane (channel) 1, 
 * the second to lane (channel) 2, etc. Any numbers other than the first 8 are ignored.  
 * Upon a start (trigger on channel 1), the times for all racers begin.  Up to 8 pads may be 
 * connected to the finish channels.  If there are not enough sensors connected for the racers, 
 * some times will not be recorded (a DNF)unless a manual trigger is used.  If there are fewer 
 * racer numbers entered than the number of channels connected, only the times for the racers 
 * entered will be recorded (if there is a trigger on a channel with no racer, it is ignored)
 */

public class ParallelGroup implements Race{
	private Time groupStart;
	private List<Racer> finished;
	private int place;
	private boolean raceEnded;
	private ArrayList<Racer> ready;
	private ArrayList<Racer> running;
	
	public ParallelGroup() {
		this.place = 0;
		raceEnded = false;
		this.ready = new ArrayList<Racer>();
		this.running = new ArrayList<Racer>();
		this.finished = new LinkedList<Racer>();
	}
	
	/**
	 * 
	 * @return type of current race in string format
	 */
	public String getType() {
		return "parallelgroup";
	}
	
	/**
	 * @return true if race is finished (end() is called)
	 */
	public boolean isFinished() {
		return raceEnded;
	}
	/**
	 * Adds Racer r to the queue of ready racers. Only eight racers are recorded. 
	 * @param r
	 * @return true if successfully added, else false
	 */
	public boolean addReady(Racer r) {
		if(ready.size() >= 8)
			return false;
		ready.add(r);
		return true;
	}
		

	/**
	 * Returns a queue containing all racers that are ready to begin a race.
	 * @return queue of ready racers
	 */
	public ArrayList<Racer> getReadyRacers(){
		return ready;
	}
	
	
	/**
	 * Returns a queue containing all racers who are currently running a race.
	 * @return queue of current racers
	 */
	public ArrayList<Racer> getCurrentRacers(){
		return running;
	}
	
	/**
	 * Returns a list of all racers who have finished a race.
	 * @return list of finished racer
	 */
	public List<Racer> getFinishedRacers(){
		return finished;
	}

	
	/**
	 * Returns a list of all racers who did not finish the race.
	 * @return list of dnf racers
	 */
	public List<Racer> getDNFRacers(){
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
	public boolean cancel(Racer racer) {
		return ready.remove(racer);
	}
	
	
	/**
	 * Overloaded Start with default channel set to 1
	 * @param Time
	 */
	public void start(Time time) {
		this.start(1, time);
	}
	
	/**
	 * Assigns the start time to the race
	 * @param time at which the racer starts
	 */
	public void start(int channel, Time time) {
		groupStart = time;
		running = ready;
		ready = new ArrayList<Racer>();
	}
	
	/**
	 * Assigns the racers finishing time and adds them to finishers.
	 * NOTE: does not remove from current racers as the racer order must be preserved for channel specification.
	 * Constraints for the time and channel limits should be handled by a higher layer class
	 * @param time that the racer finishes at
	 */
	public boolean finish(int channel, Time time) {
		Racer temp = running.get(channel-1);
		if(temp == null || temp.getDnf() || temp.getFinish() != null) {
			return false;
		}
		temp.start(groupStart);
		temp.finish(time); 
		return finished.add(temp); //should return true
	}
	
	/**
	 * removes all running racers, marking them dnf and moving them to the finished list.
	 * Sends results in tabular format to local host http server
	 * @param time that the racer finishes at
	 */
	public void end() {
		for(Racer r: running) {
			if(r.isRacing()) {
				r.setDnf();
				finished.add(r);
			}
		}
		raceEnded = true;
	}
	
	public boolean containsBib(int bib) {
		for(Racer r: ready) {
			if(r.getName() == bib)
				return true;
		}
		
		for(Racer r: running) {
			if(r.getName() == bib) return true;
		}
		
		return false;
	}
	
	 
	
	/**
	 * returns the displayable ready, current, finished racers(not the full arrays, see end of S3 PDF)
	 **/
	public String getDisplay() {
		String ret = "Current Race Time:\n";
		//some calculation of time
		ret += "\nLast Finish:\n";
		if(finished.size() != 0) 
			ret += finished.get(finished.size()-1).toString(new Time());
		return ret;
	}


}
