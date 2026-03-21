package project;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class ViewClientsScreen extends JPanel {

    private final DefaultListModel<Client> listModel;
    private final JList<Client>            clientList;
    private final JFrame                   frame;
    private final MainScreen               mainScreen;

    public ViewClientsScreen(JFrame frame, MainScreen mainScreen) {
        this.frame      = frame;
        this.mainScreen = mainScreen;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JLabel title = new JLabel("All Clients");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        // Search bar
        JTextField searchField = new JTextField();
        searchField.setToolTipText("Search by name...");
        JPanel searchPanel = new JPanel(new BorderLayout(4, 0));
        searchPanel.add(new JLabel("Search: "), BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);

        // Client list
        listModel = new DefaultListModel<>();
        clientList = new JList<>(listModel);
        clientList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        clientList.setFont(new Font("SansSerif", Font.PLAIN, 14));
        clientList.setFixedCellHeight(28);

        JPanel center = new JPanel(new BorderLayout(4, 8));
        center.add(searchPanel, BorderLayout.NORTH);
        center.add(new JScrollPane(clientList), BorderLayout.CENTER);
        add(center, BorderLayout.CENTER);

        // Buttons
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        JButton viewBtn = new JButton("View Details");
        JButton backBtn = new JButton("← Back");

        viewBtn.addActionListener(e -> {
            Client selected = clientList.getSelectedValue();
            if (selected == null) {
                JOptionPane.showMessageDialog(frame, "Select a client first.", "No selection", JOptionPane.WARNING_MESSAGE);
                return;
            }
            ClientDetailScreen detail = new ClientDetailScreen(frame, mainScreen, this, selected);
            frame.setContentPane(detail);
            frame.revalidate();
        });

        backBtn.addActionListener(e -> {
            frame.setContentPane(mainScreen);
            frame.revalidate();
        });

        btnRow.add(backBtn);
        btnRow.add(viewBtn);
        add(btnRow, BorderLayout.SOUTH);

        // Live search filter
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e)  { filter(searchField.getText()); }
            public void removeUpdate(DocumentEvent e)  { filter(searchField.getText()); }
            public void changedUpdate(DocumentEvent e) { filter(searchField.getText()); }
        });

        refresh();
    }

    public void refresh() {
        filter("");
    }

    private void filter(String query) {
        listModel.clear();
        List<Client> clients = DataStore.getClients();
        String q = query.toLowerCase().trim();
        for (Client c : clients) {
            if (q.isEmpty() || c.getName().toLowerCase().contains(q)) {
                listModel.addElement(c);
            }
        }
    }
}
