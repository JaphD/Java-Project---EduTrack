package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.nio.file.Path;
import java.util.List;

public class StudentCourseMaterialPage extends StudentHomePage implements ActionListener {
    private final JLabel titleLabel;
    private final JPanel titlePanel;
    private DefaultListModel<String> fileListModel;
    private JList<String> fileList;
    private JButton downloadButton;

    public StudentCourseMaterialPage(){
        super("Student - Course Material");

        Border border = BorderFactory.createEtchedBorder();

        // Title Label
        this.titleLabel = new JLabel("Course Material");
        formatLabel(titleLabel);
        titleLabel.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        // Title Panel
        this.titlePanel = new JPanel(new BorderLayout());
        titlePanel.setPreferredSize(new Dimension(0, 80));
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        // Create course button

        // File list
        fileListModel = new DefaultListModel<>();
        fileList = new JList<>(fileListModel);

        // Download button
        downloadButton = new JButton("Download");
        downloadButton.setEnabled(true);
        formatButton(downloadButton);
        downloadButton.addActionListener(this);

        // Set up layout
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(titlePanel, BorderLayout.NORTH);


        this.add(topPanel, BorderLayout.NORTH);
        this.add(new JScrollPane(fileList), BorderLayout.CENTER);
        this.add(downloadButton, BorderLayout.SOUTH);
        this.setVisible(true);

        try (Socket socket = new Socket(ip, 350);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            String request = "StudentCourseRequestFileList";
            out.writeObject(request);

            Object response = in.readObject();

            if (response instanceof List) {
                List<String> fileNames = (List<String>) response;
                DefaultListModel<String> model = (DefaultListModel<String>) fileList.getModel();
                model.clear(); // Clear the list
                for (String fileName : fileNames) {
                    model.addElement(fileName);
                }
            } else {
                System.out.println("Unexpected response from server");
            }
            fileList.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if (!e.getValueIsAdjusting()) {
                        int selectedRow = fileList.getSelectedIndex();
                        if (selectedRow != -1) {
                            String selectedFileName = fileList.getSelectedValue();
                            System.out.println("Selected File: " + selectedFileName);
                        }
                    }
                }
            });

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
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

    private void downloadFile(String selectedFile) {
        try (Socket socket = new Socket(ip, 350)) {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            // Send a request to the server to get file data
            out.writeObject("StudentCourseRequestFile");
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
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage() + " ");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error downloading file: " + e.getMessage());
        }
    }


    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == downloadButton) {
            String selectedFile = fileList.getSelectedValue();
            downloadFile(selectedFile);

        }
    }
}