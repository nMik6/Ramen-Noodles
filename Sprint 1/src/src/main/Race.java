package src.main;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Queue;
import java.util.ArrayDeque;

public class Race {
	
	private String lastCommand;
	private Queue<Racer> readyRacers;
	private Queue<Racer> curRacers;
	private ArrayList<Racer> finishRacers;
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
	
	public int power() {
		return -1;
	}
	
	public int exit() {
		return -1;
	}
	
	public int reset() {
		return -1;
	}
	
	public int time(LocalTime time, Racer racer) {
		return -1;
	}
	
	public int dnf(Racer racer) {
		return -1;
	}
	
	public int cancel(Racer racer) {
		return -1;
	}
	
	public int tog(int channel) {
		return -1;
	}
	
	public int trig(int channel) {
		return -1;
	}
	
	public int newRun() {
		return -1;
	}
	
	public int start(Time time, Racer racer) {
		return (racer.start(time) == 1) ? 1 : -1;
	}
	
	public int finish(Time time, Racer racer) {
		return (racer.finish(time) == 1) ? 1 : -1;
	}

}
