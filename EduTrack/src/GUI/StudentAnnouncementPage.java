package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.List;

import java.util.Date;
public class StudentAnnouncementPage extends StudentHomePage {
    private final JTextArea announcementTextArea;

    public StudentAnnouncementPage(){
        super("EduTrack - Student Announcement");

        Border border = BorderFactory.createEtchedBorder();

        // Text Area
        this.announcementTextArea = new JTextArea();
        announcementTextArea.setEditable(false);
        announcementTextArea.setLineWrap(true);
        announcementTextArea.setWrapStyleWord(true);
        announcementTextArea.setFont(new Font("Arial", Font.PLAIN, 16));
        announcementTextArea.setVisible(true);

        // Title Panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setPreferredSize(new Dimension(0, 80));
        JLabel titleLabel = new JLabel("Announcement");
        formatLabel(titleLabel);
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(announcementTextArea), BorderLayout.CENTER);

        // Set up layout
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        displayAnnouncementsFromServer();
    }

    public String processResponseList(List<Object> responseList) {
        StringBuilder displayText = new StringBuilder();
        for (Object obj : responseList) {
            if (obj instanceof Announcement) {
                Announcement announcement = (Announcement) obj;
                displayText.append(announcement.getAnnouncement()).append("\n");
                displayText.append("Posted Time: ").append(announcement.getPostTime()).append("\n\n");
            } else if (obj instanceof String) {
                displayText.append(obj).append("\n\n");
            } else {
                // Log or handle unexpected object type appropriately
                System.err.println("Unexpected object type in response list: " + obj.getClass().getName());
            }
        }
        return displayText.toString();
    }

    private void displayAnnouncementsFromServer() {
        try (Socket socket = new Socket(ip, 350);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            String request = "DisplayAnnouncement";
            out.writeObject(request);
            Object response = in.readObject();

            String formattedText = processResponseList((List<Object>) response);
            announcementTextArea.setText(formattedText);

        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null,"Error communicating with server", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public static class Announcement {
        private String announcement;
        private Date postTime;

        public String getAnnouncement() {
            return announcement;
        }
        public void setAnnouncement(String announcement) {
            this.announcement = announcement;
        }
        public Date getPostTime() {
            return postTime;
        }
        public void setPostTime(Date postTime) {
            this.postTime = postTime;
        }
    }
}