package main.events;

import java.time.format.DateTimeFormatter;
import main.RaceData;
import main.racing.Racer;

import java.time.LocalTime;
import java.time.format.FormatStyle;

import main.Time;

public class Event {
	
	private Time time;
	private DateTimeFormatter format;
	private FormatStyle SHORT;
	
	
	public void power() {
		RaceData.switchPower();
	}
	
	public void exit() {
		
	}
	
	public void conn (String one, String two) {
		
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
