package project;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class ConfirmationScreen extends JPanel {

    public ConfirmationScreen(JFrame frame, MainScreen mainScreen,
                              LocalDate date, LocalTime startTime,
                              Client client, Dentist dentist, AppointmentType type) {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JLabel title = new JLabel("Confirm Appointment");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        // Details panel
        JPanel details = new JPanel();
        details.setLayout(new BoxLayout(details, BoxLayout.Y_AXIS));
        details.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(16, 16, 16, 16)
        ));

        LocalTime endTime = startTime.plusMinutes(type.getDuration());

        details.add(detailRow("Client:",   client.getName()));
        details.add(detailRow("Dentist:",  dentist.getName()));
        details.add(detailRow("Type:",     type.getAppointmentName()));
        details.add(detailRow("Date:",     date.toString()));
        details.add(detailRow("Time:",     startTime + " – " + endTime));
        details.add(detailRow("Duration:", type.getDuration() + " minutes"));

        add(details, BorderLayout.CENTER);

        // Buttons
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        JLabel errorLabel = new JLabel(" ");
        errorLabel.setForeground(Color.RED);

        JButton confirmBtn = new JButton("Confirm Booking");
        JButton backBtn    = new JButton("← Go Back");

        confirmBtn.addActionListener(e -> {
            Receptionist receptionist = new Receptionist(DataStore.getSchedule());
            String error = receptionist.bookAppointment(date, startTime, client, dentist, type);
            if (error == null) {
                JOptionPane.showMessageDialog(frame, "Appointment booked successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                mainScreen.refresh();
                frame.setContentPane(mainScreen);
                frame.revalidate();
            } else {
                errorLabel.setText("Error: " + error);
            }
        });

        backBtn.addActionListener(e -> {
            BookAppointmentScreen screen = new BookAppointmentScreen(frame, mainScreen);
            frame.setContentPane(screen);
            frame.revalidate();
        });

        btnRow.add(backBtn);
        btnRow.add(confirmBtn);

        JPanel south = new JPanel(new BorderLayout(4, 4));
        south.add(errorLabel, BorderLayout.NORTH);
        south.add(btnRow, BorderLayout.SOUTH);
        add(south, BorderLayout.SOUTH);
    }

    private JPanel detailRow(String label, String value) {
        JPanel row = new JPanel(new BorderLayout());
        row.setBorder(BorderFactory.createEmptyBorder(6, 0, 6, 0));
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 14));
        lbl.setPreferredSize(new Dimension(120, 24));
        JLabel val = new JLabel(value);
        val.setFont(new Font("SansSerif", Font.PLAIN, 14));
        row.add(lbl, BorderLayout.WEST);
        row.add(val, BorderLayout.CENTER);
        return row;
    }
}
