package src.main;

import java.time.LocalTime;
import java.util.LinkedList;
import java.util.Queue;

public class Race {
	
	/*
	 * public to test, otherwise create a getter for testing purposes.
	*/
	public Queue<Racer> readyRacers = new LinkedList<Racer>(); 
	
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
