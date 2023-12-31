package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class StudentAttendancePage extends StudentHomePage implements ActionListener {
    private final JLabel titleLabel;
    private final JPanel titlePanel;
    private JButton confirmButton;

    public StudentAttendancePage() {
        super("EduTrack - Attendance");

        Border border = BorderFactory.createEtchedBorder();

        // Title Label
        this.titleLabel = new JLabel("Attendance");
        formatLabel(titleLabel);
        titleLabel.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        // Title Panel
        this.titlePanel = new JPanel(new BorderLayout());
        titlePanel.setPreferredSize(new Dimension(0, 80));
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        // Confirm Button
        confirmButton = new JButton("Submit Attendance");
        formatButton(confirmButton);
        confirmButton.addActionListener(this);

        // Set up layout
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(titlePanel, BorderLayout.NORTH);
        topPanel.add(confirmButton, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == confirmButton) {
            submitAttendanceToDatabase();
        }
    }

    private void submitAttendanceToDatabase() {
        try {
            // Establish a database connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://your_database_url", "your_username", "your_password");

            // SQL query to insert attendance data (modify as needed)
            String query = "INSERT INTO attendance_table (student_id, attendance_date, status) VALUES (?, NOW(), ?)"; // Replace with your actual table name

            // Assuming studentId is the unique identifier for the student
            int studentId = getStudentId(); // You need to implement this method to get the student's ID

            // Assuming status is either "Present" or "Absent"
            String status = "Present"; // Modify based on your attendance logic

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, studentId);
            preparedStatement.setString(2, status);

            // Execute the query
            preparedStatement.executeUpdate();

            // Close resources
            preparedStatement.close();
            connection.close();

            JOptionPane.showMessageDialog(this, "Attendance submitted successfully!");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error submitting attendance: " + ex.getMessage());
        }
    }

    private void formatLabel(JLabel label) {
        label.setFont(new Font("Arial", Font.BOLD, 30));
        label.setForeground(new Color(70, 130, 180));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setAlignmentY(JLabel.CENTER);
        label.setBackground(Color.white);
        label.setOpaque(true);
    }

    // Implement the getStudentId method to return the student's ID
    private int getStudentId() {
        // Add logic to get the student's ID (you might pass it as a parameter or retrieve it from a session)
        return 1; // Replace with the actual student ID
    }
}
