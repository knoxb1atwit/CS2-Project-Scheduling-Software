package project;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {
public Main() {
	
	JFrame frame = new JFrame();
	JPanel panel = new JPanel();
	panel.setBorder(BorderFactory.createEmptyBorder(500, 500, 250, 500));
	panel.setLayout(new GridLayout(0, 1));
	
	frame.add(panel, BorderLayout.CENTER);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setTitle("Schudle");
	frame.pack();
	frame.setVisible(true);
}

	public static void main(String[] args) {
		
		new Main();
		
		// Create a schedule
		Schedule schedule = new Schedule(LocalTime.of(9, 0), LocalTime.of(17, 0));
		
		// Create a dentist
		Dentist dentist = new Dentist("Dr. Smith", LocalTime.of(12, 0), LocalTime.of(13, 0), schedule);
		
		// Create appointment types
		AppointmentType cleaning = new AppointmentType("Tooth Cleaning", 30);
		AppointmentType filling = new AppointmentType("Cavity Filling", 60);
		
		// Create a client
		Client client = new Client("John Doe", "555-1234", dentist);
		
		
		// Create an appointment
		Appointment appointment = new Appointment(LocalDate.of(2026, 3, 10), LocalTime.of(10, 0), cleaning);
		
		// Add appointment to schedule
		schedule.addAppointment(appointment);
		
		// Print appointment detail
		System.out.println("Appointment created:");
		System.out.println(appointment.getDetails());
		
		// Show appointments for the day
		System.out.println("\nAppointments on March 10:");
		for (Appointment a : schedule.getAppointmentsForDay(LocalDate.of(2026, 3, 10))) {
			System.out.println(a.getDetails());
		}
		
		if (schedule.addAppointment(appointment)) {
		    System.out.println("Appointment created:");
		    System.out.println(appointment.getDetails());
		} else {
		    System.out.println("Could not schedule appointment.");
		}
	}
}
