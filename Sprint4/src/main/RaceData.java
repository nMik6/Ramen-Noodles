package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import main.racing.Race;
import main.racing.Racer;

public final class RaceData {
	
	/**
	 * The systems power state
	 */
	private boolean power = false;
	
	/**
	 * The current type of race
	 */
	private Race currentRace = null;
	
	/**
	 * The channels used in the race
	 */
	private Channel[] channels = new Channel[8];
	
	/**
	 * All the {@link Race} that have finished
	 */
	private ArrayList<Race> finishedRaces = new ArrayList<Race>();
	
	/**
	 * The race number that is currently happening
	 */
	private int raceNum = -1;
	
	/**
	 * The set time offset for races
	 */
	private Time timeOffset = null;
	
	/**
	 * The time offset state
	 */
	private boolean offsetPos = false;
	
	/**
	 * A new {@link Logger} for exporting and printing information
	 */
	private final Logger log = new Logger();
	
	/**
	 * The location in which the file will be placed
	 */
	private final String debugFile = "data/debugfile.txt";
	
	/**
	 * The location in which the file will be placed
	 */
	private final String racerFile = "data/racerfile.txt";
	
	
	/**
	 * localhost web server for displaying finished race data
	 */
	private Server server;
	
	/**
	 * Switches the power to the opposite of what its current state is
	 */
	public void switchPower() {
		power = !power;
	}
	
	/**
	 * Sets a new current {@link Race}
	 * @param race 
	 */
	public void setCurrentRace(Race race) {
		currentRace = race;
	}
	
	/**
	 * Sets the new channel at a certain position
	 * @param loc position in channel array
	 * @param channel new channel to set
	 */
	public void setChannel(int loc, Channel channel) {
		channels[loc] = channel;
	}
	
	/**
	 * Add a new {@link Race} to the finished race list
	 * @param race
	 */
	public void addFinishedRace(Race race) {
		finishedRaces.add(race);
	}

	/**
	 * Sets the number of the current race
	 * @param num
	 */
	public void setRaceNum(int num) {
		raceNum = num;
	}

	/**
	 * Sets the times offset for recording times in races
	 * @param off
	 */
	public void setTimeOffset(Time off) {
		timeOffset = off;
	}

	/**
	 * Sets the state of the offset
	 * @param off
	 */
	public void setOffset(boolean off) {
		offsetPos = off;
	}

	/**
	 * Returns the file location for debugging
	 * @return debugFile
	 */
	public String getDebugFile() {
		return debugFile;
	}

	/**
	 * Returns the file location for racer files
	 * @return the raceFile
	 */
	public String getRacerFile() {
		return racerFile;
	}

	/**
	 * Returns the state of the power
	 * @return {@code true} if system is on and {@code false} if off
	 */
	public boolean isPower() {
		return power;
	}

	/**
	 * Returns back the current race happening
	 * @return current Race
	 */
	public Race getCurrentRace() {
		return currentRace;
	}

	/**
	 * Returns the array of channels
	 * @return channels
	 */
	public Channel[] getChannels() {
		return channels;
	}

	/**
	 * Returns the list of finished races
	 * @return finishedRaces
	 */
	public ArrayList<Race> getFinishedRaces() {
		return finishedRaces;
	}

	/**
	 * Returns the current race number
	 * @return raceNum
	 */
	public int getRaceNum() {
		return raceNum;
	}

	/**
	 * Returns the {@link Time} offset
	 * @return the offset time
	 */
	public Time getTimeOffset() {
		return timeOffset;
	}

	/**
	 * 
	 * @return the offset state {@code true} if using offset {@code false} if not
	 */
	public boolean isOffsetPos() {
		return offsetPos;
	}

	/**
	 * Returns the {@link Logger}
	 * @return the logger
	 */
	public Logger getLog() {
		return log;
	}
	
	/**
	 * 
	 * @return the server running for end results after race complete
	 */
	public Server getServer() {
		List<Racer> finishedRacers = this.getCurrentRace().getFinishedRacers();
		Collections.sort(finishedRacers, new SortByTotal());
		server = new Server((List<Racer>) finishedRacers, this.getCurrentRace().getType());
		return server;
	}
	
	private class SortByTotal implements Comparator<Racer>{
		public int compare(Racer a, Racer b) {
			return a.getTotal().compareTo(b.getTotal());
		}
	}

}
