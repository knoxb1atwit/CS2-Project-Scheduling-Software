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
	
	public String checkAvailability(LocalDate date, LocalTime startTime, int duration) {
		// Must start on the hour
		if (startTime.getMinute() != 0) {
	        return "Appointments must start on the hour (e.g. 9:00, 10:00).";
	    }
		LocalTime endTime = startTime.plusMinutes(duration);

	    if (startTime.isBefore(workingHoursStart) || endTime.isAfter(workingHoursEnd)) {
	        return "Time is outside office hours (" + workingHoursStart + " - " + workingHoursEnd + ").";
	    }

	    for (Appointment a : appointments) {
	        if (a.getDate().equals(date)) {
	            LocalTime existingStart = a.getStartTime();
	            LocalTime existingEnd   = existingStart.plusMinutes(a.getType().getDuration());
	            boolean overlap = startTime.isBefore(existingEnd) && endTime.isAfter(existingStart);
	            if (overlap) {
	                return "Time conflicts with an existing appointment at " + existingStart + ".";
	            }
	        }
	    }

	    return null; // available
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
