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
	
	
	public Race(Queue<Racer> readyRacers) {
		this.ready = readyRacers;
		//this.channels = new boolean[2];			?? There's more than 2 channels
		//this.channels[0] = this.channels[1] = false;
		this.running = new ArrayDeque<Racer>();
		this.finished = new ArrayList<Racer>();
		this.raceFinished = false;	
	}
	
	public void setType(String s) {
		this.type = s;
	}
	public void addReady(Racer r) {
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
	 * @param racer
	 * @return 1 if successful and -1 otherwise
	 */
	public int start(Time time, Racer racer) {
		if (ready.peek().equals(racer))
			ready.poll();
		else return -1;
		running.add(racer);
		return (racer.start(time) == 1) ? 1 : -1;
	}
	
	/**
	 * Assigns the racers finishing time and removes them from the
	 * current racers and adds them to finishers.
	 * @param time that the racer finishes at
	 * @param racer
	 * @return 1 if successful and -1 otherwise
	 */
	public int finish(Time time, Racer racer) {
		if (running.peek().equals(racer)) 
			running.poll();
		else return -1;		
		finished.add(racer);
		return (racer.finish(time) == 1) ? 1 : -1;
	}

}
