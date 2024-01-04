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

public class StudentAccountPage extends StudentHomePage implements ActionListener {
    private JPanel profilePanel;
    private JLabel titleLabel, usernameLabel, passwordLabel;
    private JTextField userNameField;
    private JPasswordField passwordField;
    private JButton editButton;

    StudentAccountPage() {
        super("EduTrack - Student Account Page");

        Border border = BorderFactory.createEtchedBorder();

        // Title Label
        this.titleLabel = new JLabel("Account Information");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        titleLabel.setForeground(new Color(70, 130, 180));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setAlignmentY(JLabel.CENTER);
        titleLabel.setBackground(Color.white);
        titleLabel.setOpaque(true);
        titleLabel.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10))); // Add padding

        // Profile Panel
        this.profilePanel = new JPanel(new BorderLayout());
        profilePanel.setPreferredSize(new Dimension(0, 120));
        profilePanel.add(titleLabel, BorderLayout.CENTER);

        inputPanel = new JPanel(new GridBagLayout());

        // Load data from the database
        loadDataFromDatabase();

        addFormField("Old Password", userNameField = new JTextField(20), 0);
        addFormField("Old Password", passwordField = new JPasswordField(20), 1);
        addFormField("New Password", passwordField = new JPasswordField(20), 2);

        GridBagConstraints constraints = new GridBagConstraints();

        // Edit Button
        this.editButton = new JButton("Edit");
        editButton.setFocusable(false);
        editButton.setFont(new Font("Arial", Font.BOLD, 18));
        editButton.setForeground(Color.white);
        editButton.setBackground(new Color(70, 130, 180));
        editButton.addActionListener(this);
        constraints.gridy = 3;
        constraints.gridx = 1;
        constraints.insets = new Insets(10, 0, 10, 0);
        inputPanel.add(editButton, constraints);

        // Set layout for the main panel
        setLayout(new BorderLayout());
        add(profilePanel, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.CENTER);
    }

    private void loadDataFromDatabase() {
        try {
            // Establish a database connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://your_database_url", "your_username", "your_password");

            // SQL query to retrieve user data (modify as needed)
            String query = "SELECT username, password FROM your_table WHERE user_id = ?";

            // Assuming userId is the unique identifier for the user
            int userId = getUserId(); // You need to implement this method to get the user's ID
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();

            // If a record is found, populate the fields
            if (resultSet.next()) {
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");

                userNameField.setText(username);
                passwordField.setText(password);
            }

            // Close resources
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading data from the database: " + e.getMessage());
        }
    }

    // Implement the getUserId method to return the user's ID
    private int getUserId() {
        // Add logic to get the user's ID (you might pass it as a parameter or retrieve it from a session)
        return 1; // Replace with the actual user ID
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle the edit button action here
        if (e.getSource() == editButton) {
            // Add your logic for handling the edit button click
            JOptionPane.showMessageDialog(this, "Edited Successfully!");
        }
    }
}
