package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InstructorAnnouncementPage extends InstructorHomePage implements ActionListener {
    private JTextArea announcementTextArea;
    private JLabel titleLabel;
    private JPanel titlePanel;
    private JScrollPane announcementScrollPane;
    private JButton submitButton;

    public InstructorAnnouncementPage() {
        super("Instructor Announcement");
        initializeComponents();
        addComponentsToFrame();
        setVisible(true);
    }

    private void initializeComponents() {
        // Announcement Text Area
        announcementTextArea = new JTextArea();
        announcementScrollPane = new JScrollPane(announcementTextArea);
        announcementScrollPane.setPreferredSize(new Dimension(400, 200));

        // Submit Button
        submitButton = new JButton("Submit");
        formatButton(submitButton);
        submitButton.addActionListener(this);

        // Title Label
        titleLabel = new JLabel("Announcement");
        formatLabel(titleLabel);

        // Title Panel
        titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(titleLabel, BorderLayout.CENTER);
    }

    private void addComponentsToFrame() {
        Container contentPane = getContentPane();
        contentPane.add(titlePanel, BorderLayout.NORTH);
        contentPane.add(announcementScrollPane, BorderLayout.CENTER);
        contentPane.add(submitButton, BorderLayout.SOUTH);
    }
    protected Component formatLabel(JLabel label) {
        label.setFont(new Font("Arial", Font.BOLD, 30));
        label.setForeground(new Color(70, 130, 180));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setAlignmentY(JLabel.CENTER);
        label.setBackground(Color.white);
        label.setOpaque(true);
        return label;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);

        if (e.getSource() == submitButton) {
            String instructorAnnouncement = announcementTextArea.getText();
            storeAnnouncement(instructorAnnouncement);
        }
    }

    private void storeAnnouncement(String announcement) {
        try (Connection connection = establishConnection()) {
            String query = "INSERT INTO announcements (announcement_text) VALUES (?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, announcement);
                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(this, "Announcement submitted successfully.");
            }
        } catch (SQLException ex) {
            handleDatabaseError(ex);
        }
    }

    private Connection establishConnection() {
        try {
            String url = "jdbc:mysql://your-database-url";
            String user = "your-username";
            String password = "your-password";
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            handleConnectionError(e);
            return null;
        }
    }

    private void handleDatabaseError(SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error storing announcement.");
    }

    private void handleConnectionError(SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error establishing database connection.");
    }
}




