package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentSignUpPage extends Page implements ActionListener {
    private JLabel signUpLabel;
    private JTextField usernameField, idNumberField, passwordField;
    private JPanel signUpPanel, inputPanel;
    private JButton signUpButton;


    StudentSignUpPage() {

        super("EduTrack - Student Sign Up", 800, 700, 211, 211, 211);

        // Create and configure the "Sign Up" label
        Border border = BorderFactory.createEtchedBorder();

        this.signUpLabel = new JLabel("Student Sign Up");
        signUpLabel.setFont(new Font("Arial", Font.BOLD, 40));
        signUpLabel.setForeground(new Color(70, 130, 180)); // Set color to a shade of blue
        signUpLabel.setHorizontalAlignment(JLabel.CENTER);
        signUpLabel.setVerticalAlignment(JLabel.CENTER);
        signUpLabel.setBackground(Color.white);
        signUpLabel.setOpaque(true);
        signUpLabel.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10))); // Add padding
        // Create a panel for the "Sign Up" label and center it at the top
        this.signUpPanel = new JPanel(new BorderLayout());
        signUpPanel.setPreferredSize(new Dimension(0,120));
        signUpPanel.add(signUpLabel, BorderLayout.CENTER);

        this.inputPanel = new JPanel(new GridBagLayout());

        // Name Label and TextField
        addFormField("Username", usernameField = new JTextField(20), 0);

        // Department Label and TextField
        addFormField("ID Number", idNumberField = new JTextField(20), 1);

        // Course Label and TextField
        addFormField("Password", passwordField = new JPasswordField(20),2);

        GridBagConstraints constraints = new GridBagConstraints();

        // Sign Up Button
        this.signUpButton = new JButton("Sign Up");
        signUpButton.setFocusable(false);
        signUpButton.setFocusable(false);
        signUpButton.setFont(new Font("Arial", Font.BOLD, 18));
        signUpButton.setForeground(Color.white);
        signUpButton.setBackground(new Color(70, 130, 180)); // Set color to a shade of blue
        signUpButton.addActionListener(this);
        constraints.gridy = 5;
        constraints.gridx = 1;
        constraints.insets = new Insets(10, 0, 10, 0); // Add some spacing below the last text field
        inputPanel.add(signUpButton, constraints);

        frame.add(signUpPanel, BorderLayout.NORTH);
        frame.add(inputPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
    private void addFormField(String labelText, JComponent field, int gridY) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridy = gridY;
        constraints.gridx = 0;
        constraints.insets = new Insets(5, 0, 5, 10); // Add spacing to the right

        JLabel label = new JLabel(labelText);
        inputPanel.add(label, constraints);

        constraints.gridx = 1;
        constraints.insets = new Insets(5, 0, 5, 0); // Add spacing to the left
        inputPanel.add(field, constraints);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == signUpButton) {

        }
    }
}





