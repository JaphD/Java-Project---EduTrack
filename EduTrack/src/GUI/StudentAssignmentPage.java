package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentAssignmentPage extends StudentHomePage implements ActionListener {
    private final JLabel titleLabel;
    private final JPanel titlePanel;
    private JList<String> assignmentList;
    private DefaultListModel<String> assignmentListModel;
    private JButton downloadButton;

    private static final String DB_URL = "jdbc:mysql://your-database-url";
    private static final String DB_USER = "your-username";
    private static final String DB_PASSWORD = "your-password";

    public StudentAssignmentPage() {
        super("EduTrack - Assignment");

        Border border = BorderFactory.createEtchedBorder();

        // Title Label
        this.titleLabel = new JLabel("Assignment");
        formatLabel(titleLabel);
        titleLabel.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        // Title Panel
        this.titlePanel = new JPanel(new BorderLayout());
        titlePanel.setPreferredSize(new Dimension(0, 80));
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        // Assignment list
        assignmentListModel = new DefaultListModel<>();
        assignmentList = new JList<>(assignmentListModel);

        // Download button
        downloadButton = new JButton("Download Assignment");
        formatButton(downloadButton);
        downloadButton.addActionListener(this);

        // Set up layout
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(titlePanel, BorderLayout.NORTH);

        topPanel.add(new JScrollPane(assignmentList), BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);
        add(downloadButton, BorderLayout.SOUTH);

        // Load assignments from the database
        loadAssignments();

        setVisible(true);
    }

    private Component formatLabel(JLabel label) {
        label.setFont(new Font("Arial", Font.BOLD, 30));
        label.setForeground(new Color(70, 130, 180));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setAlignmentY(JLabel.CENTER);
        label.setBackground(Color.white);
        label.setOpaque(true);
        return label;
    }
    @Override
    void formatButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 20));
        button.setForeground(new Color(255, 255, 255));
        button.setBackground(new Color(70, 130, 180));
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    private Connection establishConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    private void loadAssignments() {
        try (Connection connection = establishConnection()) {
            String query = "SELECT assignment_name FROM assignments";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String assignmentName = resultSet.getString("assignment_name");
                        assignmentListModel.addElement(assignmentName);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void downloadAssignment(String selectedAssignment) {
        try (Connection connection = establishConnection()) {
            String query = "SELECT file_data FROM assignments WHERE assignment_name = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, selectedAssignment);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        // Retrieve file data from the result set
                        InputStream inputStream = resultSet.getBinaryStream("file_data");
                        byte[] buffer = new byte[inputStream.available()];
                        inputStream.read(buffer);

                        // Save the file to disk
                        File file = new File(selectedAssignment + ".pdf");
                        try (FileOutputStream outputStream = new FileOutputStream(file)) {
                            outputStream.write(buffer);
                        }

                        JOptionPane.showMessageDialog(this, "Download successful. File saved as: " + file.getAbsolutePath());
                    } else {
                        JOptionPane.showMessageDialog(this, "File not found in the database.");
                    }
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error downloading assignment.");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == downloadButton) {
            String selectedAssignment = assignmentList.getSelectedValue();
            if (selectedAssignment != null) {
                downloadAssignment(selectedAssignment);
            } else {
                JOptionPane.showMessageDialog(this, "Please select an assignment to download.");
            }
        }
    }
}





