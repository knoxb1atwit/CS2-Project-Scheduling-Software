package project;

import java.time.LocalTime;
import java.time.LocalDate;

public class Appointment {
//attributes
	private LocalDate date;
	private LocalTime startTime;
	private Client client;
	private Dentist dentist;
	private AppointmentType type;

//constructors
	public Appointment(LocalDate date, LocalTime startTime, Client client, Dentist dentist, AppointmentType type) {
		this.date = date;
		this.startTime = startTime;
		this.client = client;
		this.dentist=dentist;
		this.type = type;
	}

	public LocalDate getDate() {
		return this.date;
	}
	
	public LocalTime getStartTime() {
		return this.startTime;
	}
	
	public AppointmentType getType() {
		return this.type;
	}
	
	public Client getClient() {
		return this.client;
	}
	
	public Dentist getDentist() {
		return this.dentist;
	}
//methods

	public void rescheduleAppointment(LocalDate newDate, LocalTime newTime ) {
		this.date = newDate;
		this.startTime = newTime;
	}

	public String getDetails() {
		return "Appointment on " + date + " at " + startTime + " ( " + type + " ) ";
	}

}
