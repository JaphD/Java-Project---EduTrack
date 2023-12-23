package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class LoginPage extends Page implements ActionListener {
    private JPanel loginPanel, centerPanel;
    private JLabel loginLabel;
    private JButton studentButton, instructorButton;

    LoginPage() {
        super("EduTrack-Login", 650, 550, 211, 211, 211);

        // Create and configure the "Login" label
        Border border = BorderFactory.createEtchedBorder();
        // Create login label and giving it the desired format
        this.loginLabel = new JLabel("Login");
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

        // Create buttons for "Student" and "Instructor"
        // Student JButton
        studentButton = new JButton("Student");
        configureButton(studentButton);

        // Instructor JButton
        instructorButton = new JButton("Instructor");
        configureButton(instructorButton);

        // Create a panel for the buttons and add spacing with an empty border
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Add spacing
        buttonPanel.add(studentButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Add spacing
        buttonPanel.add(instructorButton);

        // Create a container panel for centering the buttonPanel
        this.centerPanel = new JPanel(new GridBagLayout());
        centerPanel.add(buttonPanel);

        // Add the panels to the main frame
        frame.add(loginPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
    private void configureButton(JButton button) {
        button.setFocusable(false);
        button.setFont(new Font("Arial", Font.BOLD, 25));
        button.setForeground(Color.white);
        button.setBackground(new Color(70, 130, 180)); // Set color to a shade of blue
        button.addActionListener(this);
        button.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button
    }
    @Override
        public void actionPerformed(ActionEvent e) {
        if (e.getSource() == studentButton) {
            try {
                new StudentLoginPage();
                frame.dispose(); // Close current window after opening new page
            } catch (Exception ex) {
                // Handle error opening StudentLoginPage
                JOptionPane.showMessageDialog(this, "Error opening Student Login: " + ex.getMessage());
            }
        } else if (e.getSource() == instructorButton) {
            try {
                new InstructorLoginPage();
                frame.dispose();
            } catch(Exception ex){
                // Add functionality for instructor button click (e.g., display message or open instructor login)
                JOptionPane.showMessageDialog(this, "Instructor login not yet implemented.");
            }
        }
    }
}












