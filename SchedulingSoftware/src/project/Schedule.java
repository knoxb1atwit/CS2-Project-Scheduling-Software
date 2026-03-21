package project;

import java.time.LocalDate;
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

	public String addAppointment(Appointment a) {
	    String scheduleError = checkAvailability(a.getDate(), a.getStartTime(), a.getType().getDuration());
	    if (scheduleError != null) return scheduleError;

	    if (a.getDentist() != null) {
	        if (!a.getDentist().isAvailable(a.getStartTime(), a.getType().getDuration())) {
	            return a.getDentist().getName() + " is on break at that time.";
	        }
	    }

	    appointments.add(a);
	    return null;
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
	
	public List<Appointment> getAppointmentsForWeek(LocalDate startDate) {
	    List<Appointment> result = new ArrayList<>();
	    for (int i = 0; i < 7; i++) {
	        result.addAll(getAppointmentsForDay(startDate.plusDays(i)));
	    }
	    return result;
	}

	public List<Appointment> getAllAppointments() {
	    return new ArrayList<>(appointments);
	}

	public List<LocalTime> getAvailableSlots(LocalDate date, Dentist dentist, int duration) {
	    List<LocalTime> slots = new ArrayList<>();
	    LocalTime cursor = workingHoursStart;
	    while (!cursor.plusMinutes(duration).isAfter(workingHoursEnd)) {
	        boolean scheduleOk = checkAvailability(date, cursor, duration) == null;
	        boolean dentistOk  = (dentist == null) || dentist.isAvailable(cursor, duration);
	        if (scheduleOk && dentistOk) slots.add(cursor);
	        cursor = cursor.plusHours(1);
	    }
	    return slots;
	}
}
