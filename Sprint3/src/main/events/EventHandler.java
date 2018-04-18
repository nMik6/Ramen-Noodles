package main.events;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import main.RaceData;
import main.Time;

public class EventHandler {
	
	private Time time;
	private String[] command;
	private Event event;
	Time timeOffset;
	boolean offsetPos;
	
	public EventHandler(Time time, String[] command) {
		this.time = time;
		this.command = command;
		this.event = new Event();
		offsetPos =  false;
		
		
	}
	
	public void handle() {
		
		parse(this.command);
	}
	
	
	/**
	 * Parses the commands that are entered and executes them
	 * @param commandLine the commands entered
	 */
	public void parse(String[] commandLine) {
		int length = commandLine.length;
		Time passedTime = null;
		LocalTime toCheck;

		//Checks if first arg is a time. If it is, it is removed from the command line array and the commandLine array is shortened
		if (commandLine[0] != null) {
			final DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss.S");

			try { toCheck = LocalTime.parse(commandLine[0], format); }
			catch (Exception e) { toCheck = null; }

			if (toCheck != null && length > 1) {
				String[] cmdLineNew = new String[length-1];
				passedTime = new Time(toCheck);

				for (int i = 1; i< length; ++i) 
					cmdLineNew[i-1] = commandLine[i];

				commandLine = cmdLineNew;
				length--;
			}
		}

		if (length == 1) {
			switch (commandLine[0].toLowerCase()) {
			case "power":
				event.power();
				break;
			case "exit":
				event.exit();
				break;
			case "reset":
				event.reset();
				break;
			case "print":
				event.print();
				break;
			case "start":
				if (passedTime != null) event.trig("1", passedTime);
				else {
					if (timeOffset == null)event.trig("1", new Time());
					else if (offsetPos)event.trig("1", new Time().add(timeOffset));
					else event.trig("1", new Time().difference(timeOffset));
				}
				break;
			case "end":
				if (passedTime != null) event.trig("2", passedTime);
				else {
					if (timeOffset == null)event.trig("2", new Time());
					else if (offsetPos)event.trig("2", new Time().add(timeOffset));
					else event.trig("2", new Time().difference(timeOffset));
				}
				break;
			case "newrun":
				event.newrun();
				break;
			case "endrun":
				event.endrun();
				break;
			default:
				error();
			}
		}

		else if (length == 2) {
			switch (commandLine[0].toLowerCase()) {

			case "event":
				event.event(commandLine[1]);
				break;
			case "num":
				event.num(commandLine[1]);
				break;
			case "trig":
				if (passedTime != null) event.trig(commandLine[1], passedTime);
				else {
					if (timeOffset == null)event.trig(commandLine[1], new Time());
					else if (offsetPos)event.trig(commandLine[1], new Time().add(timeOffset));
					else event.trig(commandLine[1], new Time().difference(timeOffset));
				}
				break;
			case "time":
				event.time(commandLine[1]);
				break;
			case "tog":
				event.tog(commandLine[1]);
				break;
				//If endrun is called prior to export, an automatic export will have occurred and the race will have been cleared
			case "export":
				try {
					event.export(Integer.parseInt(commandLine[1]));
				}catch(Exception e) {error();}
				break;
			default:	
				error();

			}
		}

		else if (length == 3) {
			if (commandLine[0].equalsIgnoreCase("conn"))
				event.conn(commandLine[1], commandLine[2]);
			else
				error();
		} 

		else { 	//Error
			error();
		}
		
		
	}
	
	public void error() {
		if (RaceData.isPower()) System.out.println("Invalid command");
	}
	

}
