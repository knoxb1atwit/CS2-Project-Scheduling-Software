package project;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class BookAppointmentScreen extends JPanel {

    private final JComboBox<Client>          clientBox;
    private final JComboBox<Dentist>         dentistBox;
    private final JComboBox<AppointmentType> typeBox;
    private final JComboBox<LocalTime>       timeBox;
    private final JSpinner                   datePicker;
    private final JLabel                     errorLabel;
    private final JFrame                     frame;
    private final MainScreen                 mainScreen;

    public BookAppointmentScreen(JFrame frame, MainScreen mainScreen) {
        this.frame      = frame;
        this.mainScreen = mainScreen;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        // Title
        JLabel title = new JLabel("Book New Appointment");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        // Form
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets  = new Insets(8, 8, 8, 8);
        gbc.anchor  = GridBagConstraints.WEST;
        gbc.fill    = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        clientBox  = new JComboBox<>(DataStore.getClients().toArray(new Client[0]));
        dentistBox = new JComboBox<>(DataStore.getDentists().toArray(new Dentist[0]));
        typeBox    = new JComboBox<>(DataStore.getAppointmentTypes().toArray(new AppointmentType[0]));
        timeBox    = new JComboBox<>();

        // Date picker using a spinner
        SpinnerDateModel dateModel = new SpinnerDateModel();
        datePicker = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(datePicker, "yyyy-MM-dd");
        datePicker.setEditor(dateEditor);

        // Refresh time slots when dentist or type changes
        dentistBox.addActionListener(e -> refreshTimeSlots());
        typeBox.addActionListener(e -> refreshTimeSlots());
        datePicker.addChangeListener(e -> refreshTimeSlots());

        addRow(form, gbc, 0, "Client:",           clientBox);
        addRow(form, gbc, 1, "Dentist:",          dentistBox);
        addRow(form, gbc, 2, "Appointment Type:", typeBox);
        addRow(form, gbc, 3, "Date:",             datePicker);
        addRow(form, gbc, 4, "Start Time:",       timeBox);

        add(form, BorderLayout.CENTER);

        // Error label + buttons
        JPanel bottom = new JPanel(new BorderLayout(4, 4));
        errorLabel = new JLabel(" ");
        errorLabel.setForeground(Color.RED);
        bottom.add(errorLabel, BorderLayout.NORTH);

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        JButton nextBtn = new JButton("Review Appointment →");
        JButton backBtn = new JButton("← Back");

        nextBtn.addActionListener(e -> goToConfirmation());
        backBtn.addActionListener(e -> goBack());

        btnRow.add(backBtn);
        btnRow.add(nextBtn);
        bottom.add(btnRow, BorderLayout.SOUTH);
        add(bottom, BorderLayout.SOUTH);

        refreshTimeSlots();
    }

    private void addRow(JPanel panel, GridBagConstraints gbc, int row, String label, JComponent field) {
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        panel.add(field, gbc);
    }

    private void refreshTimeSlots() {
        timeBox.removeAllItems();
        Dentist dentist = (Dentist) dentistBox.getSelectedItem();
        AppointmentType type = (AppointmentType) typeBox.getSelectedItem();
        if (dentist == null || type == null) return;

        LocalDate date = getSelectedDate();
        List<LocalTime> slots = DataStore.getSchedule().getAvailableSlots(date, dentist, type.getDuration());
        for (LocalTime slot : slots) {
            timeBox.addItem(slot);
        }
        if (slots.isEmpty()) {
            errorLabel.setText("No available slots for this dentist/type/date.");
        } else {
            errorLabel.setText(" ");
        }
    }

    private LocalDate getSelectedDate() {
        java.util.Date d = (java.util.Date) datePicker.getValue();
        return d.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
    }

    private void goToConfirmation() {
        Client client          = (Client)          clientBox.getSelectedItem();
        Dentist dentist        = (Dentist)         dentistBox.getSelectedItem();
        AppointmentType type   = (AppointmentType) typeBox.getSelectedItem();
        LocalTime time         = (LocalTime)       timeBox.getSelectedItem();
        LocalDate date         = getSelectedDate();

        if (time == null) {
            errorLabel.setText("No time slot available. Try a different date or dentist.");
            return;
        }

        ConfirmationScreen screen = new ConfirmationScreen(frame, mainScreen, date, time, client, dentist, type);
        frame.setContentPane(screen);
        frame.revalidate();
    }

    private void goBack() {
        frame.setContentPane(mainScreen);
        frame.revalidate();
    }
}
