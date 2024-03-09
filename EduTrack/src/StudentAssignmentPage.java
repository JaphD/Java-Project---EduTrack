package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.nio.file.Path;
import java.util.List;

public class StudentAssignmentPage extends StudentHomePage {
    private final JLabel titleLabel;
    private final JPanel titlePanel, downloadPanel, uploadPanel, downloadlistPanel;
    private JButton downloadButton, uploadButton;
    private final JFileChooser fileChooser;
    private DefaultListModel<String> fileListModel;
    private JList<String> fileList;

    private JScrollPane scrollPane;

    public StudentAssignmentPage() {
        super(" EduTrack - Student Assignment");

        Border border = BorderFactory.createEtchedBorder();

        // Title Label
        this.titleLabel = new JLabel("Assignment");
        formatLabel(titleLabel);
        titleLabel.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        // Title Panel
        this.titlePanel = new JPanel(new BorderLayout());
        titlePanel.setPreferredSize(new Dimension(0, 80));
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        // File list
        fileListModel = new DefaultListModel<>();
        fileList = new JList<>(fileListModel);

        this.downloadPanel = createPanel("Download", "download.png", "Download", panelColor);
        this.uploadPanel = createPanel("Upload", "upload.png", "Upload", panelColor);
        this.downloadlistPanel = createPanel(panelColor);

        // Main Content Panel
        this.setLayout(new BorderLayout(30,30)); // Use BorderLayout with horizontal and vertical gaps
        this.add(titlePanel, BorderLayout.NORTH); // Add the title panel to the north

        // Panel containing Download and Upload panels
        JPanel panelsContainer = new JPanel(new GridLayout(2, 2, 30, 30));
        panelsContainer.add(downloadlistPanel);
        panelsContainer.add(uploadPanel);
        panelsContainer.add(downloadPanel);
        panelsContainer.setBackground(backgroundColor);

        this.add(panelsContainer, BorderLayout.CENTER); // Add the container to the center
        setVisible(true);

        try (Socket socket = new Socket(ip, 350);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            String request = "InstructorRequestFileList";
            out.writeObject(request);

            Object response = in.readObject();

            if (response instanceof List) {
                List<String> fileNames = (List<String>) response;

                DefaultListModel<String> listModel = (DefaultListModel<String>) fileList.getModel();
                listModel.clear();
                for (String fileName : fileNames) {
                    listModel.addElement(fileName);
                }

                // Create JList with the created DefaultListModel
                fileList.setModel(listModel);
                fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                fileList.setLayoutOrientation(JList.VERTICAL);

                this.scrollPane = new JScrollPane(fileList);
                scrollPane.setPreferredSize(new Dimension(720, 310));
                downloadlistPanel.add(scrollPane);
            }
               //
            else {
                System.out.println("Unexpected response from server");
            }

            fileList.addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = fileList.getSelectedIndex();
                    if (selectedRow != -1) {
                        String selectedFileName = fileList.getSelectedValue();
                        System.out.println("Selected File: " + selectedFileName);
                    }
                }
            });

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        // File chooser initialization
        fileChooser = new JFileChooser();
    }
    JPanel createPanel(Color backgroundColor) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(backgroundColor);

        return panel;
    }
    private void downloadFile(String selectedFile) {
        try (Socket socket = new Socket(ip, 350)) {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            // Send a request to the server to get file data
            out.writeObject("InstructorRequestFile");
            out.writeObject(selectedFile);

            // Receive the file data from the server
            byte[] fileData = (byte[]) in.readObject();

            if (fileData != null) {
                // Show file chooser dialog
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Specify a file to save");
                fileChooser.setSelectedFile(new File(selectedFile));
                FileNameExtensionFilter pdfFilter = new FileNameExtensionFilter("PDF files", "pdf");
                FileNameExtensionFilter pngFilter = new FileNameExtensionFilter("PNG files", "png");
                FileNameExtensionFilter jpegFilter = new FileNameExtensionFilter("JPEG files", "jpeg");
                fileChooser.setFileFilter(pdfFilter);
                fileChooser.addChoosableFileFilter(pngFilter);
                fileChooser.addChoosableFileFilter(jpegFilter);
                int userSelection = fileChooser.showSaveDialog(this);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    // Save the file to the selected location
                    Path destination = fileChooser.getSelectedFile().toPath();
                    try (FileOutputStream fileOutputStream = new FileOutputStream(destination.toFile())) {
                        fileOutputStream.write(fileData);
                        JOptionPane.showMessageDialog(this, "File downloaded successfully!");
                    } catch (IOException e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Error saving file: " + e.getMessage());
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Error: Empty file data received");
            }
        } catch (ClassNotFoundException | IOException e) {
            JOptionPane.showMessageDialog(this, "Error downloading file. Please try again ", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    @Override
    void handleButtonClick(String buttonText) {
        switch (buttonText) {
            case "Download" -> {
                String selectedFileName = fileList.getSelectedValue();
                downloadFile(selectedFileName);
            }
            case "Upload" -> {
                int result = fileChooser.showOpenDialog(this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String serverAddress = ip;
                    int serverPort = 300;
                    try (Socket socket = new Socket(serverAddress, serverPort);
                         OutputStream outputStream = socket.getOutputStream();
                         DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
                         FileInputStream fileInputStream = new FileInputStream(selectedFile)) {
                        dataOutputStream.writeUTF(selectedFile.getName());
                        dataOutputStream.write("StudentAssignmentsUpload\n".getBytes());
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                        }
                        JOptionPane.showMessageDialog(this, "File uploaded successfully");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(this, "Error uploading file", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }

    }
}









