package main.events;

import java.time.LocalTime;

import java.time.format.DateTimeFormatter;

import main.Channel;
import main.Logger;
import main.RaceData;
import main.racing.Group;
import main.racing.Individual;
import main.racing.ParallelIndividual;
import main.racing.Race;
import main.racing.Racer;

import java.time.format.FormatStyle;
import java.util.ArrayList;

import main.Time;

public class Event {
	
	private Time time;
	private DateTimeFormatter format;
	private FormatStyle SHORT;
	Logger log;
	Time timeOffset;
	boolean offsetPos;
	private RaceData raceData;
	
	public Event(RaceData data) {
		time = new Time();
		log = new Logger();
		timeOffset = new Time();
		offsetPos = false;
		raceData = data;
		
	}
	
	
	public void power() {
		raceData.switchPower();
		if (raceData.isPower())
			for (int i = 0; i < 7; i++) {
				raceData.setChannel(i, new Channel());
			}
	}
	
	public void exit() {
		System.out.println("Exiting...Goodbye!");
		System.exit(0);
		
	}
	
	public void reset() {
		if (!raceData.isPower()) return;
		raceData.switchPower();
		raceData.setCurrentRace(null);
		raceData.setCommand("none");
		raceData.setOffset(false);
		for (int i = 0; i < 7; i++) {
			raceData.setChannel(i, new Channel());
		}
	}
	
	public void print() {
		if (!raceData.isPower() || raceData.getCurrentRace() == null)
			return;
		
		for (Racer r: raceData.getCurrentRace().getFinishedRacers()) {
			if (!r.didNotFinish()) {
			System.out.printf("Racer: %d,\tStart: %s,\tFinish: %s,\tTotal: %s\n", 
					r.getName(), r.getStart().printTime(), r.getFinish().printTime(), r.getTotal().printTime());
			}
			else {
				System.out.printf("Racer: %d,\tStart: %s, \tDid not finish!", 
						r.getName(), r.getStart().printTime());
			}
		}
	}
	
	public void conn (String one, String two) {
		if (!raceData.isPower()) return;
		
	}
	
	public void num(String str) {
		if (!raceData.getCurrentRace().containsBib(Integer.parseInt(str)))
			raceData.getCurrentRace().addReady(new Racer(Integer.parseInt(str)));
	}
	
	/**
	 * Verifies a channels state and triggers is
	 * @param channel the channel to check
	 * @param t the time
	 */
	public void trig(String channel, Time t) {
		if (!raceData.isPower()) return; 	
		try {
			int channelInt = Integer.parseInt(channel);
			Channel[] temp = raceData.getChannels();

			if (temp[channelInt].getState()) {
				System.out.println("(log) Trigger on channel #" + channel);
				Race cur_race = raceData.getCurrentRace();
				if (channelInt % 2 != 0) cur_race.start(channelInt, t);
				else cur_race.finish(channelInt, t);
			}
		}catch(Exception e) {
			System.out.println("No Current race! Cannot trigger a start!");
		}
	}
	
	/**
	 * Sets the type of event running
	 * @param type of the event
	 */
	public void event(String type) {
		if (!raceData.isPower()) return;
		Race cur_race;
		if (type.equals("IND"))
		{
			cur_race = new Individual();
		}
		else if (type.equals("PARA")) 
		{
			cur_race = new ParallelIndividual();
		}
		else if (type.equals("GRP"))
		{
			cur_race = new Group();
		}
		else
		{
			System.out.println("Event type not found!");
			return;
		}
		raceData.setCurrentRace(cur_race);
		System.out.println("(log) Race type set to: " + type);
	}
	
	/**
	 * Toggle the state of the channel at string converted to integer index of channels[]
	 * @param channel
	 * @return the channel triggered, -1 if power is off
	 */
	public int tog(String channel) {
		if (raceData.isPower()) 	
			try {
				int intchan = Integer.parseInt(channel);
				Channel[] channels = raceData.getChannels();
				channels[intchan].toggle();
				System.out.println("(log) Channel #" + channel + " toggled");
				return intchan;
			} catch(Exception e) {}
		return -1;
	}
	
	/**
	 * Starts a new race event
	 */
	public void newrun() {
		if (!raceData.isPower())	return;
		if (raceData.getCurrentRace() == null) return;
		raceData.setRaceNum(raceData.getRaceNum() + 1);
		System.out.println("(log) New race created");
	}
	
	/**
	 * Ends the current race event and moves it to the list of finished races
	 */
	public void endrun() {
		if (!raceData.isPower()) return;
		raceData.getCurrentRace().end();
		log.export(raceData.getCurrentRace().getFinishedRacers(), raceData.getRaceNum());
		raceData.addFinishedRace(raceData.getCurrentRace());
		raceData.setCurrentRace(new Individual());
		System.out.println("(log) Race ended");
	}
	/**
	 * Ends the current race event and moves it to the list of finished races
	 */
	public void export(int num) {
		if (!raceData.isPower()) return;

		if (raceData.getCurrentRace() != null) {
			Race cur_race = raceData.getCurrentRace();
			log.export(cur_race.getFinishedRacers(), num);
			System.out.println("(log) Export Occurred");
		}

		else {
			ArrayList<Race> finished = raceData.getFinishedRaces();
			Race ret = finished.get(raceData.getRaceNum());
			if (ret != null) {
				log.export(ret.getFinishedRacers(), num);
				System.out.println("(log) Export Occurred");
			}
		}
	}
	
	public void time(String t) {
		Time sysTime = new Time();
		Time passedTime;
		LocalTime toCheck;

		final DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss.S");

		try { toCheck = LocalTime.parse(t, format); }
		catch (Exception e) { toCheck = null; } 
		//For now, if an invalid time is passed it defaults to the system time. will change later

		if (toCheck!= null) {
			passedTime = new Time(toCheck);
			System.out.println("(log) Time set to " + passedTime.printTime());
			//the passed time is after the current system time
			offsetPos = sysTime.isBefore(passedTime);
			timeOffset = passedTime.difference(sysTime);
		}
	}

}
