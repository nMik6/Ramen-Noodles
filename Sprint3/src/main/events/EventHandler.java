package main.events;

import main.Time;

public class EventHandler {
	
	private Time time;
	private String[] command;
	
	public EventHandler(Time time, String[] command) {
		this.time = time;
		this.command = command;
		
	}
	
	public String handle(String str) {
		return "";
	}
	

}
