package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class StudentVerificationPage extends StudentSignUpPage implements ActionListener {
    private final JLabel verificationLabel;
    private final JPanel verificationPanel;
    private final JTextField verificationField;
    private final JButton submitButton;

    StudentVerificationPage() {
        super("Student Verification Page");

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

        addFormField("Verification Number", verificationField = new JTextField(20),"Input your verification number",4);

        this.constraints = new GridBagConstraints();

        // Submit Button
        this.submitButton = new JButton("Submit");
        configureButton(submitButton,1,5,10,0,10,0);
        inputPanel.add(submitButton, constraints);

        page.add(verificationPanel, BorderLayout.NORTH);
        page.add(inputPanel, BorderLayout.CENTER);
        page.setVisible(true);
    }
    private String getEmailFromFile() {
        try (DataInputStream dis = new DataInputStream(new FileInputStream("email.txt"))) {
            email = dis.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(email);
        return email;
    }
    String savedEmail = getEmailFromFile();

    private void configureButton(JButton button, int gridx, int gridy, int top, int left, int bottom, int right) {
        button.setFocusable(false);
        button.setFont(new Font("Arial", Font.BOLD, 25));
        button.setForeground(Color.white);
        button.setBackground(new Color(70, 130, 180)); // Set color to a shade of blue
        button.addActionListener(this);
        constraints.gridy = gridy;
        constraints.gridx = gridx;
        constraints.insets = new Insets(top,left, bottom, right); // Add some spacing below the last text field
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String verificationNumber=verificationField.getText();
        System.out.println("the email value"+ savedEmail);
        String send="Correct";
        if (e.getSource() == submitButton) {
            try (Socket socket = new Socket(ip, 358);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 OutputStream out = socket.getOutputStream()) {
                String data2 = String.format("%s,%s,%s\n",savedEmail,verificationNumber,send);
                out.write(data2.getBytes());
                String responseVR= in.readLine();// Print server response
                System.out.println(responseVR);
                if(("23").equals(responseVR)){
                    JOptionPane.showMessageDialog(this, "Sign up successful");
                    new StudentLoginPage();
                    page.dispose();
                }
                else{
                    JOptionPane.showMessageDialog(this, "Invalid verification number" );
                }
            } catch (IOException a) {
                a.printStackTrace();
            }
        }
    }
}
