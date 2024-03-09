package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class StudentLoginPage extends Page implements ActionListener {
    private final JPanel loginPanel, buttonPanel, imagePanel;
    private final JLabel loginLabel;
    private final JTextField usernameField, passwordField;
    private final JButton signUpButton, loginButton;

    StudentLoginPage() {
        super("EduTrack - Student Login", true, 800, 700, 211,211, 211);

        // Create and configure the "Login" label
        Border border = BorderFactory.createEtchedBorder();

        loginLabel = new JLabel("Student Login");
        loginLabel.setFont(new Font("Arial", Font.BOLD, 40));
        loginLabel.setForeground(new Color(70, 130, 180)); // Set color to a shade of blue
        loginLabel.setHorizontalAlignment(JLabel.CENTER);
        loginLabel.setVerticalAlignment(JLabel.CENTER);
        loginLabel.setBackground(Color.white);
        loginLabel.setOpaque(true);
        loginLabel.setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(10, 10, 10, 10))); // Add padding

        // Create a panel for the "Login" label and center it at the top
        loginPanel = new JPanel(new BorderLayout());
        loginPanel.setPreferredSize(new Dimension(0, 120)); // Increased height for a more prominent look
        loginPanel.add(loginLabel, BorderLayout.CENTER);

        imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(imageLabel,BorderLayout.CENTER);

        inputPanel = new JPanel(new GridBagLayout());

        addFormField("Username", usernameField = new JTextField(20), "Input your username",0);
        addFormField("Password", passwordField = new JPasswordField(20), "Input your password",1);

        // New Panel for login and image labels in the north
        JPanel centerPanel = new JPanel(new BorderLayout());

        // Add login panel to the north panel
        centerPanel.add(imagePanel, BorderLayout.NORTH);
        centerPanel.add(inputPanel,BorderLayout.CENTER);

        buttonPanel = new JPanel(new GridBagLayout());
        constraints = new GridBagConstraints();

        // Login Button
        loginButton = new JButton("Login");
        configureButton(loginButton, 1, 5, 10, 10, 0, 0);
        inputPanel.add(loginButton, constraints);

        // Sign Up Button
        signUpButton = new JButton("Sign Up");
        configureButton(signUpButton, 1, 6, 10, 0, 10, 0);
        inputPanel.add(signUpButton,constraints);

        page.add(loginPanel,BorderLayout.NORTH);
        page.add(centerPanel, BorderLayout.CENTER);
       // page.add(buttonPanel, BorderLayout.SOUTH);
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
                new StudentSignUpPage();
                page.dispose();
            } catch (Exception ex) {
                handleException("Error opening Student Sign Up", ex);
            }
        }
        else if(e.getSource() == loginButton){
            String username = usernameField.getText();
            String password = passwordField.getText();

            // Set the cursor to wait mode
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

            if(areNotNull(username, password)) {
                try {
                    Socket socket = new Socket(ip, 100);
                    OutputStream out = socket.getOutputStream();

                    // Send data in a structured format
                    String data = String.format("%s,%s\n",username,password);
                    out.write(data.getBytes());

                    InputStream in = socket.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    String response = reader.readLine();

                    if ("2".equals(response)) {
                        new StudentHomePage();
                        page.dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Login Failed. Please check your credentials");
                    }
                    in.close();
                    socket.close();
                    out.close();
                } catch (IOException ex) {
                handleException("Error communicating with server", ex);
                } finally {
                setCursor(Cursor.getDefaultCursor());
                }
            }
            else {
                JOptionPane.showMessageDialog(this, "Missing Input!");
            }
        }
    }
}



