package main.events;

import java.time.LocalTime;

import java.time.format.DateTimeFormatter;

import main.Channel;
import main.Logger;
import main.RaceData;
import main.racing.Group;
import main.racing.Individual;
import main.racing.ParallelIndividual;
import main.racing.ParallelGroup;
import main.racing.Race;
import main.racing.Racer;
import main.Server;

import java.time.format.FormatStyle;
import java.util.ArrayList;

import main.Time;

public class Event {
	
	protected RaceData raceData;
	private Server server;
	
	public Event(RaceData data) {
		raceData = data;
		//raceData.setTimeOffset(new Time());	
	}
	
	/**
	 * Powers on and off the system
	 */
	public void power() {
		raceData.switchPower();
		if (raceData.isPower())
			for (int i = 0; i < 8; i++)
				raceData.setChannel(i, new Channel());
		raceData.getLog().msg("The power is " + (raceData.isPower() ? "ON" : "OFF"));
	}
	
	/**
	 * Leaves the system
	 */
	public void exit() {
		raceData.getLog().msg("Exiting...Goodbye!");
		System.exit(0);	
	}
	
	/**
	 * Resets the system
	 */
	public void reset() {
		if (!raceData.isPower()) return;
		raceData.setCurrentRace(null);
		raceData.setOffset(false);
		for (int i = 0; i < 7; i++) {
			raceData.setChannel(i, new Channel());
		}
		raceData.getLog().msg("System has been reset");
	}
	
	/**
	 * Prints the racers information
	 */
	public void print() {
		if (!raceData.isPower() || raceData.getCurrentRace() == null)
			return;
		
		for (Racer r: raceData.getCurrentRace().getFinishedRacers()) {
			if (!r.getDnf()) {
			System.out.printf("Racer: %d,\tStart: %s,\tFinish: %s,\tTotal: %s\n", 
					r.getName(), r.getStart().printTime(), r.getFinish().printTime(), r.getTotal().printTime());
			}
			else {
				System.out.printf("Racer: %d,\tStart: %s, \tDid not finish!", 
						r.getName(), r.getStart().printTime());
			}
		}
	}
	
	/**
	 * Connects a channel to a sensor
	 * @param sensor
	 * @param channel
	 */
	public void conn (String sensor, String channel) {
		if (!raceData.isPower()) return;
		raceData.getChannels()[Integer.parseInt(channel)].conn(sensor);
		raceData.getLog().msg("Sensor connected Type: " + sensor +" Num: " + channel);
	}
	
	/**
	*Disconnects the channel
	*/
	public void disc(String channel) {
		if (!raceData.isPower()) return;
		raceData.getChannels()[Integer.parseInt(channel)].conn("NONE");
		raceData.getLog().msg("Sensor disconnected.");
	}
	
	/**
	 * Assigns the num of a racer, if a group race has finished then assigns their nums
	 * @param str number
	 */
	public void num(String str) {
		if (raceData.getCurrentRace() == null) {
			if (raceData.getFinishedRaces().get(raceData.getFinishedRaces().size()-1) instanceof Group) {//if most recent race instance of group
				int previousRaceSize = raceData.getFinishedRaces().size();
				Group previousRace = (Group) raceData.getFinishedRaces().get(previousRaceSize-1);
				previousRace.setBib(Integer.parseInt(str));
				raceData.getLog().msg("Group racer assigned " + str);
				raceData.getLog().export(previousRace.getFinishedRacers(), raceData.getRaceNum());		
			} else {	
				raceData.getLog().msg("No race to add to. Create a new race event first.");
				return;
			}
		} else { 
			try {
				if (!raceData.getCurrentRace().containsBib(Integer.parseInt(str))) {
					raceData.getCurrentRace().addReady(new Racer(Integer.parseInt(str)));
					raceData.getLog().msg("Racer assigned " + str);
				} else
					raceData.getLog().msg("That bib number already exists");
			} catch (NumberFormatException e) {
				raceData.getLog().msg("No input characters were detected.");
			}
		}
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
			if (temp[channelInt -1].getState()) {
				//bad workaround to fix trigger issues with parallel group
				if(raceData.getCurrentRace() instanceof ParallelGroup) {
					ParallelGroup cur_race = (ParallelGroup) raceData.getCurrentRace();
					if(channelInt == 1 && cur_race.groupStart == null) cur_race.start(channelInt, t);
					else cur_race.finish(channelInt, t);
				}
			else {
				raceData.getLog().msg("Trigger on channel #" + channel);
				Race cur_race = raceData.getCurrentRace();
				if (channelInt % 2 != 0) cur_race.start(channelInt, t);
				else cur_race.finish(channelInt, t);
			}
			}
		}catch(Exception e) {
			raceData.getLog().msg("No Current race! Cannot trigger a start!");
		}
	}
	
	/**
	 * Sets the type of event running
	 * @param type of the event
	 */
	public void event(String type) {
		if (!raceData.isPower()) return;
		Race cur_race;
		if (type.equals("IND")) {
			cur_race = new Individual();
		} else if (type.equals("PARIND")) {
			cur_race = new ParallelIndividual();
		} else if (type.equals("GRP")) {
			cur_race = new Group();
		} else if (type.equals("PARGRP")) {
			cur_race = new ParallelGroup();
		} else {
			raceData.getLog().msg("Event type not found!");
			return;
		}
		raceData.setCurrentRace(cur_race);
		raceData.getLog().msg("Race type set to: " + type);
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
				raceData.getLog().msg("Channel #" + (intchan + 1) + " toggled " + (channels[intchan].getState() ? "ON" : "OFF"));
				return intchan;
			} catch(Exception e) {}
		return -1;
	}
	
	/**
	 * Starts a new race event
	 */
	public void newrun() {
		if (!raceData.isPower()) return;
		if (raceData.getCurrentRace() == null) {
			raceData.getLog().msg("No race to start.");
			return;
		}
		raceData.setRaceNum(raceData.getRaceNum() + 1);
		raceData.getLog().msg("New race created");
		
		this.stopServer();
	}
	
	/**
	 * Ends the current race event and moves it to the list of finished races
	 */
	public void endrun() {
		if (!raceData.isPower()) return;
		Race race = raceData.getCurrentRace();
		if (race == null) {
			raceData.getLog().msg("No race to end.");
			return;
		}
		race.end();
		raceData.getLog().export(race.getFinishedRacers(), raceData.getRaceNum());
		raceData.addFinishedRace(race);
		this.startServer();
		race = null;
		raceData.setCurrentRace(race);
		raceData.getLog().msg("Race ended");
	}
	
	/**
	 * Ends the current race event and moves it to the list of finished races
	 */
	public void export(int num) {
		if (!raceData.isPower()) return;

		if (raceData.getCurrentRace() != null) {
			Race cur_race = raceData.getCurrentRace();
			raceData.getLog().export(cur_race.getFinishedRacers(), num);
			raceData.getLog().msg("Race information exported to /data/RUN"+num+".txt");
		}

		else {
			ArrayList<Race> finished = raceData.getFinishedRaces();
			Race ret = finished.get(raceData.getRaceNum());
			if (ret != null) {
				raceData.getLog().export(ret.getFinishedRacers(), num);
				raceData.getLog().msg("Race information exported to /data/RUN"+num+".txt");
			}
		}
	}
	
	/**
	 * Assign a time
	 * @param t time
	 */
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
			raceData.getLog().msg("Time set to " + passedTime.printTime());
			//the passed time is after the current system time
			raceData.setOffset(sysTime.isBefore(passedTime));
			raceData.setTimeOffset(passedTime.difference(sysTime));
		}
	}
	
	/**
	 * Swaps the first and second racer of an {@link Individual} race
	 */
	public void swap() {
		if (raceData.getCurrentRace() instanceof Individual) {
			Individual cur_race = (Individual) raceData.getCurrentRace();
			cur_race.swap();
			raceData.getLog().msg("Racers swapped");
		} else {
			raceData.getLog().msg("Not an individual race. Cannot swap!");
		}	
	}
	
	/**
	 *Starts the server for the current running race in raceData 
	 */
	private void startServer() {
		if(raceData.getCurrentRace().isFinished()) {
			server = raceData.getServer();
			server.run();
		}
	}
	
	private void stopServer() {
		if(server != null) {
			server.stop();
		}
	}
}
