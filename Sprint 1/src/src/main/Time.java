package src.main;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Time {
	
	private LocalTime time;
	private transient static DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss.S");

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
	
	public Time addSeconds(int amount) {
		return new Time(this.time.plusSeconds(amount));
	}
	
	public Time addMinutes(int amount) {
		return new Time(this.time.plusMinutes(amount));
	}
	
	public Time addHours(int amount) {
		return new Time(this.time.plusHours(amount));
	}
	
	public Time difference(Time one, Time two) {
		String hour = Long.toString(ChronoUnit.HOURS.between(one.getTime(), two.getTime()));
		String minute = Long.toString(ChronoUnit.MINUTES.between(one.getTime(), two.getTime()));
		String second = Long.toString(ChronoUnit.SECONDS.between(one.getTime(), two.getTime()));
		String milli = Long.toString(ChronoUnit.MILLIS.between(one.getTime(), two.getTime()));
		if (hour.length() < 2) hour = "0" + hour;
		if (minute.length() < 2) minute = "0" + minute;
		if (second.length() < 2) second = "0" + second;
		milli = milli.substring(0, 1);
		return new Time(LocalTime.parse(hour + ":" + minute + ":" + second + "." + milli, format));
	}
	
	public LocalTime getTime() {
		return time;
	}

}
