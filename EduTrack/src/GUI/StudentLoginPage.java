package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentLoginPage extends Page implements ActionListener {
    private JPanel loginPanel, inputPanel, buttonPanel;
    private JLabel usernameLabel, passwordLabel, loginLabel, iconLabel;
    private JTextField usernameField, passwordField;
    private JButton signUpButton, loginButton, backButton;

    StudentLoginPage() {
        super("EduTrack - Student Login", 800, 700, 211, 211, 211);

        // Create and configure the "Login" label
        Border border = BorderFactory.createEtchedBorder();

        this.loginLabel = new JLabel("Student Login");
        loginLabel.setFont(new Font("Arial", Font.BOLD, 40));
        loginLabel.setForeground(new Color(70, 130, 180)); // Set color to a shade of blue
        loginLabel.setHorizontalAlignment(JLabel.CENTER);
        loginLabel.setVerticalAlignment(JLabel.CENTER);
        loginLabel.setBackground(Color.white);
        loginLabel.setOpaque(true);
        loginLabel.setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(10, 10, 10, 10))); // Add padding

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
        configureButton(signUpButton, 0, 4, 10, 0, 0, 0);
        inputPanel.add(signUpButton);
        buttonPanel.add(signUpButton);

        // Login Button
        this.loginButton = new JButton("Login");
        configureButton(loginButton, 2, 5, 10, 10, 0, 0);
        inputPanel.add(loginButton);
        buttonPanel.add(loginButton);

        page.add(loginPanel, BorderLayout.NORTH);
        page.add(inputPanel, BorderLayout.CENTER);
        page.add(buttonPanel, BorderLayout.SOUTH);
        page.setVisible(true);
    }
    private void configureButton(JButton button, int gridx, int gridy, int top, int left, int bottom, int right) {
        button.setFocusable(false);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setForeground(Color.white);
        button.setBackground(new Color(70, 130, 180)); // Set color to a shade of blue
        button.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == signUpButton) {
            try {
                new StudentSignUpPage();
                page.dispose(); // Close current window after opening new page
            } catch (Exception ex) {
                // Handle error opening StudentLoginPage
                JOptionPane.showMessageDialog(this, "Error opening Student Login: " + ex.getMessage());
            }
        }
        else if(e.getSource() == loginButton){
            String username = usernameField.getText();
            String password = passwordField.getText();

            if(areNotNull(username, password)) {
                try {
                    new StudentHomePage();
                    page.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error opening Student Homepage" + ex.getMessage());
                }
            }
            else {
                JOptionPane.showMessageDialog(this, "Missing Input!");
            }
        }
    }
}



