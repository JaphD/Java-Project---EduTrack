package GUI;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InstructorAttendancePage extends InstructorHomePage {
    private JList<String> studentList;
    private DefaultListModel<String> listModel;

    InstructorAttendancePage() {
        super("Student Attendance");
        initializeComponents();
        retrieveStudentAttendance();
        addComponentsToFrame();
    }
    private void initializeComponents() {
        listModel = new DefaultListModel<>();
        studentList = new JList<>(listModel);
        studentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    private void retrieveStudentAttendance() {
        try (Connection connection = establishConnection()) {
            String query = "SELECT student_name FROM attendance";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    String studentName = resultSet.getString("student_name");
                    listModel.addElement(studentName);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error retrieving student attendance.");
        }
    }
    private Connection establishConnection() throws SQLException {
        String url = "jdbc:mysql://your-database-url";
        String user = "your-username";
        String password = "your-password";
        return DriverManager.getConnection(url, user, password);
    }

    private void addComponentsToFrame() {
        JScrollPane scrollPane = new JScrollPane(studentList);
        add(scrollPane);
    }
}


