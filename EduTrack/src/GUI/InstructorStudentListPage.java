package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class InstructorStudentListPage extends InstructorHomePage {
    private final JLabel titleLabel;
    private final JPanel titlePanel;
    private DefaultListModel<String> fileListModel;
    private JList<String> fileList;

    InstructorStudentListPage() {
        super("EduTrack - Student List");

        Border border = BorderFactory.createEtchedBorder();

        // Title Label
        this.titleLabel = new JLabel("Student List");
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

        // Set up layout
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(titlePanel, BorderLayout.NORTH);


        this.add(topPanel, BorderLayout.NORTH);
        this.add(new JScrollPane(fileList), BorderLayout.CENTER);
        this.setVisible(true);

        try (Socket socket = new Socket(ip, 350);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            String request = "DisplayStudentFileList";
            out.writeObject(request);

            Object response = in.readObject();

            if (response instanceof java.util.List) {
                java.util.List<String> fileNames = (List<String>) response;
                DefaultListModel<String> model = (DefaultListModel<String>) fileList.getModel();
                model.clear();
                for (String fileName : fileNames) {
                    model.addElement(fileName);
                }
            } else {
                System.out.println("Unexpected response from server");
            }
            fileList.addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = fileList.getSelectedIndex();
                    if (selectedRow != -1) {
                        String selectedFileName = (String) fileList.getSelectedValue();
                        System.out.println("Selected File: " + selectedFileName);
                    }
                }
            });

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
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
}


