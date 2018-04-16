package main.events;

import java.time.format.DateTimeFormatter;

import main.Channel;
import main.RaceData;
import main.racing.Racer;

import java.time.format.FormatStyle;

import main.Time;

public class Event {
	
	private Time time;
	private DateTimeFormatter format;
	private FormatStyle SHORT;
	
	
	public void power() {
		RaceData.switchPower();
		if (RaceData.isPower())
			for (int i = 0; i < 7; i++) {
				RaceData.setChannel(i, new Channel());
			}
	}
	
	public void exit() {
		
	}
	
	public void reset() {
		if (!RaceData.isPower()) return;
		RaceData.switchPower();
		RaceData.setCurrentRace(null);
		RaceData.setCommand("none");
		RaceData.setOffset(false);
		for (int i = 0; i < 7; i++) {
			RaceData.setChannel(i, new Channel());
		}
	}
	
	public void print() {
		if (!RaceData.isPower() || RaceData.getCurrentRace() == null)
			return;
		
		for (Racer r: RaceData.getCurrentRace().getFinishedRacers()) {
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
		if (!RaceData.isPower()) return;
		
	}
	
	public void num(String str) {
		if (!RaceData.getCurrentRace().containsBib(Integer.parseInt(str)))
			RaceData.getCurrentRace().addReady(new Racer(Integer.parseInt(str)));
	}
	
	public void trig(String str, Time time)
	{
		
	}
	
	public void event (String str)
	{
		
	}
	
	public void tog(String str)
	{
		
	}
	
	public void newrun() {
		
	}
	
	public void endrun() {
		
	}
	
	public void export(int run) { //Why does this take an int? 
		
	}

}
