package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class InstructorLoginPage extends Page implements ActionListener {
    private final JPasswordField securityKeyField;
    private final JPanel loginPanel, imagePanel, buttonPanel;
    private final JLabel loginLabel;
    private final JTextField usernameField, passwordField;
    private final JButton signUpButton, loginButton;

    InstructorLoginPage() {
        super("EduTrack - Instructor Login", true, 800, 700, 211, 211, 211);

        // Create and configure the "Login" label
        Border border = BorderFactory.createEtchedBorder();

        loginLabel = new JLabel("Instructor Login");
        loginLabel.setFont(new Font("Arial", Font.BOLD, 40));
        loginLabel.setForeground(new Color(70, 130, 180)); // Set color to a shade of blue
        loginLabel.setHorizontalAlignment(JLabel.CENTER);
        loginLabel.setVerticalAlignment(JLabel.CENTER);
        loginLabel.setBackground(Color.white);
        loginLabel.setOpaque(true);
        loginLabel.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10))); // Add padding

        // Create a panel for the "Login" label and center it at the top
        loginPanel = new JPanel(new BorderLayout());
        loginPanel.setPreferredSize(new Dimension(0, 120)); // Increased height for a more prominent look
        loginPanel.add(loginLabel, BorderLayout.CENTER);

        imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(imageLabel, BorderLayout.CENTER);

        inputPanel = new JPanel(new GridBagLayout());

        // New Panel for login and image labels in the north
        //JPanel northPanel = new JPanel(new BorderLayout());

        // Add login panel to the north panel
        //northPanel.add(loginPanel, BorderLayout.NORTH);
        //northPanel.add(imagePanel,BorderLayout.SOUTH);

        addFormField("Username", usernameField = new JTextField(20), "Input your username", 0);
        addFormField("Password", passwordField = new JPasswordField(20), "Input your password", 1);
        addFormField("Security Key", securityKeyField = new JPasswordField(20), "Input your security key", 2);

        // New Panel for login and image labels in the north
        JPanel centerPanel = new JPanel(new BorderLayout());

        // Add login panel to the north panel
        centerPanel.add(imagePanel, BorderLayout.NORTH);
        centerPanel.add(inputPanel, BorderLayout.CENTER);

        buttonPanel = new JPanel(new GridBagLayout());
        constraints = new GridBagConstraints();

        // Login Button
        loginButton = new JButton("Login");
        configureButton(loginButton, 1, 5, 10, 10, 0, 0);
        inputPanel.add(loginButton, constraints);

        // Sign Up Button
        signUpButton = new JButton("Sign Up");
        configureButton(signUpButton, 1, 6, 10, 0, 10, 0);
        inputPanel.add(signUpButton, constraints);

        page.add(loginPanel, BorderLayout.NORTH);
        page.add(centerPanel, BorderLayout.CENTER);
        //page.add(buttonPanel, BorderLayout.SOUTH);
        page.setVisible(true);
    }

    private void configureButton(JButton button, int gridx, int gridy, int top, int left, int bottom, int right) {
        button.setFocusable(false);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setForeground(Color.white);
        button.setBackground(new Color(70, 130, 180)); // Sets color to a shade of blue
        button.addActionListener(this);
        constraints.gridy = gridy;
        constraints.gridx = gridx;
        constraints.insets = new Insets(top, left, bottom, right); // Adds some spacing below the last text field
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == signUpButton) {
            try {
                new InstructorSignUpPage();
                page.dispose();
            } catch (Exception ex) {
                handleException("Error opening Instructor Sign Up", ex);
            }
        } else if (e.getSource() == loginButton) {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String securityKey = securityKeyField.getText();

            // Set the curosr to wait mode
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

            if (areNotNull(username, password, securityKey)) {
                try {
                    Socket socket = new Socket(ip, 100);
                    OutputStream out = socket.getOutputStream();

                    // Sends data in a structured format
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
                } catch (IOException ex) {
                    handleException("Error communicating with server", ex);
                } finally {
                    // Reset the cursor back to normal
                    setCursor(Cursor.getDefaultCursor());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Missing Input!");
            }
        }
    }
}




