package project;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Dentist Office Scheduler");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 600);
            frame.setMinimumSize(new java.awt.Dimension(700, 450));
            frame.setLocationRelativeTo(null);

            MainScreen mainScreen = new MainScreen(frame);
            frame.setContentPane(mainScreen);
            frame.setVisible(true);
        });
    }
}