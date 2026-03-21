package project;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

// Single source of truth for all data during the session.
// All UI screens read and write through this class.
public class DataStore {

    private static final List<Dentist>         dentists         = new ArrayList<>();
    private static final List<Client>          clients          = new ArrayList<>();
    private static final List<AppointmentType> appointmentTypes = new ArrayList<>();
    private static final Schedule              schedule         = new Schedule(LocalTime.of(9, 0), LocalTime.of(17, 0));

    // Seed default data so the app works on first launch
    static {
        AppointmentType cleaning = new AppointmentType("Tooth Cleaning", 60);
        AppointmentType filling  = new AppointmentType("Cavity Filling", 120);
        appointmentTypes.add(cleaning);
        appointmentTypes.add(filling);

        Dentist smith  = new Dentist("Dr. Smith",  LocalTime.of(12, 0), LocalTime.of(13, 0), schedule);
        Dentist patel  = new Dentist("Dr. Patel",  LocalTime.of(13, 0), LocalTime.of(14, 0), schedule);
        dentists.add(smith);
        dentists.add(patel);

        clients.add(new Client("John Doe",   "555-1234", smith));
        clients.add(new Client("Jane Smith", "555-5678", patel));
    }

    public static List<Dentist>         getDentists()         { return dentists; }
    public static List<Client>          getClients()          { return clients; }
    public static List<AppointmentType> getAppointmentTypes() { return appointmentTypes; }
    public static Schedule              getSchedule()         { return schedule; }

    public static void addClient(Client c)   { clients.add(c); }
    public static void addDentist(Dentist d) { dentists.add(d); }
}
