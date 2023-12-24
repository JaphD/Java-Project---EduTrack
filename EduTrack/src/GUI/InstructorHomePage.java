package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InstructorHomePage extends Page {
    private JMenuBar instructorMenuBar;

    public InstructorHomePage() {
        super("EduTrack - Instructor Homepage",211, 211, 211);
        this.setTitle("EduTrack - Instructor Homepage");
        this.instructorMenuBar = new JMenuBar();

        JMenu homeMenu = new JMenu("Home");
        JMenu menuMenu = new JMenu("Menu");

        JMenuItem accountItem = new JMenuItem("Account");
        JMenuItem assessmentItem = new JMenuItem("Assessment");
        JMenuItem attendanceItem = new JMenuItem("Attendance");
        JMenuItem courseItem = new JMenuItem("Course Material");
        JMenuItem quizRoomItem = new JMenuItem("Quiz Room");
        JMenuItem contactItem = new JMenuItem("Contact");
        JMenuItem logoutItem = new JMenuItem("Logout");

        accountItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle logout button click event
                account();
            }
        });

        logoutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle logout button click event
                logout();
            }
        });

        formatMenuItem(accountItem);
        formatMenuItem(assessmentItem);
        formatMenuItem(attendanceItem);
        formatMenuItem(courseItem);
        formatMenuItem(quizRoomItem);
        formatMenuItem(contactItem);
        formatMenuItem(logoutItem);

        menuMenu.add(accountItem);
        menuMenu.add(assessmentItem);
        menuMenu.add(attendanceItem);
        menuMenu.add(courseItem);
        menuMenu.add(quizRoomItem);
        menuMenu.add(contactItem);
        menuMenu.add(logoutItem);

        instructorMenuBar.add(homeMenu);
        instructorMenuBar.add(menuMenu);

        // Create panels for each button and set layout
        JPanel announcementPanel = createPanel("Announcement");
        JPanel studentListPanel = createPanel("Student List");
        JPanel assignmentPanel = createPanel("Assignment");

        // Add panels to the main layout
        this.setLayout(new GridLayout(2, 1, 20, 20)); // Adjusted grid layout with reduced spacing
        this.add(announcementPanel);
        this.add(studentListPanel);
        this.add(assignmentPanel);

        ImageIcon icon = createImageIcon("icon2.jpeg");
        if (icon != null) {
            this.setIconImage(icon.getImage());
        }

        this.setJMenuBar(instructorMenuBar);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    InstructorHomePage(String title){
        super("EduTrack - Instructor Homepage",211, 211, 211);
        this.setTitle(title);
        this.instructorMenuBar = new JMenuBar();

        JMenu homeMenu = new JMenu("Home");
        JMenu menuMenu = new JMenu("Menu");

        JMenuItem accountItem = new JMenuItem("Account");
        JMenuItem assessmentItem = new JMenuItem("Assessment");
        JMenuItem attendanceItem = new JMenuItem("Attendance");
        JMenuItem courseItem = new JMenuItem("Course Material");
        JMenuItem quizRoomItem = new JMenuItem("Quiz Room");
        JMenuItem contactItem = new JMenuItem("Contact");
        JMenuItem logoutItem = new JMenuItem("Logout");

        logoutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle logout button click event
                logout();
            }
        });

        formatMenuItem(accountItem);
        formatMenuItem(assessmentItem);
        formatMenuItem(attendanceItem);
        formatMenuItem(courseItem);
        formatMenuItem(quizRoomItem);
        formatMenuItem(contactItem);
        formatMenuItem(logoutItem);

        menuMenu.add(accountItem);
        menuMenu.add(assessmentItem);
        menuMenu.add(attendanceItem);
        menuMenu.add(courseItem);
        menuMenu.add(quizRoomItem);
        menuMenu.add(contactItem);
        menuMenu.add(logoutItem);

        instructorMenuBar.add(homeMenu);
        instructorMenuBar.add(menuMenu);

        ImageIcon icon = createImageIcon("icon2.jpeg");
        if (icon != null) {
            this.setIconImage(icon.getImage());
        }

        this.setJMenuBar(instructorMenuBar);
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
    private void account(){
        new InstructorAccountPage();
        this.dispose();
    }
    private void logout() {
        new LoginPage();
        this.dispose();
    }
