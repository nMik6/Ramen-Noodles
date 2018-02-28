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
	
	public static void main(String[] args) {
		Time t1 = new Time(LocalTime.now());
		Time t2 = new Time("12:03:24.1");
		System.out.println(t1.difference(t2).getTime());
	}
	
	public Time difference(Time in) {
		LocalTime ret;
		ret = this.getTime().minusHours(in.getTime().getHour())
				.minusMinutes(in.getTime().getMinute())
				.minusSeconds(in.getTime().getSecond())
				.minusNanos(in.getTime().getNano());
		
		return new Time(ret);	
	}
	
	public void setTime(LocalTime time) {
		this.time = time;
	}
	
	public LocalTime getTime() {
		return time;
	}

}
