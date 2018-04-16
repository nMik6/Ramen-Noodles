package main;

import java.util.ArrayList;

import main.racing.Race;

public class RaceData {
	
	private static String command = "none";
	
	private static boolean power = false;
	
	private static Race currentRace = null;
	
	private static Channel[] channels = new Channel[8];
	
	private static ArrayList<Race> finishedRaces = new ArrayList<Race>();
	
	private static int raceNum = -1;
	
	private static Time timeOffset = null;
	
	private static boolean offsetPos = false;
	
	private static Logger log = new Logger();
	
	private static String debugFile = "data/debugfile.txt";
	
	private static String racerFile = "data/racerfile.txt";
	
	public static void setCommand(String cmd) {
		command = cmd;
	}
	
	public static void switchPower() {
		power = !power;
	}
	
	public static void setCurrentRace(Race race) {
		currentRace = race;
	}
	
	public static void setChannel(int loc, Channel channel) {
		channels[loc] = channel;
	}
	
	public static void addFinishedRace(Race race) {
		finishedRaces.add(race);
	}
	
	public static void setRaceNum(int num) {
		raceNum = num;
	}
	
	public static void setTimeOffset(Time off) {
		timeOffset = off;
	}
	
	public static void setOffset(boolean off) {
		offsetPos = off;
	}
	
	public static String getDebugFile() {
		return debugFile;
	}
	
	public static String getRacerFile() {
		return racerFile;
	}
	
	public static String getCommand() {
		return command;
	}

	public static boolean isPower() {
		return power;
	}

	public static Race getCurrentRace() {
		return currentRace;
	}

	public static Channel[] getChannels() {
		return channels;
	}

	public static ArrayList<Race> getFinishedRaces() {
		return finishedRaces;
	}

	public static int getRaceNum() {
		return raceNum;
	}

	public static Time getTimeOffset() {
		return timeOffset;
	}

	public static boolean isOffsetPos() {
		return offsetPos;
	}

	public static Logger getLog() {
		return log;
	}

}
