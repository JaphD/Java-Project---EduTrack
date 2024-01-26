package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class InstructorSchedulePage extends InstructorHomePage implements ActionListener {
    private JTextField courseNameField;
    private JTextField dayField;
    private JTextField timeField;
    private JButton submitButton;
    public InstructorSchedulePage() {
        super("Course Schedule");
        initializeComponents();
        addComponentsToFrame();
    }
    private void initializeComponents() {
        courseNameField = new JTextField(8); // Reduced text field size
        dayField = new JTextField(8); // Reduced text field size
        timeField = new JTextField(8); // Reduced text field size
        submitButton = new JButton("Submit");
        formatButton(submitButton);
        submitButton.addActionListener(this);
    }
    private void addComponentsToFrame() {
        // Title Panel
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Schedule");
        formatTitleLabel(titleLabel);
        titlePanel.add(titleLabel);

        // Input Panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Adjusted insets for spacing

        JLabel courseLabel = new JLabel("Course Name:");
        formatLabel(courseLabel);
        JLabel dayLabel = new JLabel("Day:");
        formatLabel(dayLabel);
        JLabel timeLabel = new JLabel("Time:");
        formatLabel(timeLabel);

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(courseLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        inputPanel.add(courseNameField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(dayLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        inputPanel.add(dayField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(timeLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        inputPanel.add(timeField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        inputPanel.add(submitButton, gbc);

        add(titlePanel, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.CENTER);
    }
    private void formatTitleLabel(JLabel label) {
        label.setFont(new Font("Arial", Font.BOLD, 30));
        label.setForeground(new Color(70, 130, 180));
    }
    private void formatLabel(JLabel label) {
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setForeground(new Color(70, 130, 180));
    }
    @Override
    void formatButton(JButton button) {
        button.setFocusable(false);
        button.setFont(new Font("Arial", Font.BOLD, 15));
        button.setForeground(Color.white);
        button.setBackground(new Color(70, 130, 180));
        button.setPreferredSize(new Dimension(100, 30));
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            String courseName = courseNameField.getText();
            String day = dayField.getText();
            String time = timeField.getText();

            if (!courseName.isEmpty() && !day.isEmpty() && !time.isEmpty()) {
                storeCourseSchedule(courseName, day, time);
                JOptionPane.showMessageDialog(this, "Schedule submitted successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Please fill out all fields.");
            }
        }
    }
    private void storeCourseSchedule(String courseName, String day, String time) {
        try (Connection connection = establishConnection()) {
            String query = "INSERT INTO course_schedule (course_name, day, time) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, courseName);
                preparedStatement.setString(2, day);
                preparedStatement.setString(3, time);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error storing course schedule.");
        }
    }
    private Connection establishConnection() throws SQLException {
        String url = "jdbc:mysql://your-database-url";
        String user = "your-username";
        String password = "your-password";
        return DriverManager.getConnection(url, user, password);
    }
}
