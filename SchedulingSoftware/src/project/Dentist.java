package project;

import java.time.LocalTime;

public class Dentist {
// attributes
	private String name;
	private LocalTime breakTimeStarts;
	private LocalTime breakTimeEnds;
	private Schedule schedule;

//Constructors
	public Dentist(String name, LocalTime breakTimeStarts, LocalTime breakTimeEnds, Schedule schedule) {
		this.name = name;
		this.breakTimeStarts = breakTimeStarts;
		this.breakTimeEnds = breakTimeEnds;
		this.schedule = schedule;
	}

	public String getName() {
		return this.name;
	}

	public LocalTime getBreakTimeStart() {
		return this.breakTimeStarts;
	}

	public LocalTime getBreakTimeEnds() {
		return this.breakTimeEnds;
	}

	public Schedule getSchedule() {
		return this.schedule;
	}

//Methods
	public boolean isAvailable(LocalTime startTime, int duration) {
		public boolean isAvailable(LocalTime startTime, int duration) {
		    LocalTime endTime = startTime.plusMinutes(duration);
		    boolean overlap = startTime.isBefore(breakTimeEnds) && endTime.isAfter(breakTimeStarts);
		    return !overlap;
		}
	
	public String toString() {
	    return name;
	}
}
