package GUI;

import javax.swing.*;
import java.awt.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentHomePage extends Page {
    private JMenuBar studentMenuBar;

    public StudentHomePage() {
        super("EduTrack - Student Homepage",211, 211, 211);
        this.studentMenuBar = new JMenuBar();

        JMenu homeMenu = new JMenu("Home");
        JMenu menuMenu = new JMenu("Menu");
        JMenu logoutMenu = new JMenu("Logout");

        JMenuItem profileItem = new JMenuItem("Profile");
        JMenuItem assessmentItem = new JMenuItem("Assessment");
        JMenuItem attendanceItem = new JMenuItem("Attendance");
        JMenuItem quizRoomItem = new JMenuItem("Quiz Room");
        JMenuItem contactItem = new JMenuItem("Contact");

        formatMenuItem(profileItem);
        formatMenuItem(assessmentItem);
        formatMenuItem(attendanceItem);
        formatMenuItem(quizRoomItem);
        formatMenuItem(contactItem);

        menuMenu.add(profileItem);
        menuMenu.add(assessmentItem);
        menuMenu.add(attendanceItem);
        menuMenu.add(quizRoomItem);
        menuMenu.add(contactItem);

        studentMenuBar.add(homeMenu);
        studentMenuBar.add(menuMenu);
        studentMenuBar.add(logoutMenu);

        // Create panels for each button and set layout
        JPanel announcementPanel = createPanel("Announcement");
        JPanel schedulePanel = createPanel("Schedule");
        JPanel materialPanel = createPanel("Course Material");
        JPanel assignmentPanel = createPanel("Assignment");

        // Add panels to the main layout
        this.setLayout(new GridLayout(2, 2, 20, 20)); // Adjusted grid layout with reduced spacing
        this.add(announcementPanel);
        this.add(schedulePanel);
        this.add(materialPanel);
        this.add(assignmentPanel);

        ImageIcon icon = new ImageIcon("C:\\Users\\Japhe\\OneDrive\\Desktop\\Java\\EduTrack\\src\\GUI\\icon2.jpeg");
        this.setIconImage(icon.getImage());

        this.setJMenuBar(studentMenuBar);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    private JPanel createPanel(String buttonText) {
        JPanel panel = new JPanel(new GridBagLayout());

        // Add buttons to panels with appropriate constraints
        JButton button = new JButton(buttonText);
        formatButton(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle button click event
                JOptionPane.showMessageDialog(null, "Button clicked: " + buttonText);
            }
        });

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(10, 10, 10, 10); // Add padding around buttons

        panel.add(button, constraints);

        return panel;
    }
    private void formatButton(JButton button) {
        button.setFocusable(false);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setForeground(Color.white);
        button.setBackground(new Color(70, 130, 180)); // Set color to a shade of blue
    }
    private void formatMenuItem(JMenuItem menuItem) {
        menuItem.setFont(new Font("Arial", Font.PLAIN, 16));
        menuItem.setForeground(new Color(70, 130, 180));
    }
}

