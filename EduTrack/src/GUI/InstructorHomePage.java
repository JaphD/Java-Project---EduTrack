package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InstructorHomePage extends Page{
    private JMenuBar instructorMenuBar;
     InstructorHomePage() {
        super("EduTrack - Instructor Homepage",211, 211, 211);
        // Panel Color and Background Color
         Color panelColor = new Color(211,211,211);
         Color backgroundColor = new Color(70,130,180);

        // Create panels for each button and set layout
        JPanel announcementPanel = createPanel("Announcement","Announcement.png", "Announcement",panelColor);
        JPanel studentListPanel = createPanel("Student List", "Student List.png", "Student List",panelColor);
        JPanel courseMaterialPanel = createPanel("Course Material","Course Material.png", "Course Material",panelColor);
        JPanel assignmentPanel = createPanel("Assignment","Assignment.png", "Assignment",panelColor);

        // Add panels to the main layout
        this.setLayout(new GridLayout(2, 1, 30, 30)); // Adjusted grid layout with reduced spacing
        this.add(announcementPanel);
        this.add(studentListPanel);
        this.add(courseMaterialPanel);
        this.add(assignmentPanel);

         initializeHome();
         this.getContentPane().setBackground(backgroundColor);
    }
    InstructorHomePage(String title){
        super(title,211, 211, 211);
        initializeHome();
    }
    private void initializeHome(){
        this.setTitle(title);
        this.instructorMenuBar = new JMenuBar();

        JMenu homeMenu = new JMenu("Home");
        JMenu menuMenu = new JMenu("Menu");

        JMenuItem homeItem = new JMenuItem("Home");
        JMenuItem logoutItem = new JMenuItem("Logout");

        JMenuItem accountItem = new JMenuItem("Account");
        JMenuItem assessmentItem = new JMenuItem("Assessment");
        JMenuItem attendanceItem = new JMenuItem("Attendance");
        JMenuItem scheduleItem = new JMenuItem("Schedule");
        JMenuItem quizRoomItem = new JMenuItem("Quiz Room");

        addMenuItemListener(homeItem, e -> {}, this::home);
        addMenuItemListener(logoutItem, e -> {}, this::logout);
        addMenuItemListener(accountItem, e -> {}, this::account);
        addMenuItemListener(assessmentItem, e -> {}, this::assessment);
        addMenuItemListener(attendanceItem, e -> {}, this::attendance);
        addMenuItemListener(scheduleItem, e -> {}, this::schedule);

        formatMenuItem(homeItem); formatMenuItem(logoutItem);

        formatMenuItem(accountItem); formatMenuItem(assessmentItem); formatMenuItem(attendanceItem); formatMenuItem(scheduleItem); formatMenuItem(quizRoomItem);

        homeMenu.add(homeItem); homeMenu.add(logoutItem);

        menuMenu.add(accountItem); menuMenu.add(assessmentItem); menuMenu.add(attendanceItem); menuMenu.add(scheduleItem); menuMenu.add(quizRoomItem);

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
    private void addMenuItemListener(JMenuItem menuItem, ActionListener listener, ActionMethod action) {
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.actionPerformed(e);
                action.performAction();
            }
        });
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
            Image scaledImage = icon.getImage().getScaledInstance(125, 125, Image.SCALE_SMOOTH);
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
    private void home(){
        navigateToPage(InstructorHomePage.class);
    }
    private void logout() {
        navigateToPage(LoginPage.class);
    }
    private void account(){
        navigateToPage(InstructorAccountPage.class);
    }
    private void assessment(){
        navigateToPage(InstructorAssessmentPage.class);
    }
    private void attendance(){
        navigateToPage(InstructorAttendancePage.class);
    }
    private void schedule(){
        navigateToPage(InstructorSchedulePage.class);
    }
    void handleButtonClick(String buttonText) {
        switch (buttonText) {
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
            case "Assignment":
                // Create and show a new instance of StudentAssignmentPage
                new InstructorAssignmentPage();
                this.dispose(); // Dispose of the current page
                break;
            case "Course Material":
                new InstructorCourseMaterialPage();
                this.dispose();
                break;
            default:
                // Handle unknown button text
                break;
        }
    }
    @Override
    void actionPerformed(ActionEvent e) {
    }
}
