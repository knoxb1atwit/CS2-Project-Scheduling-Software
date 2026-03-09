package project;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Schedule {

// Attributes
	private List<Appointment> appointments;
	private LocalTime workingHoursStart;
	private LocalTime workingHoursEnd;

// Constructor
	public Schedule(LocalTime workingHoursStart, LocalTime workingHoursEnd) {
		this.workingHoursStart = workingHoursStart;
		this.workingHoursEnd = workingHoursEnd;
		this.appointments = new ArrayList<>();
	}

//Methods
	
	public boolean checkAvailability(LocalDateTime dateTime, int duration) {
		LocalTime start = dateTime.toLocalTime();
		LocalTime end = start.plusMinutes(duration);

		// Check working hours
		if (start.isBefore(workingHoursStart) || end.isAfter(workingHoursEnd)) {
			return false;
		}

		//Check for conflicts with other appointments
		for (Appointment a : appointments) {
			if (a.getDate().equals(dateTime.toLocalDate())) {
				LocalTime existingStart = a.getStartTime();
				LocalTime existingEnd = existingStart.plusMinutes(a.getType().getDuration());

				boolean overlap = start.isBefore(existingEnd) && end.isAfter(existingStart);

				if (overlap) {
					return false;
				}
			}
		}

		return true;
	}

	public void addAppointment(Appointment a) {
		appointments.add(a);
	}

	public void removeAppointment(Appointment a) {
		appointments.remove(a);
	}

	public List<Appointment> getAppointmentsForDay(LocalDate date) {
		List<Appointment> result = new ArrayList<>();

		for (Appointment a : appointments) {
			if (a.getDate().equals(date)) {
				result.add(a);
			}
		}

		return result;
	}
}
