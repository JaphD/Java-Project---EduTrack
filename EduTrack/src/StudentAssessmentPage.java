package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class StudentAssessmentPage extends StudentHomePage implements ActionListener {
    private final JLabel titleLabel;
    private final JPanel titlePanel;
    private JTextField idField;

    public StudentAssessmentPage() {
        super("EduTrack - Student Assessment");
        Border border = BorderFactory.createEtchedBorder();

        // Title Label
        this.titleLabel = new JLabel("Assessment");
        formatLabel(titleLabel);
        titleLabel.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        // Title Panel
        this.titlePanel = new JPanel(new BorderLayout());
        titlePanel.setPreferredSize(new Dimension(0, 80));
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        idField = new JTextField(10);
        JButton getGradeButton = new JButton("Get Grade");
        getGradeButton.addActionListener(this);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Enter your password: "));
        panel.add(idField);
        panel.add(getGradeButton);

        // Set up layout
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(titlePanel, BorderLayout.NORTH);
        add(topPanel, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600,500);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Get Grade")) {
            String studentId = idField.getText();

            try (Socket socket = new Socket(ip, 350);
                 ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

                // Sends request to the server for the grade of a specific student
                out.writeObject("RequestStudentGrade");
                out.writeObject(studentId);

                // Receives and display the response from the server using JOptionPane
                Object response = in.readObject();

                if (response instanceof String) {
                    String gradeResult = (String) response;
                    JOptionPane.showMessageDialog(null, "Your Grade Result: " + gradeResult);
                } else {
                    JOptionPane.showMessageDialog(null, "Unexpected response from server");
                }

            }
            catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Network error occurred. Please check your internet connection and try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "Unexpected response from server. Please contact support for assistance.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    /*
    private JLabel formatLabel(JLabel label) {
        label.setFont(new Font("Arial", Font.BOLD, 30));
        label.setForeground(new Color(70, 130, 180));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setAlignmentY(JLabel.CENTER);
        label.setBackground(Color.white);
        label.setOpaque(true);
        return label;
    }
     */
}
