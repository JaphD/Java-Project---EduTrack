package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InstructorStudentListPage extends InstructorHomePage {
    private JTable studentTable;
    private DefaultTableModel tableModel;

    InstructorStudentListPage() {
        super("Student List");
        initializeComponents();
        retrieveStudentData();
        addComponentsToFrame();
    }

    private void initializeComponents() {
        tableModel = new DefaultTableModel();
        studentTable = new JTable(tableModel);
        tableModel.addColumn("Student ID");

        // You can add more columns based on your database table structure
        // For example, tableModel.addColumn("Name");

        studentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void retrieveStudentData() {
        try (Connection connection = establishConnection()) {
            String query = "SELECT student_id FROM students";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    int studentID = resultSet.getInt("student_id");
                    tableModel.addRow(new Object[]{studentID});
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error retrieving student data.");
        }
    }

    private Connection establishConnection() throws SQLException {
        String url = "jdbc:mysql://your-database-url";
        String user = "your-username";
        String password = "your-password";
        return DriverManager.getConnection(url, user, password);
    }

    private void addComponentsToFrame() {
        JScrollPane scrollPane = new JScrollPane(studentTable);
        add(scrollPane);
    }
}


