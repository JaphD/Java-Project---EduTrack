package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class InstructorSignUpPage extends Page implements ActionListener {
    private final JLabel signUpLabel;
    private final JPanel imagePanel;
    private JTextField firstNameField, lastNameField, userNameField, departmentField, courseField, emailField, passwordField, securityKeyField;
    private JPanel signUpPanel;
    private JButton signUpButton;
    InstructorSignUpPage() {
        super("EduTrack - Instructor Sign Up", 800, 700, 211, 211, 211);

        // Create and configure the "Sign Up" label
        Border border = BorderFactory.createEtchedBorder();

        this.signUpLabel = new JLabel("Instructor Sign Up");
        signUpLabel.setFont(new Font("Arial", Font.BOLD, 40));
        signUpLabel.setForeground(new Color(70, 130, 180)); // Set color to a shade of blue
        signUpLabel.setHorizontalAlignment(JLabel.CENTER);
        signUpLabel.setVerticalAlignment(JLabel.CENTER);
        signUpLabel.setBackground(Color.white);
        signUpLabel.setOpaque(true);
        signUpLabel.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10))); // Add padding

        // Create a panel for the "Sign Up" label and center it at the top
        this.signUpPanel = new JPanel(new BorderLayout());
        signUpPanel.setPreferredSize(new Dimension(0, 120)); // Increased height for a more prominent look
        signUpPanel.add(signUpLabel, BorderLayout.CENTER);

        this.imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(imageLabel,BorderLayout.CENTER);

        // New Panel for login and image labels in the north
        JPanel northPanel = new JPanel(new BorderLayout());

        // Add login panel to the north panel
        northPanel.add(signUpPanel, BorderLayout.NORTH);
        northPanel.add(imagePanel,BorderLayout.SOUTH);

        inputPanel = new JPanel(new GridBagLayout());

        // Name Label and TextField
        addFormField("First name", firstNameField = new JTextField(20),0);
        addFormField("Last name", lastNameField = new JTextField(20),1);
        addFormField("Username", userNameField = new JTextField(20), 2);
        addFormField("Department", departmentField = new JTextField(20), 3);
        addFormField("Course", courseField = new JTextField(20), 4);
        addFormField("Email", emailField = new JTextField(20), 5);
        addFormField("Password", passwordField = new JPasswordField(20), 6);

        this.constraints = new GridBagConstraints();

        // Sign Up Button
        this.signUpButton = new JButton("Sign Up");
        configureButton(signUpButton, 1,8,10,0,10,0);
        inputPanel.add(signUpButton, constraints);

        page.add(northPanel, BorderLayout.NORTH);
        page.add(inputPanel, BorderLayout.CENTER);
        page.setVisible(true);
    }
    private void configureButton(JButton button, int gridx, int gridy, int top, int left, int bottom, int right) {
        button.setFocusable(false);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setForeground(Color.white);
        button.setBackground(new Color(70, 130, 180)); // Set color to a shade of blue
        button.addActionListener(this);
        constraints.gridy = gridy;
        constraints.gridx = gridx;
        constraints.insets = new Insets(top,left, bottom, right); // Add some spacing below the last text field
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == signUpButton) {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String username = userNameField.getText();
            String department = departmentField.getText();
            String course = courseField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();

            if (isValidSignUpInput(firstName, lastName, username, department, course, email, password)) {
                try {
                    try {
                        Socket socket = new Socket(ip, 456); // Replace with server address and port
                        OutputStream out = socket.getOutputStream();
                        InputStream in = socket.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));


                        // Send data in a structured format (e.g., CSV)
                        String data = String.format("%s,%s,%s,%s,%s,%s,%s\n", firstName, lastName, username, department, course, email, password);
                        out.write(data.getBytes());

                        String response = reader.readLine();

                        if ("Success".equals(response)) {
                            new InstructorVerificationPage();
                            JOptionPane.showMessageDialog(this, "Sign Up Successful!");
                        } else {
                            JOptionPane.showMessageDialog(this, "Sign up failed try again it ");
                        }
                        out.close();
                        in.close();
                        socket.close();

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "An error has occurred please try again" + ex.getMessage());
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error signing up" + ex.getMessage());
                }
            }
            else{
                    JOptionPane.showMessageDialog(null, "Not Valid Inputs!");
                }
            }
        }
    private boolean isValidSignUpInput(String firstname, String lastname, String username, String department, String course, String email, String password){
        String firstNameRegex = "^[a-zA-Z]{3,20}$";
        String lastNameRegex = "^[a-zA-Z]{3,20}$";
        String usernameRegex = "^[a-zA-Z0-9]{3,20}$";
        String departmentRegex = "^[a-zA-Z ]{7,60}$";
        String courseRegex = "^[a-zA-Z ]{7,60}$";
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,20}$";

        return (firstname.matches(firstNameRegex) && lastname.matches(lastNameRegex) &&
                username.matches(usernameRegex) && department.matches(departmentRegex) &&
                course.matches(courseRegex) && email.matches(emailRegex) &&
                password.matches(passwordRegex));
    }
}

