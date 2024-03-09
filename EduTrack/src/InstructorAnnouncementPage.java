package GUI;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class InstructorAnnouncementPage extends InstructorHomePage implements ActionListener {
    private JTextArea announcementTextArea;
    private JLabel titleLabel;
    private JPanel titlePanel;
    private JButton submitButton;
    private Color textColor;

    public InstructorAnnouncementPage() {
        super("Instructor Announcement");
        initializeComponents();
        addComponentsToFrame();
        setVisible(true);
    }
    private void initializeComponents() {
        // Announcement Text Area with shady blue border
        textColor = new Color(15,15,15);
        announcementTextArea = new JTextArea();
        announcementTextArea.setLineWrap(true);
        announcementTextArea.setWrapStyleWord(true);
        announcementTextArea.setFont(new Font("Arial", Font.BOLD, 25));
        announcementTextArea.setForeground(textColor);
        announcementTextArea.setToolTipText("Character Count should not exceed 750");
        announcementTextArea.setBorder(new LineBorder(new Color(173, 216, 230), 15)); // Shady blue border


        int squareSize = 150; // Adjust as needed
        announcementTextArea.setPreferredSize(new Dimension(squareSize, squareSize));

        // Submit Button
        submitButton = new JButton("Post");
        submitButton.setPreferredSize(new Dimension(20, 20));
        formatButton(submitButton);
        submitButton.addActionListener(this);

        // Title Label
        titleLabel = new JLabel("Announcement");
        titleLabel.setSize(100,120);
        formatLabel(titleLabel);

        // Title Panel
        titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(titleLabel, BorderLayout.CENTER);
    }
    private void addComponentsToFrame() {
        Container contentPane = getContentPane();
        contentPane.setLayout(new GridBagLayout());

        GridBagConstraints gridbag = new GridBagConstraints();

        // Title Panel
        gridbag.gridx = 0;
        gridbag.gridy = 0;
        gridbag.gridwidth = 2;
        gridbag.fill = GridBagConstraints.HORIZONTAL;
        gridbag.insets = new Insets(50, 300, 50, 300);
        contentPane.add(titlePanel, gridbag);

        // Announcement Text Area
        gridbag.gridy = 1;
        gridbag.gridwidth = 2; // Span across both columns
        gridbag.weightx = 1.0;  // Fill horizontally
        gridbag.weighty = 0.7;
        gridbag.fill = GridBagConstraints.BOTH;
        contentPane.add(announcementTextArea, gridbag);

        // Submit Button
        gridbag.gridx = 0;
        gridbag.gridy = 2;
        gridbag.gridwidth = 1; // Keep spanning both columns
        gridbag.weightx = 0.1;  // Fill horizontally
        gridbag.weighty = 0.1;
        gridbag.fill = GridBagConstraints.BOTH;
        contentPane.add(submitButton, gridbag);

        submitButton.setSize(new Dimension(80, 30));
    }
    /*
    protected Component formatLabel(JLabel label) {
        label.setFont(new Font("Arial", Font.BOLD, 65));
        label.setForeground(new Color(70, 130, 180));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setAlignmentY(JLabel.CENTER);
        label.setBackground(panelColor);
        label.setOpaque(true);
        return label;
    }

     */

    @Override
    public void actionPerformed(ActionEvent e) {
        String post = announcementTextArea.getText();

        if (e.getSource() == submitButton) {
            try (Socket socket = new Socket(ip, 500);
                 OutputStream out = socket.getOutputStream();
                 InputStream in = socket.getInputStream();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {

                //out.write("AnnouncementPost".getBytes());
                String data = String.format("%s\n",post);
                out.write(data.getBytes());

                String response = reader.readLine();
                System.out.println(response);
                if (response.equals("posted")) {
                    JOptionPane.showMessageDialog(this, "Announcement is successfully posted");

                } else {
                    JOptionPane.showMessageDialog(this, "Announcement has not been posted. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }

                out.close();
                in.close();
                socket.close();
            }
            catch(IOException ex){
                System.out.println(ex.getMessage());
            }
            catch(Exception ex){
                System.out.println(ex.getMessage());
            }
        }
    }
}




