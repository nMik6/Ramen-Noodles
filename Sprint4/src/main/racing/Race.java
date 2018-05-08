package main.racing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;
import java.util.Collection;
import main.Time;

import java.util.LinkedList;
import java.util.List;


public interface Race {
	
	/**
	 * 
	 * @return type of current race in string format
	 */
	public String getType();
	
	/**
	 * @return true if current race is finished
	 */
	public boolean isFinished();

	/**
	 * Adds Racer r to the queue of ready racers
	 * @param r
	 * @return true if successfully added, else false
	 */
	public boolean addReady(Racer r);
	

	/**
	 * Returns a queue containing all racers that are ready to begin a race.
	 * @return queue of ready racers
	 */
	public Collection<Racer> getReadyRacers();
	
	
	/**
	 * Returns a queue containing all racers who are currently running a race.
	 * @return queue of current racers
	 */
	public Collection<Racer> getCurrentRacers();
	
	/**
	 * Returns a list of all racers who have finished a race.
	 * @return list of finished racer
	 */
	public List<Racer> getFinishedRacers();

	
	/**
	 * Returns a list of all racers who did not finish the race.
	 * @return list of dnf racers
	 */
	public List<Racer> getDNFRacers();
	
	
	/**
	 * Removes a racer from the ready position
	 * @param racer to remove
	 * @return true if racer was removed from ready position, else false
	 */
	public boolean cancel(int bib);
	
	/**
	 * Marks the next racer to finish as dnf
	 * @return true if dnf assignment successful
	 */
	public boolean dnf();
	
	/**
	 * Assigns the start time to the racer and adds them to the 
	 * current racers.
	 * If paraInd, will check the size of the queue before picking which queue to pull the starting racer from.
	 * @param time at which the racer starts
	 */
	public void start(int channel, Time time);
	
	/**
	 * Assigns the racers finishing time and removes them from the
	 * current racers and adds them to finishers.
	 * @param time that the racer finishes at
	 */
	public boolean finish(int channel, Time time);
	
	/**
	 * removes all running racers, marking them dnf and moving them to the finished list.
	 * @param time that the racer finishes at
	 */
	public void end();
	
	/**
	 * returns whether the bib is in use during num entry
	 **/
	public boolean containsBib(int bib);
	
	/**
	 * returns the displayable ready, current, finished racers(not the full arrays, see end of S3 PDF)
	 **/
	public String getDisplay();


}
