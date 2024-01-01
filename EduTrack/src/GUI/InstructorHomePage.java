package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InstructorHomePage extends Page {
    private JMenuBar instructorMenuBar;
     InstructorHomePage() {
        super("EduTrack - Instructor Homepage",211, 211, 211);
        this.setTitle("EduTrack - Instructor Homepage");
        this.instructorMenuBar = new JMenuBar();

        JMenu homeMenu = new JMenu("Home");
        JMenu menuMenu = new JMenu("Menu");

        JMenuItem homeItem = new JMenuItem("Home");
        JMenuItem logoutItem = new JMenuItem("Logout");


        JMenuItem accountItem = new JMenuItem("Account");
        JMenuItem assessmentItem = new JMenuItem("Assessment");
        JMenuItem attendanceItem = new JMenuItem("Attendance");
        JMenuItem courseItem = new JMenuItem("Course Material");
        JMenuItem quizRoomItem = new JMenuItem("Quiz Room");
        JMenuItem contactItem = new JMenuItem("Contact");

        homeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                home();
            }
        });

        logoutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });

        accountItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle logout button click event
                account();
            }
        });

        assessmentItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                assessment();
            }
        });

        courseItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                course();
            }
        });

        contactItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contact();
            }
        });

        formatMenuItem(homeItem);
        formatMenuItem(logoutItem);

        formatMenuItem(accountItem);
        formatMenuItem(assessmentItem);
        formatMenuItem(attendanceItem);
        formatMenuItem(courseItem);
        formatMenuItem(quizRoomItem);
        formatMenuItem(contactItem);

        homeMenu.add(homeItem);
        homeMenu.add(logoutItem);

        menuMenu.add(accountItem);
        menuMenu.add(assessmentItem);
        menuMenu.add(attendanceItem);
        menuMenu.add(courseItem);
        menuMenu.add(quizRoomItem);
        menuMenu.add(contactItem);

        instructorMenuBar.add(homeMenu);
        instructorMenuBar.add(menuMenu);

         // Panel Color
         Color backgroundColor = new Color(211,211,211);

        // Create panels for each button and set layout
        JPanel announcementPanel = createPanel("Announcement","Announcement.png", "Announcement",backgroundColor);
        JPanel studentListPanel = createPanel("Student List", "Student List.png", "Student List",backgroundColor);
        JPanel assignmentPanel = createPanel("Assignment","Assignment.png", "Assignment",backgroundColor);

        announcementPanel.setBackground(Color.white);
        studentListPanel.setBackground(Color.white);
        assignmentPanel.setBackground(Color.white);

        // Add panels to the main layout
        this.setLayout(new GridLayout(2, 1, 20, 20)); // Adjusted grid layout with reduced spacing
        this.add(announcementPanel);
        this.add(studentListPanel);
        this.add(assignmentPanel);

        ImageIcon icon = createImageIcon("icon2.jpeg");
        if (icon != null) {
            this.setIconImage(icon.getImage());
        }

        this.getContentPane().setBackground(new Color(238, 238,238));
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

        JMenuItem homeItem = new JMenuItem("Home");
        JMenuItem logoutItem = new JMenuItem("Logout");

        JMenuItem accountItem = new JMenuItem("Account");
        JMenuItem assessmentItem = new JMenuItem("Assessment");
        JMenuItem attendanceItem = new JMenuItem("Attendance");
        JMenuItem courseItem = new JMenuItem("Course Material");
        JMenuItem quizRoomItem = new JMenuItem("Quiz Room");
        JMenuItem contactItem = new JMenuItem("Contact");

        homeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                home();
            }
        });

        logoutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });

        accountItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle logout button click event
                account();
            }
        });

        assessmentItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                assessment();
            }
        });

        courseItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                course();
            }
        });

        contactItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contact();
            }
        });

        formatMenuItem(homeItem);
        formatMenuItem(logoutItem);

        formatMenuItem(accountItem);
        formatMenuItem(assessmentItem);
        formatMenuItem(attendanceItem);
        formatMenuItem(courseItem);
        formatMenuItem(quizRoomItem);
        formatMenuItem(contactItem);

        homeMenu.add(homeItem);
        homeMenu.add(logoutItem);

        menuMenu.add(accountItem);
        menuMenu.add(assessmentItem);
        menuMenu.add(attendanceItem);
        menuMenu.add(courseItem);
        menuMenu.add(quizRoomItem);
        menuMenu.add(contactItem);

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
    private JPanel createPanel(String buttonText, String imagePath,String tooltipText,Color backgroundColor) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(backgroundColor);

        // Add buttons to panels with appropriate constraints
        JButton button = new JButton();
        button.setBorderPainted(false); // Make the button border invisible
        button.setContentAreaFilled(false); // Make the button content area (background) invisible
        button.setBorder(null);
        button.setText("");

        ImageIcon icon = createImageIcon(imagePath);
        if(icon != null){
            Image scaledImage = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(scaledImage));

            // Center the image within the button
            button.setVerticalTextPosition(SwingConstants.BOTTOM);
            button.setHorizontalTextPosition(SwingConstants.CENTER);
        }

        button.setToolTipText(tooltipText);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleButtonClick(buttonText);
            }
        });

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(10, 10, 10, 10); // Add padding around buttons

        panel.add(button, constraints);

        return panel;
    }
    void formatButton(JButton button) {
        button.setFocusable(false);
        button.setFont(new Font("Arial", Font.BOLD, 30));
        button.setForeground(Color.white);
        button.setBackground(new Color(70, 130, 180)); // Set color to a shade of blue
    }
    private void formatMenuItem(JMenuItem menuItem) {
        menuItem.setFont(new Font("Arial", Font.PLAIN, 16));
        menuItem.setForeground(new Color(70, 130, 180));
        menuItem.setBackground(Color.WHITE);
        menuItem.setHorizontalAlignment(SwingConstants.LEADING);
    }
    private void home(){
        new InstructorHomePage();
        this.dispose();
    }
    private void logout() {
        new LoginPage();
        this.dispose();
    }
    private void account(){
        new InstructorAccountPage();
        this.dispose();
    }
    private void assessment(){
        new InstructorAssessmentPage();
        this.dispose();
    }
    private void course(){
        new InstructorCourseMaterialPage();
        this.dispose();
    }
    private void contact(){
        new InstructorContactPage();
        this.dispose();
    }
    void handleButtonClick(String buttonText) {
        switch (buttonText) {
            /*
            case "Announcement":
                // Create and show a new instance of StudentAnnouncementPage
                new InstructorAnnouncementPage();
                this.dispose(); // Dispose of the current page
                break;

            case "Student List":
                // Create and show a new instance of StudentSchedulePage
                new InstructorStudentListPage();
                this.dispose(); // Dispose of the current page
                break;

                 */
            case "Assignment":
                // Create and show a new instance of StudentAssignmentPage
                new InstructorAssignmentPage();
                this.dispose(); // Dispose of the current page
                break;
                /*
            case "Schedule":
                new InstructorSchedulePage();
                this.dispose();
                break;

                 */
            default:
                // Handle unknown button text
                break;
        }
    }
    @Override
    void actionPerformed(ActionEvent e) {

    }
}
