package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InstructorAccountPage extends InstructorHomePage implements ActionListener {
    private JPanel profilePanel;
    private JLabel titleLabel, usernameLabel, passwordLabel;
    private JTextField  userNameField, phoneNumberField;
    private JPasswordField passwordField;
    private JButton editButton;

    InstructorAccountPage() {
        super("EduTrack - Instructor Account Page ");

        Border border = BorderFactory.createEtchedBorder();

        // Title Label
        this.titleLabel = new JLabel("Instructor Information");
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

        addFormField("Old Password", userNameField = new JTextField(20), 0);

        addFormField("Old Password", phoneNumberField = new JTextField(20),1);

        addFormField("New Password", passwordField = new JPasswordField(20), 2);

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
        // Handle the edit button action here
        if (e.getSource() == editButton) {
            // Add your logic for handling the edit button click
            JOptionPane.showMessageDialog(this, "Edited Successfully!");
        }
    }
}
