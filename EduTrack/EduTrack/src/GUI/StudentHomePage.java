package GUI;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public  class StudentHomePage extends Page{
    protected JMenu homeMenu, menuMenu;
    protected JMenuItem homeItem, logoutItem, accountItem, assessmentItem, attendanceItem, quizRoomItem, contactItem;
    private JMenuBar studentMenuBar;
    protected Color panelColor = new Color(211,211,211);
    protected Color backgroundColor = new Color(70,130,180);

    public StudentHomePage() {
        super("EduTrack - Student Homepage", 211, 211, 211);

        // Create panels for each button and set layout
        JPanel announcementPanel = createPanel("Announcement", "Announcement.png", "Announcement", panelColor);
        JPanel schedulePanel = createPanel("Schedule", "Schedule.png", "Schedule",panelColor);
        JPanel materialPanel = createPanel("Course Material","Course Material.png", "Course Material", panelColor);
        JPanel assignmentPanel = createPanel("Assignment", "Assignment.png", "Assignment", panelColor);

        // Add panels to the main layout
        this.setLayout(new GridLayout(2, 2, 30, 30)); // Adjusted grid layout with reduced spacing
        this.add(announcementPanel);
        this.add(schedulePanel);
        this.add(materialPanel);
        this.add(assignmentPanel);

        initializeHome();
        this.getContentPane().setBackground(backgroundColor);
    }
    StudentHomePage(String title) {
        super(title, 211, 211, 211);

        initializeHome();
    }
    private void initializeHome(){
        this.setTitle(title);
        this.studentMenuBar = new JMenuBar();

        JMenu homeMenu = new JMenu("Home");
        JMenu menuMenu = new JMenu("Menu");

        JMenuItem homeItem = new JMenuItem("Home");
        JMenuItem logoutItem = new JMenuItem("Logout");

        JMenuItem accountItem = new JMenuItem("Account");
        JMenuItem assessmentItem = new JMenuItem("Assessment");
        JMenuItem quizRoomItem = new JMenuItem("Quiz Room");


        addMenuItemListener(homeItem, e -> {}, this::home);
        addMenuItemListener(logoutItem, e -> {}, this::logout);
        addMenuItemListener(accountItem, e -> {}, this::account);
        addMenuItemListener(assessmentItem, e -> {}, this::assessment);
        addMenuItemListener(quizRoomItem, e -> {}, this::quiz);


        formatMenuItem(homeItem);
        formatMenuItem(logoutItem);

        formatMenuItem(accountItem);
        formatMenuItem(assessmentItem);
        formatMenuItem(quizRoomItem);

        homeMenu.add(homeItem); homeMenu.add(logoutItem);

        menuMenu.add(accountItem); menuMenu.add(assessmentItem); menuMenu.add(quizRoomItem); 

        studentMenuBar.add(homeMenu);
        studentMenuBar.add(menuMenu);

        ImageIcon icon = createImageIcon("icon2.jpeg");
        if (icon != null) {
            this.setIconImage(icon.getImage());
        }

        this.setJMenuBar(studentMenuBar);
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
    JPanel createPanel(String buttonText, String imagePath,String tooltipText, Color backgroundColor) {
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
    private void home() {
        navigateToPage(StudentHomePage.class);
    }
    private void logout() {
       navigateToPage(LoginPage.class);
    }
    private void account() {
        navigateToPage(StudentAccountPage.class);
    }
    private void assessment() {
        navigateToPage(StudentAssessmentPage.class);
    }
    private void quiz() {
        navigateToPage(StudentQuizRoomPage.class);
    }
    void handleButtonClick(String buttonText) {
        switch (buttonText) {
            case "Announcement":
                // Create and show a new instance of StudentAnnouncementPage
                new StudentAnnouncementPage();
                this.dispose(); // Dispose of the current page
                break;
            case "Schedule":
                // Create and show a new instance of StudentSchedulePage
                new StudentSchedulePage();
                this.dispose(); // Dispose of the current page
                break;
            case "Course Material":
                // Create and show a new instance of StudentCourseMaterialPage
                new StudentCourseMaterialPage();
                this.dispose(); // Dispose of the current page
                break;
            case "Assignment":
                // Create and show a new instance of StudentAssignmentPage
                new StudentAssignmentPage();
                this.dispose(); // Dispose of the current page
                break;
            default:
                // Handles unknown button text
                break;
        }
    }
    @Override
    void actionPerformed(ActionEvent e) {

    }
}







