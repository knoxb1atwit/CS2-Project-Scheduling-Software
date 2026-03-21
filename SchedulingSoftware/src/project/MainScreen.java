package project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MainScreen extends JPanel {

    private final JTable table;
    private final DefaultTableModel tableModel;
    private final JLabel weekLabel;
    private LocalDate weekStart;

    private static final String[] COLUMNS = {"Date", "Time", "Client", "Dentist", "Type", "Duration"};
    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("EEE, MMM d");

    public MainScreen(JFrame frame) {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        weekStart = LocalDate.now();

        // --- Top bar ---
        JPanel topBar = new JPanel(new BorderLayout(8, 0));

        JLabel title = new JLabel("Dentist Office Scheduler");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        topBar.add(title, BorderLayout.WEST);

        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        JButton prevBtn = new JButton("< Prev Week");
        weekLabel = new JLabel("", SwingConstants.CENTER);
        weekLabel.setPreferredSize(new Dimension(200, 28));
        JButton nextBtn = new JButton("Next Week >");

        prevBtn.addActionListener(e -> { weekStart = weekStart.minusWeeks(1); refresh(); });
        nextBtn.addActionListener(e -> { weekStart = weekStart.plusWeeks(1); refresh(); });

        navPanel.add(prevBtn);
        navPanel.add(weekLabel);
        navPanel.add(nextBtn);
        topBar.add(navPanel, BorderLayout.EAST);
        add(topBar, BorderLayout.NORTH);

        // --- Table ---
        tableModel = new DefaultTableModel(COLUMNS, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        table.setRowHeight(28);
        table.setFillsViewportHeight(true);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        add(new JScrollPane(table), BorderLayout.CENTER);

        // --- Bottom button bar ---
        JPanel btnBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));

        JButton bookBtn   = new JButton("+ Book Appointment");
        JButton clientBtn = new JButton("Add Client");
        JButton viewBtn   = new JButton("View Clients");
        JButton cancelBtn = new JButton("Cancel Selected");

        bookBtn.addActionListener(e -> {
            BookAppointmentScreen screen = new BookAppointmentScreen(frame, this);
            frame.setContentPane(screen);
            frame.revalidate();
        });

        clientBtn.addActionListener(e -> {
            AddClientScreen screen = new AddClientScreen(frame, this);
            frame.setContentPane(screen);
            frame.revalidate();
        });

        viewBtn.addActionListener(e -> {
            ViewClientsScreen screen = new ViewClientsScreen(frame, this);
            frame.setContentPane(screen);
            frame.revalidate();
        });

        cancelBtn.addActionListener(e -> cancelSelected(frame));

        btnBar.add(bookBtn);
        btnBar.add(clientBtn);
        btnBar.add(viewBtn);
        btnBar.add(Box.createHorizontalStrut(20));
        btnBar.add(cancelBtn);
        add(btnBar, BorderLayout.SOUTH);

        refresh();
    }

    public void refresh() {
        tableModel.setRowCount(0);
        LocalDate end = weekStart.plusDays(6);
        weekLabel.setText(weekStart.format(DATE_FMT) + "  –  " + end.format(DATE_FMT));

        List<Appointment> appts = DataStore.getSchedule().getAppointmentsForWeek(weekStart);
        for (Appointment a : appts) {
            String clientName  = a.getClient()  != null ? a.getClient().getName()           : "—";
            String dentistName = a.getDentist() != null ? a.getDentist().getName()           : "—";
            String duration    = a.getType().getDuration() + " min";
            tableModel.addRow(new Object[]{
                a.getDate().format(DATE_FMT),
                a.getStartTime() + " – " + a.getEndTime(),
                clientName,
                dentistName,
                a.getType().getAppointmentName(),
                duration
            });
        }
    }

    private void cancelSelected(JFrame frame) {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(frame, "Select an appointment to cancel.", "No selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        List<Appointment> appts = DataStore.getSchedule().getAppointmentsForWeek(weekStart);
        Appointment toCancel = appts.get(row);
        int confirm = JOptionPane.showConfirmDialog(frame,
            "Cancel appointment for " + toCancel.getClient().getName() + " on " + toCancel.getDate() + "?",
            "Confirm cancellation", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Receptionist r = new Receptionist(DataStore.getSchedule());
            r.cancelAppointment(toCancel);
            refresh();
        }
    }
}
