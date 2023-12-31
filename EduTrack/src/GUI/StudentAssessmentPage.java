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
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StudentAssessmentPage extends StudentHomePage implements ActionListener {
    private final JLabel titleLabel;
    private final JPanel titlePanel;
    private JComboBox<String> courseDropdown;
    private JTable assessmentTable;
    private DefaultTableModel tableModel;
    public StudentAssessmentPage() {
        super("EduTrack - Assessment");
        Border border = BorderFactory.createEtchedBorder();

        // Title Label
        this.titleLabel = new JLabel("Assessment");
        formatLabel(titleLabel);
        titleLabel.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        // Title Panel
        this.titlePanel = new JPanel(new BorderLayout());
        titlePanel.setPreferredSize(new Dimension(0, 80));
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        // Sample course configurations
        String[] courses = {"Course A", "Course B", "Course C"};

        // Create course dropdown
        courseDropdown = new JComboBox<>(courses);
        formatComboBox(courseDropdown);
        courseDropdown.addActionListener(this);

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
        cellRenderer.setFont(new Font("Arial", Font.PLAIN, 30));
        assessmentTable.setDefaultRenderer(Object.class, cellRenderer);

        // Sample course configurations (you will replace this with data from the database)
        String[] course = fetchCourseNamesFromDatabase();

        // Create course dropdown
        courseDropdown = new JComboBox<>(courses);
        formatComboBox(courseDropdown);
        courseDropdown.addActionListener(this);

        // Set up layout
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(titlePanel, BorderLayout.NORTH);

        JPanel dropdownPanel = new JPanel();
        dropdownPanel.add(formatLabel(new JLabel("Select Course:")));
        dropdownPanel.add(courseDropdown);

        topPanel.add(dropdownPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(assessmentTable), BorderLayout.CENTER);
    }
    // Fetch course names from the database
    private String[] fetchCourseNamesFromDatabase() {
        List<String> courseList = new ArrayList<>();

        try {
            // Establish a database connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://your_database_url", "your_username", "your_password");

            // SQL query to retrieve course names
            String query = "SELECT course_name FROM your_course_table"; // Replace with your actual table name

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            // Add course names to the list
            while (resultSet.next()) {
                String courseName = resultSet.getString("course_name");
                courseList.add(courseName);
            }

            // Close resources
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching course names from the database: " + e.getMessage());
        }

        // Convert list to array
        return courseList.toArray(new String[0]);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == courseDropdown) {
            // Update table based on selected course
            updateTable((String) courseDropdown.getSelectedItem());
        }
    }
    private void updateTable(String selectedCourse) {
        // For simplicity, clear the table and add dummy data
        clearTable();

        if (selectedCourse.equals("Course A")) {
            addRow("Assessment", 45, calculateGrade(45));
            addRow("Final Exam", 35, calculateGrade(35));
        } else if (selectedCourse.equals("Course B")) {
            addRow("Assessment", 40, calculateGrade(40));
            addRow("Final Exam", 38, calculateGrade(38));
        } else if (selectedCourse.equals("Course C")) {
            addRow("Assessment", 48, calculateGrade(48));
            addRow("Final Exam", 42, calculateGrade(42));
        }
    }
    private void addRow(String assessmentType, int score, String letterGrade) {
        tableModel.addRow(new Object[]{assessmentType, score, letterGrade});
    }
    private void clearTable() {
        tableModel.setRowCount(0);
    }
    private JLabel formatLabel(JLabel label) {
        label.setFont(new Font("Arial", Font.BOLD, 30));
        label.setForeground(new Color(70, 130, 180));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setAlignmentY(JLabel.CENTER);
        label.setBackground(Color.white);
        label.setOpaque(true);
        return label;
    }
    private JComboBox<String> formatComboBox(JComboBox<String> comboBox) {
        comboBox.setFont(new Font("Arial", Font.PLAIN, 20));
        comboBox.setForeground(new Color(70, 130, 180));
        return comboBox;
    }
    private String calculateGrade(int score) {
        switch (score / 10) {
            case 10:
            case 9:
                return "A+";
            case 8:
                return "A";
            case 7:
                return "A-";
            case 6:
                return "B+";
            default:
                return "B";
        }
    }
}
