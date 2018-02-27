package src.main;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Time {
	
	private LocalTime time;
	private DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
	
	public Time() {
		time = LocalTime.now();
	}
	
	public Time(LocalTime time) {
		this.time = time;
	}
	
	public Time(String time) {
		this.time = LocalTime.parse(time, format);
	}
	
	public String printTime() {
		return time.toString();
	}
	
	public Time difference(Time one, Time two) {
		long hour = ChronoUnit.HOURS.between(one.getTime(), two.getTime());
		long minute = ChronoUnit.MINUTES.between(one.getTime(), two.getTime());
		long second = ChronoUnit.SECONDS.between(one.getTime(), two.getTime());
		long milli = ChronoUnit.MILLIS.between(one.getTime(), two.getTime());
		return new Time(LocalTime.parse(hour + ":" + minute + ":" + second + "." + milli, format));
	}
	
	public LocalTime getTime() {
		return time;
	}

}
