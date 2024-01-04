package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class InstructorVerificationPage extends Page implements ActionListener {
    private final JLabel verificationLabel;
    private final JPanel verificationPanel;
    private final JTextField verificationField;
    private final JButton submitButton;

    InstructorVerificationPage() {
        super("Verification Page", 800, 700, 211, 211, 211);

        // Create and configure the "Verification" label
        Border border = BorderFactory.createEtchedBorder();

        this.verificationLabel = new JLabel("Student Sign Up");
        verificationLabel.setFont(new Font("Arial", Font.BOLD, 40));
        verificationLabel.setForeground(new Color(70, 130, 180)); // Set color to a shade of blue
        verificationLabel.setHorizontalAlignment(JLabel.CENTER);
        verificationLabel.setVerticalAlignment(JLabel.CENTER);
        verificationLabel.setBackground(Color.white);
        verificationLabel.setOpaque(true);
        verificationLabel.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10))); // Add padding

        // Create a panel for the "Verification" label and center it at the top
        this.verificationPanel = new JPanel(new BorderLayout());
        verificationPanel.setPreferredSize(new Dimension(0, 120));
        verificationPanel.add(verificationLabel, BorderLayout.CENTER);

        inputPanel = new JPanel(new GridBagLayout());

        addFormField("Verification Number", verificationField = new JTextField(20), 4);

        this.constraints = new GridBagConstraints();

        // Submit Button
        this.submitButton = new JButton("Submit");
        configureButton(submitButton,1,5,10,0,10,0);
        inputPanel.add(submitButton, constraints);

        page.add(verificationPanel, BorderLayout.NORTH);
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
        if (e.getSource() == submitButton) {
            try {
                new InstructorHomePage();
                page.dispose();
                JOptionPane.showMessageDialog(this.page, "Verification successful!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Verification Failed" + ex.getMessage());
            }
        }
    }
}
