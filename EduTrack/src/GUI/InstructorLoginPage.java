package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InstructorLoginPage extends Page implements ActionListener {
    private JPanel loginPanel, inputPanel, buttonPanel;
    private JLabel usernameLabel, passwordLabel, loginLabel;
    private JTextField usernameField, passwordField;
    private JButton signUpButton, loginButton;

    InstructorLoginPage() {
        super("EduTrack - Instructor Login", 800, 700, 211, 211, 211);

        // Create and configure the "Login" label
        Border border = BorderFactory.createEtchedBorder();

        this.loginLabel = new JLabel("Instructor Login");
        loginLabel.setFont(new Font("Arial", Font.BOLD, 40));
        loginLabel.setForeground(new Color(70, 130, 180)); // Set color to a shade of blue
        loginLabel.setHorizontalAlignment(JLabel.CENTER);
        loginLabel.setVerticalAlignment(JLabel.CENTER);
        loginLabel.setBackground(Color.white);
        loginLabel.setOpaque(true);
        loginLabel.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10))); // Add padding

        // Create a panel for the "Login" label and center it at the top
        this.loginPanel = new JPanel(new BorderLayout());
        loginPanel.setPreferredSize(new Dimension(0, 120)); // Increased height for a more prominent look
        loginPanel.add(loginLabel, BorderLayout.CENTER);

        this.inputPanel = new JPanel(new GridBagLayout());

        // Username Label and TextField
        this.usernameLabel = new JLabel("Username:");
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridy = 0;
        constraints.gridx = 0;
        constraints.insets = new Insets(0, 0, 5, 10); // Add spacing to the right
        inputPanel.add(usernameLabel, constraints);

        this.usernameField = new JTextField(20);
        constraints = new GridBagConstraints();
        constraints.gridy = 0;
        constraints.gridx = 1;
        constraints.insets = new Insets(0, 0, 5, 0); // Add spacing to the left
        inputPanel.add(usernameField, constraints);

        // Password Label and TextField
        this.passwordLabel = new JLabel("Password:");
        constraints = new GridBagConstraints();
        constraints.gridy = 1;
        constraints.gridx = 0;
        constraints.insets = new Insets(5, 0, 5, 10); // Add spacing to the right
        inputPanel.add(passwordLabel, constraints);

        this.passwordField = new JPasswordField(20);
        constraints = new GridBagConstraints();
        constraints.gridy = 1;
        constraints.gridx = 1;
        constraints.insets = new Insets(5, 0, 5, 0); // Add spacing to the left
        inputPanel.add(passwordField, constraints);

        this.buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // Sign Up Button
        this.signUpButton = new JButton("Sign Up");
        configureButton(signUpButton, 10, 0);
        buttonPanel.add(signUpButton);

        // Login Button
        this.loginButton = new JButton("Login");
        configureButton(loginButton, 10, 0);
        buttonPanel.add(loginButton);

        frame.add(loginPanel, BorderLayout.NORTH);
        frame.add(inputPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH); // Add button panel below the input panel
        frame.setVisible(true);
    }

    private void configureButton(JButton button, int right, int bottom) {
        button.setFocusable(false);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setForeground(Color.white);
        button.setBackground(new Color(70, 130, 180)); // Set color to a shade of blue
        button.addActionListener(this);

        buttonPanel.add(button);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == signUpButton) {
            try {
                new InstructorSignUpPage();
                frame.dispose(); // Close current window after opening new page
            } catch (Exception ex) {
                // Handle error opening StudentLoginPage
                JOptionPane.showMessageDialog(this, "Error opening Student Login: " + ex.getMessage());
            }
        }
    }
}




