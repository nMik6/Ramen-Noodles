package src.main;

import java.util.ArrayList;
import java.util.Queue;
import java.util.ArrayDeque;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class Race {
	

	private String lastCommand;
	private Queue<Racer> ready;
	private Queue<Racer> running;
	private List<Racer> finished;
	//private Channel[] channels;
	private boolean raceFinished;
	private String type;
	private int autoNum;
	
	
	public Race() {
		this.ready = new LinkedList<Racer>() ;
		//this.channels = new boolean[2];			?? There's more than 2 channels
		//this.channels[0] = this.channels[1] = false;
		this.running = new LinkedList<Racer>();
		this.finished = new ArrayList<Racer>();
		this.raceFinished = false;
		autoNum = 0;
	}
	
	public void setType(String s) {
		this.type = s;
	}
	public void addReady(Racer r) {
		if(ready.contains(r) || running.contains(r)) return;
		//requires a racer.equals() method? TODO
		ready.add(r);
	}


	public Queue<Racer> getReadyRacers() {
		return ready;
	}
	
	public Queue<Racer> getCurrentRacers() {
		return running;
	}
	
	public List<Racer> getFinishedRacers() {
		return finished;
	}
	
	
	public int time(Time time, Racer racer) {
		return -1;
	}
	
	/**
	 * Assigns the DNF flag to the racer
	 * @param racer
	 * @return 1 if the assignment is successful, and -1 otherwise
	 */
	public int dnf(Racer racer) {
		return racer.dnf() == 1 ? 1 : -1;
	}
	
	public int cancel(Racer racer) {
		return -1;
	}
	

	public int newRun() {
		return -1;
	}
	
	/**
	 * Assigns the start time to the racer and adds them to the 
	 * current racers.
	 * @param time at which the racer starts
	 */
	public void start(Time time) {
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
	public void finish(Time time) {
		Racer ending = running.poll();
		if (ending == null) return;
		ending.finish(time);
		finished.add(ending);
	}

}
