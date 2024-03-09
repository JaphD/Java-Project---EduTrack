package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class InstructorCourseMaterialPage extends InstructorHomePage implements ActionListener {
    private final JLabel titleLabel;
    private final JPanel titlePanel;
    private final JButton uploadButton;
    private final JFileChooser fileChooser;

    InstructorCourseMaterialPage() {
        super("EduTrack - Instructor Course Material");

        Border border = BorderFactory.createEtchedBorder();

        // Title Label
        this.titleLabel = new JLabel("Course Material");
        formatLabel(titleLabel);
        titleLabel.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        // Title Panel
        this.titlePanel = new JPanel(new BorderLayout());
        titlePanel.setPreferredSize(new Dimension(0, 80));
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        // Upload Button
        uploadButton = new JButton("Upload");
        formatSmallButton(uploadButton);
        uploadButton.addActionListener(this);

        // Set up layout
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(titlePanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.add(uploadButton);

        topPanel.add(centerPanel, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);

        setVisible(true);

        // File chooser initialization
        fileChooser = new JFileChooser();
    }
    /*
    private Component formatLabel(JLabel label) {
        label.setFont(new Font("Arial", Font.BOLD, 30));
        label.setForeground(new Color(70, 130, 180));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setAlignmentY(JLabel.CENTER);
        label.setBackground(Color.white);
        label.setOpaque(true);
        return label;
    }

     */
    /*
    private void formatSmallButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 25)); // Changed font size
        button.setForeground(new Color(255, 255, 255));
        button.setBackground(new Color(70, 130, 180));
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Adjusted padding
        button.setFocusable(false);
    }

     */

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == uploadButton) {
            int result = fileChooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();

                String serverAddress = ip;
                int serverPort = 300;

                try (Socket socket = new Socket(serverAddress, serverPort);
                     OutputStream outputStream = socket.getOutputStream();
                     DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
                     FileInputStream fileInputStream = new FileInputStream(selectedFile))
                {
                    // Send the file name to the server
                    dataOutputStream.writeUTF(selectedFile.getName());
                    dataOutputStream.write("InstructorCourseMaterialsUpload\n".getBytes());
                    byte[] buffer = new byte[1024];
                    int bytesRead;

                    // Read the file data from the input stream and write it to the output stream
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






