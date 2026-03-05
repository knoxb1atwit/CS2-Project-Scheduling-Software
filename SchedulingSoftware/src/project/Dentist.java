package project;

import java.time.LocalTime;

public class Dentist {
// attributes
	private String name;
	private LocalTime breakTimeStarts;
	private LocalTime breakTimeEnds;
	private Schedule schedule;

//Constructors
	public Dentist(String name, LocalTime breakTimeStarts, LocalTime breakTimeEnds) {
		this.name = name;
		this.breakTimeStarts = breakTimeStarts;
		this.breakTimeEnds = breakTimeEnds;
	}

//Methods
	public boolean isAvaliable(LocalTime newTime) {
		if (LocalTime.now().isAfter(breakTimeStarts) && LocalTime.now().isBefore(breakTimeEnds)) {
			return false;
		}

		return true;
	}

	public Schedule getSchedule() {
		return this.schedule;
	}

	public String getName() {
		return name;
	}

}
