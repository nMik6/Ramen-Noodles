package main;

import java.util.ArrayList;

import main.racing.Race;

public final class RaceData {
	
	private String command = "none";
	
	private boolean power = false;
	
	private Race currentRace = null;
	
	private Channel[] channels = new Channel[8];
	
	private ArrayList<Race> finishedRaces = new ArrayList<Race>();
	
	private int raceNum = -1;
	
	private Time timeOffset = null;
	
	private boolean offsetPos = false;
	
	private final Logger log = new Logger();
	
	private final String debugFile = "data/debugfile.txt";
	
	private final String racerFile = "data/racerfile.txt";
	
	public void setCommand(String cmd) {
		command = cmd;
	}
	
	public void switchPower() {
		power = !power;
	}
	
	public void setCurrentRace(Race race) {
		currentRace = race;
	}
	
	public void setChannel(int loc, Channel channel) {
		channels[loc] = channel;
	}
	
	public void addFinishedRace(Race race) {
		finishedRaces.add(race);
	}
	
	public void setRaceNum(int num) {
		raceNum = num;
	}
	
	public void setTimeOffset(Time off) {
		timeOffset = off;
	}
	
	public void setOffset(boolean off) {
		offsetPos = off;
	}
	
	public String getDebugFile() {
		return debugFile;
	}
	
	public String getRacerFile() {
		return racerFile;
	}
	
	public String getCommand() {
		return command;
	}

	public boolean isPower() {
		return power;
	}

	public Race getCurrentRace() {
		return currentRace;
	}

	public Channel[] getChannels() {
		return channels;
	}

	public ArrayList<Race> getFinishedRaces() {
		return finishedRaces;
	}

	public int getRaceNum() {
		return raceNum;
	}

	public Time getTimeOffset() {
		return timeOffset;
	}

	public boolean isOffsetPos() {
		return offsetPos;
	}

	public Logger getLog() {
		return log;
	}

}
