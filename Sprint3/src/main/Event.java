package main;

import java.time.format.DateTimeFormatter;
import java.time.LocalTime;
import java.time.format.FormatStyle;

public class Event {
	
	private LocalTime time;
	private DateTimeFormatter format;
	private FormatStyle SHORT;
	
	
	public Event() {
		time = LocalTime.now();
		format = ofLocalizedtime(FormatStyle SHORT);
		
	}
	
	public void power() {
		
	}
	
	public void exit() {
		
	}
	
	public void conn (String one, String two) {
		
	}
	
	public void num(String str) {
		
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
