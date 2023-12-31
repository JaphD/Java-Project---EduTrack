package GUI;

import javax.swing.*;
import java.awt.*;

public class HomeStyle {
    public static void formatButton(JButton button) {
        button.setFocusable(false);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setForeground(Color.white);
        button.setBackground(new Color(70, 130, 180));
    }

    public static void formatMenuItem(JMenuItem menuItem) {
        menuItem.setFont(new Font("Arial", Font.PLAIN, 16));
        menuItem.setForeground(new Color(70, 130, 180));
    }
    public static void menuBar(){
        JMenuBar studentMenuBar = new JMenuBar();

        JMenu homeMenu = new JMenu("Home");
        JMenu menuMenu = new JMenu("Menu");

        JMenuItem accountItem = new JMenuItem("Account");
        JMenuItem assessmentItem = new JMenuItem("Assessment");
        JMenuItem attendanceItem = new JMenuItem("Attendance");
        JMenuItem quizRoomItem = new JMenuItem("Quiz Room");
        JMenuItem contactItem = new JMenuItem("Contact");
        JMenuItem logoutItem = new JMenuItem("Logout");

        menuMenu.add(accountItem);
        menuMenu.add(assessmentItem);
        menuMenu.add(attendanceItem);
        menuMenu.add(quizRoomItem);
        menuMenu.add(contactItem);
        menuMenu.add(logoutItem);

        studentMenuBar.add(homeMenu);
        studentMenuBar.add(menuMenu);

    }
}
