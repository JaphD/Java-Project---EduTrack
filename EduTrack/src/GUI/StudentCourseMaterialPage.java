package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentCourseMaterialPage extends StudentHomePage implements ActionListener {
    private final JLabel titleLabel;
    private final JPanel titlePanel;
    private JComboBox<String> courseDropdown;
    private DefaultListModel<String> fileListModel;
    private JList<String> fileList;
    private JButton downloadButton;
    public StudentCourseMaterialPage() {
        super("EduTrack - Course Material");

        Border border = BorderFactory.createEtchedBorder();

        // Title Label
        this.titleLabel = new JLabel("Course Material");
        formatLabel(titleLabel);
        titleLabel.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        // Title Panel
        this.titlePanel = new JPanel(new BorderLayout());
        titlePanel.setPreferredSize(new Dimension(0, 80));
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        // Sample course configurations
        String[] courses = {"Course A", "Course B", "Course C"};

        // Create course dropdown
        courseDropdown = new JComboBox<>(courses);
        formatComboBox(courseDropdown);
        courseDropdown.addActionListener(this);

        // File list
        fileListModel = new DefaultListModel<>();
        fileList = new JList<>(fileListModel);

        // Download button
        downloadButton = new JButton("Download");
        downloadButton.setEnabled(false);
        formatButton(downloadButton);
        downloadButton.addActionListener(this);

        // Set up layout
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(titlePanel, BorderLayout.NORTH);

        JPanel dropdownPanel = new JPanel();
        dropdownPanel.add(formatLabel(new JLabel("Select Course:")));
        dropdownPanel.add(courseDropdown);

        topPanel.add(dropdownPanel, BorderLayout.SOUTH);

        this.add(topPanel, BorderLayout.NORTH);
        this.add(new JScrollPane(fileList), BorderLayout.CENTER);
        this.add(downloadButton, BorderLayout.SOUTH);
        this.setVisible(true);
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
    private void formatComboBox(JComboBox<String> comboBox) {
        comboBox.setFont(new Font("Arial", Font.PLAIN, 20));
        comboBox.setForeground(new Color(70, 130, 180));
    }
    @Override
    void formatButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 20));
        button.setForeground(new Color(255, 255, 255));
        button.setBackground(new Color(70, 130, 180));
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }
    private Connection establishConnection() throws SQLException {
        String url = "jdbc:mysql://your-database-url";
        String user = "your-username";
        String password = "your-password";
        return DriverManager.getConnection(url, user, password);
    }
    private void loadFiles(String selectedCourse) {
        fileListModel.clear();
        downloadButton.setEnabled(false);

        try (Connection connection = establishConnection()) {
            String query = "SELECT file_name FROM course_material WHERE course = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, selectedCourse);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String fileName = resultSet.getString("file_name");
                        fileListModel.addElement(fileName);
                    }
                }

                // Enable the download button since files are loaded
                downloadButton.setEnabled(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void downloadFile(String selectedFile) {
        try (Connection connection = establishConnection()) {
            String query = "SELECT file_data FROM course_material WHERE file_name = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, selectedFile);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        InputStream inputStream = resultSet.getBinaryStream("file_data");

                        JFileChooser fileChooser = new JFileChooser();
                        fileChooser.setDialogTitle("Specify a file to save");
                        int userSelection = fileChooser.showSaveDialog(this);

                        if (userSelection == JFileChooser.APPROVE_OPTION) {
                            Path destination = fileChooser.getSelectedFile().toPath();
                            Files.copy(inputStream, destination, StandardCopyOption.REPLACE_EXISTING);
                            JOptionPane.showMessageDialog(this, "File downloaded successfully!");
                        }
                    }
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error downloading file: " + e.getMessage());
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == courseDropdown) {
            String selectedCourse = (String) courseDropdown.getSelectedItem();
            loadFiles(selectedCourse);
        } else if (e.getSource() == downloadButton) {
            String selectedFile = fileList.getSelectedValue();
            if (selectedFile != null) {
                downloadFile(selectedFile);
            }
        }
    }
}