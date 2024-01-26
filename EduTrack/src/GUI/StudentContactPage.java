package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class StudentContactPage extends StudentHomePage  {
    private final JLabel titleLabel;
    private final JPanel titlePanel;
    private final JTable dataTable;

    StudentContactPage() {
        super("Contact");

        Border border = BorderFactory.createEtchedBorder();

        // Title Label
        titleLabel = new JLabel("Contact");
        formatLabel(titleLabel);
        titleLabel.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        // Title Panel
        titlePanel = new JPanel(new BorderLayout());
        titlePanel.setPreferredSize(new Dimension(0, 80));
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        // Set up layout
        setLayout(new BorderLayout());

        // Create a table model with no data
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Information");

        // Create the JTable with the model
        dataTable = new JTable(tableModel);
        formatTable(dataTable);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(titlePanel, BorderLayout.NORTH);
        topPanel.add(new JScrollPane(dataTable), BorderLayout.CENTER);

        add(topPanel, BorderLayout.CENTER);

        // Display data from the database
        displayDataFromDatabase();
    }
    private void displayDataFromDatabase() {
        try {
            // Establish a database connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://instructorform", "your_username", "your_password");

            // SQL query to retrieve message data (modify as needed)
            String query = "SELECT message_text FROM instructor"; // Replace with your actual table name

            // Assuming "message_text" is the column in which the message is stored
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                // Add retrieved data to the table model
                DefaultTableModel tableModel = (DefaultTableModel) dataTable.getModel();
                while (resultSet.next()) {
                    String message = resultSet.getString("email");
                    tableModel.addRow(new Object[]{message});
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error retrieving data from the database: " + ex.getMessage());
        }
    }
    private void formatTable(JTable table) {
        // Your formatting code for JTable goes here
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
