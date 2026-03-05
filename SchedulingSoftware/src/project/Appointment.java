package project;

import java.time.LocalTime;
import java.time.LocalDate;

public class Appointment {
//attributes
	private int appointmentId;
	private LocalDate date;
	private LocalTime startTime;
	private int duration;
	private Client client;
	private Dentist dentist;
	private AppointmentType type;

//constructors
	public Appointment(int appointmentId, LocalDate date, LocalTime startTime, int duration) {
		this.appointmentId = appointmentId;
		this.date = date;
		this.startTime = startTime;
		this.duration = duration;
	}

//methods
	public void confirmAppointment() {

	}

	public void cancelAppointment() {

	}

	public void rescheduleAppointment(LocalDate newDate, LocalTime newTime ) {
		this.date = newDate;
		this.startTime = newTime;
	}

	public String getDetails() {
		return "";
	}

}
