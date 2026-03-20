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

	public boolean addAppointment(Appointment a) {
		LocalDateTime dateTime = LocalDateTime.of(a.getDate(), a.getStartTime());
	    int duration = a.getType().getDuration();

	    // Check schedule conflicts
	    if (!checkAvailability(dateTime, duration)) {
	        return false;
	    }

	    // Check dentist availability (if dentist exists)
	    if (a.getDentist() != null) {
	        if (!a.getDentist().isAvailable(a.getStartTime(), duration)) {
	            return false;
	        }
	    }

	    appointments.add(a);
	    return true;
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
