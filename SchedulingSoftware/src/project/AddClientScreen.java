package project;

import javax.swing.*;
import java.awt.*;

public class AddClientScreen extends JPanel {

    public AddClientScreen(JFrame frame, MainScreen mainScreen) {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JLabel title = new JLabel("Add New Client");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        // Form
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets  = new Insets(8, 8, 8, 8);
        gbc.anchor  = GridBagConstraints.WEST;
        gbc.fill    = GridBagConstraints.HORIZONTAL;

        JTextField nameField  = new JTextField(20);
        JTextField phoneField = new JTextField(20);
        JComboBox<Dentist> dentistBox = new JComboBox<>(DataStore.getDentists().toArray(new Dentist[0]));

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0; form.add(new JLabel("Full Name:"),       gbc);
        gbc.gridx = 1; gbc.weightx = 1;                 form.add(nameField,                      gbc);
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0; form.add(new JLabel("Phone Number:"),    gbc);
        gbc.gridx = 1; gbc.weightx = 1;                 form.add(phoneField,                     gbc);
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0; form.add(new JLabel("Preferred Dentist:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;                 form.add(dentistBox,                     gbc);

        add(form, BorderLayout.CENTER);

        // Buttons + error
        JLabel errorLabel = new JLabel(" ");
        errorLabel.setForeground(Color.RED);

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        JButton saveBtn = new JButton("Save Client");
        JButton backBtn = new JButton("← Back");

        saveBtn.addActionListener(e -> {
            String name     = nameField.getText().trim();
            String phone    = phoneField.getText().trim();
            Dentist dentist = (Dentist) dentistBox.getSelectedItem();

            Receptionist receptionist = new Receptionist(DataStore.getSchedule());
            String error = receptionist.registerClient(name, phone, dentist);
            if (error == null) {
                JOptionPane.showMessageDialog(frame, "Client \"" + name + "\" added!", "Success", JOptionPane.INFORMATION_MESSAGE);
                nameField.setText("");
                phoneField.setText("");
                errorLabel.setText(" ");
            } else {
                errorLabel.setText(error);
            }
        });

        backBtn.addActionListener(e -> {
            frame.setContentPane(mainScreen);
            frame.revalidate();
        });

        btnRow.add(backBtn);
        btnRow.add(saveBtn);

        JPanel south = new JPanel(new BorderLayout(4, 4));
        south.add(errorLabel, BorderLayout.NORTH);
        south.add(btnRow, BorderLayout.SOUTH);
        add(south, BorderLayout.SOUTH);
    }
}
