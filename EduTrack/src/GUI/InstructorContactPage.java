package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InstructorContactPage extends InstructorHomePage implements ActionListener {
    private final JLabel titleLabel;
    private final JPanel titlePanel;
    private JComboBox<String> studentDropdown;
    private JTextArea messageTextArea;
    private JButton submitButton;

    InstructorContactPage() {
        super("Instructor - Contact");

        Border border = BorderFactory.createEtchedBorder();

        // Title Label
        this.titleLabel = new JLabel("Contact");
        formatLabel(titleLabel);
        titleLabel.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        // Title Panel
        this.titlePanel = new JPanel(new BorderLayout());
        titlePanel.setPreferredSize(new Dimension(0, 80));
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        // Sample student names
        String[] students = retrieveStudentNamesFromDatabase();

        // Create student dropdown
        studentDropdown = new JComboBox<>(students);
        formatComboBox(studentDropdown);

        // Message Text Area
        messageTextArea = new JTextArea();
        JScrollPane messageScrollPane = new JScrollPane(messageTextArea);
        messageScrollPane.setPreferredSize(new Dimension(400, 200));

        // Submit Button
        submitButton = new JButton("Submit");
        formatButton(submitButton);
        submitButton.addActionListener(this);

        // Set up layout
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(titlePanel, BorderLayout.NORTH);

        JPanel dropdownPanel = new JPanel();
        dropdownPanel.add(formatLabel(new JLabel("Select Student:")));
        dropdownPanel.add(studentDropdown);

        topPanel.add(dropdownPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(messageScrollPane, BorderLayout.CENTER);
        add(submitButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private Component formatLabel(JLabel label) {
        label.setFont(new Font("Arial", Font.BOLD, 30));
        label.setForeground(new Color(70, 130, 180));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setAlignmentY(JLabel.CENTER);
        label.setBackground(Color.white);
        label.setOpaque(true);
        return label;
    }

    private JComboBox<String> formatComboBox(JComboBox<String> comboBox) {
        comboBox.setFont(new Font("Arial", Font.PLAIN, 20));
        comboBox.setForeground(new Color(70, 130, 180));
        return comboBox;
    }

    void formatButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setForeground(Color.white);
        button.setBackground(new Color(70, 130, 180));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            // Get selected student
            String selectedStudent = (String) studentDropdown.getSelectedItem();

            // Get the message entered by the instructor
            String instructorMessage = messageTextArea.getText();

            // Store the message in the database with the selected student information
            storeMessage(selectedStudent, instructorMessage);

            // Display a confirmation message or handle database submission result
            JOptionPane.showMessageDialog(this, "Message submitted to " + selectedStudent);
        }
    }

    // Method to establish a connection to the MySQL database
    private Connection establishConnection() throws SQLException {
        String url = "jdbc:mysql://your-database-url";
        String user = "your-username";
        String password = "your-password";
        return DriverManager.getConnection(url, user, password);
    }

    // Method to retrieve student names from the database
    private String[] retrieveStudentNamesFromDatabase() {
        // TODO: Implement the logic to retrieve student names from the database
        // Example: return new String[]{"Student 1", "Student 2", "Student 3"};
        return new String[]{};
    }

    // Method to store the instructor's message in the database
    private void storeMessage(String studentName, String message) {
        try (Connection connection = establishConnection()) {
            String query = "INSERT INTO instructor_messages (student_name, message) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, studentName);
                preparedStatement.setString(2, message);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Handle database error
            JOptionPane.showMessageDialog(this, "Error storing instructor message.");
        }
    }
}