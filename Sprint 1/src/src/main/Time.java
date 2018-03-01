package src.main;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Time {
	
	private LocalTime time;
	private transient static DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss.S");

	/**
	 * Default constructor with time set at the time initialized
	 */
	public Time() {
		time = LocalTime.now();
	}
	
	/**
	 * Time set with a given LocalTime object
	 * @param time to set the time at
	 */
	public Time(LocalTime time) {
		this.time = time;
	}
	
	/**
	 * Time set with a given string
	 * @param time to set the time at
	 */
	public Time(String time) {
		this.time = LocalTime.parse(time, format);
	}
	
	/**
	 * Prints the time
	 * @return the time
	 */
	public String printTime() {
		return time.toString();
	}
	
	/**
	 * Adds time to an exsisting time
	 * @param t time to add
	 * @return the new time
	 */
	public Time add(Time t) {
		LocalTime ret;
		LocalTime toAdd = t.getTime();
		
		ret = time.plusHours(toAdd.getHour())
				  .plusMinutes(toAdd.getMinute())
				  .plusSeconds(toAdd.getSecond())
				  .plusNanos(toAdd.getNano());
		return new Time(ret);
	}
	
	public static void main(String[] args) {
		Time t1 = new Time(LocalTime.now());
		Time t2 = new Time("12:03:24.1");
		System.out.println(t1.difference(t2).getTime());
	}
	
	/**
	 * Gets the difference between two different times
	 * @param in the time to get the difference with
	 * @return the difference in time
	 */
	public Time difference(Time in) {
		LocalTime ret;
		LocalTime late = time;
		LocalTime early = in.getTime();
		//handles improper input order.
		if(late.isBefore(early)) {
			late = early;
			early=time;	
		}
		
		ret = late.minusHours(early.getHour())
				.minusMinutes(early.getMinute())
				.minusSeconds(early.getSecond())
				.minusNanos(early.getNano());
		
		return new Time(ret);	
	}
	
	/**
	 * Checks if a time is before another time
	 * @param in time to check with current
	 * @return true if the current time is before
	 */
	public boolean isBefore(Time in) {
		if(this.getTime().isBefore(in.getTime())) return true;
		return false;
	}
	
	/**
	 * Gets the time in a LocalTime object
	 * @return the time
	 */
	public LocalTime getTime() {
		return time;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Time)) return false;
		Time other = (Time) obj;
		return this.getTime().getHour() == other.getTime().getHour() 
				&& this.getTime().getMinute() == other.getTime().getMinute()
				&& this.getTime().getSecond() == other.getTime().getSecond() 
				&& this.getTime().getNano() == other.getTime().getNano();
	}

}
