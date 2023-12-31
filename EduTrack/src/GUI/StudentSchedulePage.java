package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentSchedulePage extends StudentHomePage {
    private final JLabel titleLabel;
    private final JPanel titlePanel;
    private JTable scheduleTable;
    private DefaultTableModel tableModel;

    StudentSchedulePage() {
        super("EduTrack - Student Schedule");

        Border border = BorderFactory.createEtchedBorder();

        // Title Label
        this.titleLabel = new JLabel("Schedule");
        formatLabel(titleLabel);
        titleLabel.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        // Title Panel
        this.titlePanel = new JPanel(new BorderLayout());
        titlePanel.setPreferredSize(new Dimension(0, 80));
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        // Create table
        String[] columnNames = {"Time", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        tableModel = new DefaultTableModel(null, columnNames);
        scheduleTable = new JTable(tableModel);

        // Retrieve schedule data from the database
        retrieveScheduleData();

        // Set row height and column widths
        scheduleTable.setRowHeight(30);

        // Set up layout
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(titlePanel, BorderLayout.NORTH);

        this.add(topPanel, BorderLayout.NORTH);
        this.add(new JScrollPane(scheduleTable), BorderLayout.CENTER);

    }

    private void retrieveScheduleData() {
        try {
            // Establish a database connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://your_database_url", "your_username", "your_password");

            // SQL query to retrieve schedule data (modify as needed)
            String query = "SELECT time, monday, tuesday, wednesday, thursday, friday FROM schedule"; // Replace with your actual table name

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Process the result set and update the table model
            while (resultSet.next()) {
                String[] rowData = {
                        resultSet.getString("time"),
                        resultSet.getString("monday"),
                        resultSet.getString("tuesday"),
                        resultSet.getString("wednesday"),
                        resultSet.getString("thursday"),
                        resultSet.getString("friday")
                };
                tableModel.addRow(rowData);
            }

            // Close resources
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database error
            JOptionPane.showMessageDialog(this, "Error retrieving schedule data: " + e.getMessage());
        }
    }

    private void formatLabel(JLabel label) {
        label.setFont(new Font("Arial", Font.BOLD, 30));
        label.setForeground(new Color(70, 130, 180));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setAlignmentY(JLabel.CENTER);
        label.setBackground(Color.white);
        label.setOpaque(true);
    }
}
