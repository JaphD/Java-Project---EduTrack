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

public class StudentSignUpPage extends Page implements ActionListener {
    private JLabel signUpLabel;
    private JTextField firstNameField, lastNameField, userNameField, idNumberField, emailField, passwordField;
    private JPanel signUpPanel, imagePanel;
    private JButton signUpButton;
    protected String email;

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
        signUpPanel.setPreferredSize(new Dimension(0, 120));
        signUpPanel.add(signUpLabel, BorderLayout.CENTER);

        this.imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(imageLabel,BorderLayout.CENTER);

        // New Panel for login and image labels in the north
        JPanel northPanel = new JPanel(new BorderLayout());

        // Add login panel to the north panel
        northPanel.add(signUpPanel, BorderLayout.NORTH);
        northPanel.add(imagePanel,BorderLayout.SOUTH);

        this.inputPanel = new JPanel(new GridBagLayout());

        addFormField("First name", firstNameField = new JTextField(20), "Enter your first name (3-20 characters, only letters)",0);
        addFormField("Last name", lastNameField = new JTextField(20), "Enter your last name (3-20 characters, only letters)",1);
        addFormField("Username", userNameField = new JTextField(20), "Enter your username (3-20 characters, letters and numbers)",2);
        addFormField("ID Number", idNumberField = new JTextField(20), "Enter your ID number (11 characters, letters, numbers, and '/')",3);
        addFormField("Email", emailField = new JTextField(20), "Enter your email (e.g., user@example.com)",4);
        addFormField("Password", passwordField = new JPasswordField(20), "Enter your password (8-20 characters, at least one lowercase letter, one uppercase letter, and one digit)",5);

        this.constraints = new GridBagConstraints();

        // Sign Up Button
        this.signUpButton = new JButton("Sign Up");
        configureButton(signUpButton,1, 6, 10, 0, 10, 0);
        inputPanel.add(signUpButton, constraints);

        page.add(northPanel, BorderLayout.NORTH);
        page.add(inputPanel, BorderLayout.CENTER);
        page.setVisible(true);
    }
    StudentSignUpPage(String title){
        super(title, 800,700,211,211,211);
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
    private void saveEmailToFile(String email) {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream("email.txt"))) {
            dos.writeUTF(email);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == signUpButton) {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String username = userNameField.getText();
            String idNumber = idNumberField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();

            List<String> validationErrors = isValidSignUpInput(firstName, lastName, username, idNumber, email, password);

            if (validationErrors.isEmpty()) {
                try {
                    try {
                        Socket socket = new Socket(ip, 456); // Replace with server address and port
                        OutputStream out = socket.getOutputStream();
                        InputStream in = socket.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                        // Send data in a structured format (e.g., JSON or CSV)
                        String data = String.format("%s,%s,%s,%s,%s,%s\n", firstName, lastName, username, idNumber, email, password);
                        out.write(data.getBytes());

                        String response = reader.readLine();

                        if (response.equals("Success")) {
                            try (Socket socket1 = new Socket(ip, 358);
                                 OutputStream Out = socket1.getOutputStream();
                                 InputStream In = socket1.getInputStream();
                                 BufferedReader Reader = new BufferedReader(new InputStreamReader(In))) {
                                saveEmailToFile(email);

                                String send = "12";
                                String data1 = String.format("%s,%s\n", send, email);
                                Out.write(data1.getBytes());
                                String response3 = Reader.readLine();
                                if ("Sent".equals(response3)) {
                                    JOptionPane.showMessageDialog(this, "The verification code sent to the email!");
                                    new StudentVerificationPage(); // Pass email to verification page
                                    page.dispose();
                                } else {
                                    JOptionPane.showMessageDialog(this, "Error. Verification code not be sent to the email!");
                                }
                            }
                        } else{
                            JOptionPane.showMessageDialog(this, "Sign Up Failed. Please try again.");
                        }
                        out.close();
                        in.close();
                        socket.close();

                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "An error has occurred. Please try again\n" + ex.getMessage(),
                                "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                }
                catch (HeadlessException ex) {
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
    }
    private List<String> isValidSignUpInput(String firstname, String lastname, String username, String idNumber, String email, String password){
        List<String> errors = new ArrayList<>();

        validateField("First Name", firstname, "^[a-zA-Z]{3,20}$", "3-20 characters of letters only", errors);
        validateField("Last Name", lastname, "^[a-zA-Z]{3,20}$", "3-20 characters of letters only", errors);
        validateField("Username", username, "^[a-zA-Z0-9]{3,20}$", "3-20 characters of letters and numbers only", errors);
        validateField("ID Number", idNumber, "^[a-zA-Z0-9/]{11}", "11 characters of letters,digits and forward slash", errors);
        validateField("Email", email, "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$", "Invalid Email Address", errors);
        validateField("Password", password, "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,20}$", "8-20 characters, with at least one uppercase letter, one lowercase letter, and one digit", errors);

        return errors;
    }
    private void validateField(String fieldName, String value, String regex, String errorMessage, List<String> errors) {
        if (!value.matches(regex)) {
            errors.add(fieldName + " must be " + errorMessage);
        }
    }
}





