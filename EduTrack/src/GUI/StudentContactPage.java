package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class StudentContactPage extends StudentHomePage implements ActionListener {
    private final JLabel titleLabel;
    private final JPanel titlePanel;
    private JTextArea messageTextArea;
    private JButton submitButton;

    StudentContactPage() {
        super("Contact");

        Border border = BorderFactory.createEtchedBorder();

        // Title Label
        this.titleLabel = new JLabel("Contact");
        formatLabel(titleLabel);
        titleLabel.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        // Title Panel
        this.titlePanel = new JPanel(new BorderLayout());
        titlePanel.setPreferredSize(new Dimension(0, 80));
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        // Message Text Area
        messageTextArea = new JTextArea();
        formatTextArea(messageTextArea);

        // Submit Button
        submitButton = new JButton("Submit");
        formatButton(submitButton);
        submitButton.addActionListener(this);

        // Set up layout
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(titlePanel, BorderLayout.NORTH);
        topPanel.add(messageTextArea, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Centered FlowLayout
        buttonPanel.add(submitButton);

        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            String message = messageTextArea.getText();
            if (submitMessageToDatabase(message)) {
                JOptionPane.showMessageDialog(this, "Message submitted");
                // You can add further logic to handle the submitted message
            } else {
                JOptionPane.showMessageDialog(this, "Error submitting message. Please try again.");
            }
        }
    }

    private boolean submitMessageToDatabase(String message) {
        try {
            // Establish a database connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://your_database_url", "your_username", "your_password");

            // SQL query to insert message data (modify as needed)
            String query = "INSERT INTO messages (message_text, submission_date) VALUES (?, NOW())"; // Replace with your actual table name

            // Assuming "message_text" is the column in which the message should be stored
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, message);

            // Execute the query and check if any rows were affected
            int rowsAffected = preparedStatement.executeUpdate();

            // Close resources
            preparedStatement.close();
            connection.close();

            // Return true if rows were affected, indicating successful insertion
            return rowsAffected > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error submitting message: " + ex.getMessage());
            return false; // Return false in case of an exception or failed insertion
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

    private void formatTextArea(JTextArea textArea) {
        textArea.setFont(new Font("Arial", Font.PLAIN, 16));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    void formatButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setForeground(Color.white);
        button.setBackground(new Color(70, 130, 180));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(120, 40)); // Set preferred size
    }
}
