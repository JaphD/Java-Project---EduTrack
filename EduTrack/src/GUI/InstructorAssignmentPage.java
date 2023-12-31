package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InstructorAssignmentPage extends StudentHomePage {
        private final JLabel titleLabel;
        private final JPanel titlePanel;
        private JButton chooseFileButton;
        private JButton uploadButton;
        private JLabel filePathLabel;
        private JTextArea resultTextArea;

        InstructorAssignmentPage() {
            super("Assignment");

            Border border = BorderFactory.createEtchedBorder();

            // Title Label
            this.titleLabel = new JLabel("Assignment Upload");
            formatLabel(titleLabel);
            titleLabel.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

            // Title Panel
            this.titlePanel = new JPanel(new BorderLayout());
            titlePanel.setPreferredSize(new Dimension(0, 80));
            titlePanel.add(titleLabel, BorderLayout.CENTER);

            // File Upload Section
            JPanel fileUploadPanel = new JPanel(new GridLayout(5, 1));  // Increased rows for vertical spacing
            chooseFileButton = new JButton("Select File");  // Changed button label
            uploadButton = new JButton("Upload");
            filePathLabel = new JLabel("Selected File: ");
            resultTextArea = new JTextArea("Result: ");

            // Apply formatting to components
            formatSmallButton(chooseFileButton);  // Modified formatting
            formatSmallButton(uploadButton);  // Modified formatting
            formatLabel(filePathLabel);
            formatTextArea(resultTextArea);

            // Add action listener to the "Select File" button
            chooseFileButton.addActionListener(e -> chooseFile());

            // Add action listener to the "Upload" button
            uploadButton.addActionListener(e -> uploadAssignment());

            // Add components to the file upload panel
            fileUploadPanel.add(chooseFileButton);
            fileUploadPanel.add(uploadButton);
            fileUploadPanel.add(filePathLabel);
            fileUploadPanel.add(resultTextArea);

            // Add panels to the frame
            add(titlePanel, BorderLayout.NORTH);
            add(fileUploadPanel, BorderLayout.CENTER);

            setVisible(true);
        }

        private void chooseFile() {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(this);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String filePath = selectedFile.getAbsolutePath();

                filePathLabel.setText("Selected File: " + filePath);

                // Enable the "Upload" button after file selection
                uploadButton.setEnabled(true);
            } else {
                resultTextArea.setText("Result: File selection canceled.");
            }
        }

        private void uploadAssignment() {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(this);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String filePath = selectedFile.getAbsolutePath();

                filePathLabel.setText("Selected File: " + filePath);

                // Enable the "Upload Assignment" button after file selection
                uploadButton.setEnabled(true);

                // Save the file to the database
                saveFileToDatabase(selectedFile);
            } else {
                resultTextArea.setText("Result: File selection canceled.");
            }
        }
        private void saveFileToDatabase(File file) {
            String url = "jdbc:mysql://localhost:3306/your_database";
            String username = "your_username";
            String password = "your_password";

            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                String sql = "INSERT INTO assignments (file_name, file_data) VALUES (?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, file.getName());

                    try (FileInputStream fis = new FileInputStream(file)) {
                        byte[] fileData = new byte[(int) file.length()];
                        fis.read(fileData);
                        preparedStatement.setBytes(2, fileData);
                    }

                    preparedStatement.executeUpdate();
                }
                resultTextArea.setText("Result: File uploaded successfully.");
            } catch (SQLException | IOException e) {
                e.printStackTrace();
                resultTextArea.setText("Result: Error - Unable to upload file to the database.");
            }
        }

        private void formatSmallButton(JButton button) {
            button.setFont(new Font("Arial", Font.PLAIN, 30));
            button.setForeground(Color.WHITE);
            button.setBackground(new Color(70, 130, 180));
            button.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));  // Decreased padding
        }

        private void formatLabel(JLabel label) {
            label.setFont(new Font("Arial", Font.BOLD, 30));
            label.setForeground(new Color(70, 130, 180));
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setAlignmentY(JLabel.CENTER);
            label.setBackground(Color.WHITE);
            label.setOpaque(true);
        }

        private void formatTextArea(JTextArea textArea) {
            textArea.setFont(new Font("Arial", Font.BOLD, 16));
            textArea.setForeground(new Color(70, 130, 180));
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setEditable(false);
            textArea.setBackground(Color.WHITE);
            textArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        }
    }


