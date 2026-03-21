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