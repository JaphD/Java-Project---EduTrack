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

public class InstructorLoginPage extends Page implements ActionListener {

    private final JPasswordField securityKeyField;
    private final JPanel loginPanel, imagePanel, buttonPanel;
    private final JLabel loginLabel;
    private final JTextField usernameField, passwordField;
    private final JButton signUpButton, loginButton;
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

        this.imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(imageLabel,BorderLayout.CENTER);

        // New Panel for login and image labels in the north
        JPanel northPanel = new JPanel(new BorderLayout());

        // Add login panel to the north panel
        northPanel.add(loginPanel, BorderLayout.NORTH);
        northPanel.add(imagePanel,BorderLayout.SOUTH);

        inputPanel = new JPanel(new GridBagLayout());

        addFormField("Username", usernameField = new JTextField(20),0);
        addFormField("Password", passwordField = new JTextField(20),1);
        addFormField("Security Key", securityKeyField = new JPasswordField(20),2);

        this.constraints = new GridBagConstraints();


        this.buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // Sign Up Button
        this.signUpButton = new JButton("Sign Up");
        configureButton(signUpButton, 1,2,10,0,10,0);
        buttonPanel.add(signUpButton);

        // Login Button
        this.loginButton = new JButton("Login");
        configureButton(loginButton, 2,2,10,10,0,0);
        buttonPanel.add(loginButton);

        page.add(northPanel, BorderLayout.NORTH);
        page.add(inputPanel, BorderLayout.CENTER);
        page.add(buttonPanel, BorderLayout.SOUTH); // Add button panel below the input panel
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
            try {
                new InstructorSignUpPage();
                page.dispose(); // Close current window after opening new page
            } catch (Exception ex) {
                // Handle error opening StudentLoginPage
                JOptionPane.showMessageDialog(this, "Error opening Student Login: " + ex.getMessage());
            }
        }
        else if(e.getSource() == loginButton){
            String username = usernameField.getText();
            String password = passwordField.getText();
            String securityKey = securityKeyField.getText();

            if(areNotNull(username, password, securityKey)) {
                try {
                    // Send credentials to server (example using a Socket):
                    try {
                        Socket socket = new Socket(ip, 500); // Replace with server address and port
                        OutputStream out = socket.getOutputStream();

                        // Send data in a structured format (e.g., CSV)
                        String data = String.format("%s,%s,%s\n", username, password, securityKey);
                        out.write(data.getBytes());

                        InputStream in = socket.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                        String response = reader.readLine();

                        if (response.equals("Success")) {
                            new InstructorHomePage();
                            page.dispose();
                        } else {
                            JOptionPane.showMessageDialog(this, "Login failed. Please check your credentials.");
                        }

                        out.close();
                        in.close();
                        socket.close();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "An error has occurred please try again" + ex.getMessage());
                    }
                }
                catch(Exception ex){
                    JOptionPane.showMessageDialog(this, "Error opening Instructor Homepage" + ex.getMessage());
                }
            }
            else {
                JOptionPane.showMessageDialog(this, "Missing Input!");
            }
        }
    }
}




