package src.main;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Time {
	
	private LocalTime time;
	
	public Time() {
		time = LocalTime.now();
	}
	
	public Time(LocalTime time) {
		this.time = time;
	}
	
	public Time(String time) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
		this.time = LocalTime.parse(time, format);
	}
	
	public String timeDifference(Time one, Time two) {
		long hour = ChronoUnit.HOURS.between(one.getTime(), two.getTime());
		long minute = ChronoUnit.MINUTES.between(one.getTime(), two.getTime());
		long second = ChronoUnit.SECONDS.between(one.getTime(), two.getTime());
		long milli = ChronoUnit.MILLIS.between(one.getTime(), two.getTime());
		return hour + ":" + minute + ":" + second + ":" + milli;
	}
	
	public LocalTime getTime() {
		return time;
	}

}
