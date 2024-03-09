package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class InstructorAssessmentPage extends InstructorHomePage {
    private final JLabel titleLabel;
    private final JPanel titlePanel;

    public InstructorAssessmentPage() {
        super("Instructor - Assessment");

        Border border = BorderFactory.createEtchedBorder();

        // Title Label
        this.titleLabel = new JLabel("Assessment");
        formatLabel(titleLabel);
        titleLabel.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        // Title Panel
        this.titlePanel = new JPanel(new BorderLayout());
        titlePanel.setPreferredSize(new Dimension(0, 80));
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        // Set up layout
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(titlePanel, BorderLayout.NORTH);
        add(topPanel, BorderLayout.NORTH);

        try (Socket socket = new Socket(ip, 350);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            String request = "GradeAssessmentList";
            out.writeObject(request);

            Object response = in.readObject();

            if (response instanceof List) {
                List<String> fileNames = (List<String>) response;

                JFrame frame = new JFrame("File List");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                // Create a table model and add data
                Object[][] data = new Object[fileNames.size()][4];
                for (int i = 0; i < fileNames.size(); i++) {
                    String[] parts = fileNames.get(i).split(",");
                    for (int j = 0; j < parts.length; j++) {
                        data[i][j] = parts[j];
                    }
                }

                String[] columnNames = {"First Name", "Last Name", "ID", "Grade"};

                DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
                JTable table = new JTable(tableModel);

                // Create a scroll pane for the table
                JScrollPane scrollPane = new JScrollPane(table);

                // Add the scroll pane to the frame
                add(scrollPane, BorderLayout.CENTER);

                // Set up the frame
                setSize(500, 500);
                setResizable(false);
                setLocationRelativeTo(null);
                setVisible(true);

                // Add a button to trigger grade update
                JButton updateGradeButton = new JButton("Update Grade");
                formatButton(updateGradeButton);
                updateGradeButton.addActionListener(e -> {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        String id = (String) table.getValueAt(selectedRow, 2);
                        String newGrade = JOptionPane.showInputDialog("Enter new grade:");
                        updateGrade(id, newGrade);
                    } else {
                        JOptionPane.showMessageDialog(null, "Please select a student to update the grade.");
                    }
                });

                JPanel buttonPanel = new JPanel();
                buttonPanel.add(updateGradeButton);
                add(buttonPanel, BorderLayout.SOUTH);

            } else {
                System.out.println("Unexpected response from server");
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void updateGrade(String id, String newGrade) {
        try (Socket socket = new Socket(ip, 350);
                     ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                     ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

                    String request = "UpdateGrade";
                    out.writeObject(request);
                    out.writeObject(id);
                    out.writeObject(newGrade);

                    Object response = in.readObject();
                    if (response instanceof Boolean) {
                        boolean success = (Boolean) response;
                        if (success) {
                            JOptionPane.showMessageDialog(null, "Grade updated successfully.");
                        } else {
                            JOptionPane.showMessageDialog(null, "Failed to update grade. Student ID not found.");
                        }
                    } else {
                        System.out.println("Unexpected response from server");
                    }

                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
    }
    /*
    private JLabel formatLabel(JLabel label) {
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

