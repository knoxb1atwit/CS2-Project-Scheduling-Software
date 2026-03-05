package project;

import java.time.LocalTime;
import java.time.LocalDate;

public class Appointment {
//attributes
	private int appointmentId;
	private LocalDate date;
	private LocalTime startTime;
	private Client client;
	private Dentist dentist;
	private AppointmentType type;

//constructors
	public Appointment(int appointmentId, LocalDate date, LocalTime startTime, AppointmentType type) {
		this.appointmentId = appointmentId;
		this.date = date;
		this.startTime = startTime;
		this.type = type;
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
