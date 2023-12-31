package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InstructorAssessmentPage extends InstructorHomePage implements ActionListener {
    private final JLabel titleLabel;
    private final JPanel titlePanel;
    private JTable assessmentTable;
    private DefaultTableModel tableModel;
    private JButton submitButton;

    InstructorAssessmentPage() {
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

        // Create table
        String[] columnNames = {"Assessment Type", "Score", "Letter Grade"};
        tableModel = new DefaultTableModel(null, columnNames);
        assessmentTable = new JTable(tableModel);

        // Set row height and column widths
        assessmentTable.setRowHeight(30);
        assessmentTable.getColumnModel().getColumn(0).setPreferredWidth(200);
        assessmentTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        assessmentTable.getColumnModel().getColumn(2).setPreferredWidth(150);

        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setFont(new Font("Arial", Font.PLAIN, 20));
        assessmentTable.setDefaultRenderer(Object.class, cellRenderer);

        // Submit Button
        submitButton = new JButton("Submit");
        formatButton(submitButton);
        submitButton.addActionListener(this);

        // Set up layout
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(titlePanel, BorderLayout.NORTH);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(assessmentTable), BorderLayout.CENTER);
        add(submitButton, BorderLayout.SOUTH);

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

    void formatButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 20));
        button.setForeground(new Color(255, 255, 255));
        button.setBackground(new Color(70, 130, 180));
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            // Iterate through the table to get assessment data
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String assessmentType = (String) tableModel.getValueAt(i, 0);
                int score = Integer.parseInt(tableModel.getValueAt(i, 1).toString());
                String letterGrade = (String) tableModel.getValueAt(i, 2);

                // Send data to MySQL database
                sendToDatabase(assessmentType, score, letterGrade);
            }

            // Display a confirmation message or handle database submission result
            JOptionPane.showMessageDialog(this, "Assessment data submitted.");
        }
    }

    // Method to establish a connection to the MySQL database
    private Connection establishConnection() throws SQLException {
        String url = "jdbc:mysql://your-database-url";
        String user = "your-username";
        String password = "your-password";
        return DriverManager.getConnection(url, user, password);
    }

    // Method to send assessment data to the MySQL database
    private void sendToDatabase(String assessmentType, int score, String letterGrade) {
        try (Connection connection = establishConnection()) {
            String query = "INSERT INTO assessments (assessment_type, score, letter_grade) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, assessmentType);
                preparedStatement.setInt(2, score);
                preparedStatement.setString(3, letterGrade);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Handle database error
            JOptionPane.showMessageDialog(this, "Error submitting assessment data.");
        }
    }
}