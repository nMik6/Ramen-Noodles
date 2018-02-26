package src.main;

import java.time.LocalTime;

import java.util.ArrayList;
import java.util.Queue;
import java.util.ArrayDeque;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class Race {
	

	private String lastCommand;
	private Queue<Racer> readyRacers;
	private Queue<Racer> curRacers;
	private List<Racer> finishRacers;
	private boolean[] channels;
	private boolean raceFinished;
	
	public Race(Queue<Racer> readyRacers) {
		this.readyRacers = readyRacers;
		this.channels = new boolean[2];
		this.channels[1] = this.channels[2] = false;
		this.curRacers = new ArrayDeque<Racer>();
		this.finishRacers = new ArrayList<Racer>();
		this.raceFinished = false;	
	}

	public Queue<Racer> getReadyRacers() {
		return readyRacers;
	}
	
	public Queue<Racer> getCurrentRacers() {
		return curRacers;
	}
	
	public List<Racer> getFinishedRacers() {
		return finishRacers;
	}
	
	public int power() {
		return -1;
	}
	
	public int exit() {
		return -1;
	}
	
	public int reset() {
		return -1;
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
	
	/**
	 * Toggles the state of the channel
	 * @param channel index of the channel to toggle
	 * @return 1 if channel is toggled and -1 if @param is greater than
	 * the number of channels
	 */
	public int tog(int channel) {
		if (channel > 2) return -1;
		channels[channel] = !channels[channel];
		return 1;
	}
	
	public int trig(int channel) { // Unsure of the difference between this and tog is supposed to be
		return -1;
	}
	
	public int newRun() {
		return -1;
	}
	
	/**
	 * Assigns the start time to the racer
	 * @param time at which the racer starts
	 * @param racer
	 * @return 1 if successful and -1 otherwise
	 */
	public int start(Time time, Racer racer) {
		return (racer.start(time) == 1) ? 1 : -1;
	}
	
	/**
	 * Assigns the racers finishing time
	 * @param time that the racer finishes at
	 * @param racer
	 * @return 1 if successful and -1 otherwise
	 */
	public int finish(Time time, Racer racer) {
		return (racer.finish(time) == 1) ? 1 : -1;
	}

}
