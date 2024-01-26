package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class StudentAccountPage extends StudentHomePage implements ActionListener {
    private JPanel profilePanel;
    private JLabel titleLabel, usernameLabel, passwordLabel;
    private JTextField old_PasswordField, new_PasswordField;
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

        addFormField("Old Password", old_PasswordField = new JPasswordField(20), "Enter your old password",0);
        addFormField("New Password", new_PasswordField = new JPasswordField(20), "Enter your new password",1);

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
    @Override
    public void actionPerformed(ActionEvent e) {
        List<String> validationErrors = null;
        if (e.getSource() == editButton) {
            String old_Password = old_PasswordField.getText();
            String new_Password = new_PasswordField.getText();

            validationErrors = isValidSignUpInput(new_Password);

            if (validationErrors.isEmpty())
                try {
                    try {
                        Socket socket = new Socket(ip, 500);
                        OutputStream out = socket.getOutputStream();
                        InputStream in = socket.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                        // Send data in a structured format (e.g., JSON or CSV)
                        String data = String.format("%s,%s\n", old_Password, new_Password);
                        out.write(data.getBytes());

                        String response = reader.readLine();
                        System.out.println(response);

                        if (response.equals("Updated")) {
                            JOptionPane.showMessageDialog(this, "Account edited successfully!");
                        } else {
                            JOptionPane.showMessageDialog(this, "Account has not been edited", "Warning", JOptionPane.WARNING_MESSAGE);
                        }
                        out.close();
                        in.close();
                        socket.close();

                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "An error has occurred. Please try again\n" + ex.getMessage(),
                                "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (HeadlessException ex) {
                    throw new RuntimeException(ex);
                }
        } else {
            String errorMessage = "Not Valid Inputs:\n";
            for (String error : validationErrors) {
                errorMessage += "- " + error + "\n";
            }
            JOptionPane.showMessageDialog(null, errorMessage);
        }
    }
    private java.util.List<String> isValidSignUpInput(String new_Password){
        java.util.List<String> errors = new ArrayList<>();

        validateField("Password", new_Password, "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,20}$", "8-20 characters, with at least one uppercase letter, one lowercase letter, and one digit", errors);

        return errors;
    }
    private void validateField(String fieldName, String value, String regex, String errorMessage, List<String> errors) {
        if (!value.matches(regex)) {
            errors.add(fieldName + " must be " + errorMessage);
        }
    }
}
