package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public  class StudentHomePage extends Page {
    protected JMenu homeMenu, menuMenu;
    protected JMenuItem homeItem, logoutItem, accountItem, assessmentItem, attendanceItem, quizRoomItem, contactItem;
    private JMenuBar studentMenuBar;

    public StudentHomePage() {
        super("EduTrack - Student Homepage", 211, 211, 211);
        this.setTitle("EduTrack - Student Homepage");
        this.studentMenuBar = new JMenuBar();

        this.homeMenu = new JMenu("Home");
        this.menuMenu = new JMenu("Menu");

        this.homeItem = new JMenuItem("Home");
        this.logoutItem = new JMenuItem("Logout");

        this.accountItem = new JMenuItem("Account");
        this.assessmentItem = new JMenuItem("Assessment");
        this.attendanceItem = new JMenuItem("Attendance");
        this.quizRoomItem = new JMenuItem("Quiz Room");
        this.contactItem = new JMenuItem("Contact");

        homeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                home();
            }
        });

        logoutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle logout button click event
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
                // Handle logout button click event
                assessment();
            }
        });

        attendanceItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle logout button click event
                attendance();
            }
        });

        quizRoomItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle logout button click event
                quiz();
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
        formatMenuItem(quizRoomItem);
        formatMenuItem(contactItem);

        homeMenu.add(homeItem);
        homeMenu.add(logoutItem);

        menuMenu.add(accountItem);
        menuMenu.add(assessmentItem);
        menuMenu.add(attendanceItem);
        menuMenu.add(quizRoomItem);
        menuMenu.add(contactItem);

        studentMenuBar.add(homeMenu);
        studentMenuBar.add(menuMenu);

        // Create panels for each button and set layout
        JPanel announcementPanel = createPanel("Announcement");
        JPanel schedulePanel = createPanel("Schedule");
        JPanel materialPanel = createPanel("Course Material");
        JPanel assignmentPanel = createPanel("Assignment");

        announcementPanel.setBackground(Color.white);
        schedulePanel.setBackground(Color.white);
        materialPanel.setBackground(Color.white);
        assignmentPanel.setBackground(Color.white);

        // Add panels to the main layout
        this.setLayout(new GridLayout(2, 2, 30, 20)); // Adjusted grid layout with reduced spacing
        this.add(announcementPanel);
        this.add(schedulePanel);
        this.add(materialPanel);
        this.add(assignmentPanel);

        ImageIcon icon = createImageIcon("icon2.jpeg");
        if (icon != null) {
            this.setIconImage(icon.getImage());
        }
        this.getContentPane().setBackground(new Color(70, 130, 180));
        this.setJMenuBar(studentMenuBar);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    StudentHomePage(String title) {
        super(title, 211, 211, 211);
        this.setTitle(title);
        this.studentMenuBar = new JMenuBar();

        JMenu homeMenu = new JMenu("Home");
        JMenu menuMenu = new JMenu("Menu");

        JMenuItem homeItem = new JMenuItem("Home");
        JMenuItem logoutItem = new JMenuItem("Logout");

        JMenuItem accountItem = new JMenuItem("Account");
        JMenuItem assessmentItem = new JMenuItem("Assessment");
        JMenuItem attendanceItem = new JMenuItem("Attendance");
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
                // Handle logout button click event
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
                // Handle logout button click event
                assessment();
            }
        });

        attendanceItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle logout button click event
                attendance();
            }
        });

        quizRoomItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle logout button click event
                quiz();
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
        formatMenuItem(quizRoomItem);
        formatMenuItem(contactItem);

        homeMenu.add(homeItem);
        homeMenu.add(logoutItem);

        menuMenu.add(accountItem);
        menuMenu.add(assessmentItem);
        menuMenu.add(attendanceItem);
        menuMenu.add(quizRoomItem);
        menuMenu.add(contactItem);

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
    private JPanel createPanel(String buttonText) {
        JPanel panel = new JPanel(new GridBagLayout());

        // Add buttons to panels with appropriate constraints
        JButton button = new JButton(buttonText);
        formatButton(button);

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
    /*
    private void addMouseListenerToPanel(JPanel panel, String imageName) {
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                ImageIcon icon = createImageIcon(imageName);
                if (icon != null) {
                    setPanelImage(panel, icon);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // You can set a default image or clear the image when the mouse exits
                // For now, setting it to null
                setPanelImage(panel, null);


            }
        });
    }

    private void setPanelImage(JPanel panel, ImageIcon icon) {
        // Assuming the panel uses a JLabel to display the image
        JLabel label = (JLabel) panel.getComponent(0); // Adjust this if necessary
        label.setIcon(icon);
    }
     */
    void formatButton(JButton button) {
        button.setFocusable(false);
        button.setFont(new Font("Arial", Font.BOLD, 30));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(70, 130, 180)); // Set color to a shade of blue
    }
    private void formatMenuItem(JMenuItem menuItem) {
        menuItem.setFont(new Font("Arial", Font.PLAIN, 16));
        menuItem.setForeground(new Color(70, 130, 180));
        menuItem.setBackground(Color.WHITE);
        menuItem.setHorizontalAlignment(SwingConstants.LEADING);
    }
    protected void home() {
        // Create a new instance of StudentHomePage
        new StudentHomePage();
        this.dispose();
    }
    protected void logout() {
        new LoginPage();
        this.dispose();
    }
    protected void account() {
        new StudentAccountPage();
        this.dispose();
    }
    private void assessment() {
        new StudentAssessmentPage();
        this.dispose();
    }
    private void attendance() {
        new StudentAttendancePage();
        this.dispose();
    }

    private void quiz() {
        new QuizRoomPage();
        this.dispose();
    }
    private void contact() {
        new StudentContactPage();
        this.dispose();
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
                // Handle unknown button text
                break;
        }
    }
    @Override
    void actionPerformed(ActionEvent e) {

    }
}







