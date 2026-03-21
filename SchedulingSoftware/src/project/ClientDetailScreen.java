package project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class ClientDetailScreen extends JPanel {

    public ClientDetailScreen(JFrame frame, MainScreen mainScreen, ViewClientsScreen viewClientsScreen, Client client) {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JLabel title = new JLabel("Client: " + client.getName());
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        // Info panel
        JPanel info = new JPanel(new GridBagLayout());
        info.setBorder(BorderFactory.createTitledBorder("Client Information"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.anchor = GridBagConstraints.WEST;

        String dentistName = (client.getPrefferedDentists() != null) ? client.getPrefferedDentists().getName() : "None";

        gbc.gridx = 0; gbc.gridy = 0; info.add(new JLabel("Name:"),             gbc);
        gbc.gridx = 1;                info.add(new JLabel(client.getName()),     gbc);
        gbc.gridx = 0; gbc.gridy = 1; info.add(new JLabel("Preferred Dentist:"), gbc);
        gbc.gridx = 1;                info.add(new JLabel(dentistName),          gbc);

        // Editable phone number
        gbc.gridx = 0; gbc.gridy = 2; info.add(new JLabel("Phone:"), gbc);
        JTextField phoneField = new JTextField(client.getPhoneNumber(), 15);
        gbc.gridx = 1; info.add(phoneField, gbc);
        JButton savePhoneBtn = new JButton("Update");
        gbc.gridx = 2; info.add(savePhoneBtn, gbc);

        savePhoneBtn.addActionListener(e -> {
            String newPhone = phoneField.getText().trim();
            if (newPhone.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Phone number cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            client.updatePhoneNumber(newPhone);
            JOptionPane.showMessageDialog(frame, "Phone number updated.", "Saved", JOptionPane.INFORMATION_MESSAGE);
        });

        // Upcoming appointments table
        JPanel apptPanel = new JPanel(new BorderLayout(4, 4));
        apptPanel.setBorder(BorderFactory.createTitledBorder("Upcoming Appointments"));

        String[] cols = {"Date", "Time", "Dentist", "Type"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable apptTable = new JTable(model);
        apptTable.setRowHeight(26);

        List<Appointment> all = DataStore.getSchedule().getAllAppointments();
        LocalDate today = LocalDate.now();
        for (Appointment a : all) {
            if (a.getClient() != null && a.getClient().equals(client) && !a.getDate().isBefore(today)) {
                model.addRow(new Object[]{
                    a.getDate(),
                    a.getStartTime() + " – " + a.getEndTime(),
                    a.getDentist() != null ? a.getDentist().getName() : "—",
                    a.getType().getAppointmentName()
                });
            }
        }

        apptPanel.add(new JScrollPane(apptTable), BorderLayout.CENTER);

        JPanel center = new JPanel(new BorderLayout(0, 12));
        center.add(info, BorderLayout.NORTH);
        center.add(apptPanel, BorderLayout.CENTER);
        add(center, BorderLayout.CENTER);

        // Back button
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backBtn = new JButton("← Back to Clients");
        backBtn.addActionListener(e -> {
            frame.setContentPane(viewClientsScreen);
            frame.revalidate();
        });
        btnRow.add(backBtn);
        add(btnRow, BorderLayout.SOUTH);
    }
}
