package project;

import java.time.LocalDate;
import java.time.LocalTime;

// Handles all booking operations. UI screens call this instead of
// touching Schedule directly.
public class Receptionist {

    private final Schedule schedule;

    public Receptionist(Schedule schedule) {
        this.schedule = schedule;
    }

    // Returns null on success, or an error message string on failure.
    public String bookAppointment(LocalDate date, LocalTime startTime,
                                  Client client, Dentist dentist, AppointmentType type) {
        if (client == null)  return "Please select a client.";
        if (dentist == null) return "Please select a dentist.";
        if (type == null)    return "Please select an appointment type.";
        if (date == null)    return "Please select a date.";
        if (startTime == null) return "Please select a time.";

        Appointment appt = new Appointment(date, startTime, client, dentist, type);
        return schedule.addAppointment(appt); // null = success, string = error
    }

    // Returns null on success, error string if not found.
    public String cancelAppointment(Appointment appt) {
        if (appt == null) return "No appointment selected.";
        schedule.removeAppointment(appt);
        return null;
    }

    // Registers a new client and returns it.
    public String registerClient(String name, String phone, Dentist preferredDentist) {
        if (name == null || name.trim().isEmpty())  return "Name cannot be empty.";
        if (phone == null || phone.trim().isEmpty()) return "Phone number cannot be empty.";
        Client c = new Client(name.trim(), phone.trim(), preferredDentist);
        DataStore.addClient(c);
        return null; // success
    }
}
