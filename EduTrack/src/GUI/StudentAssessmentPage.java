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

        // Create table model with no data
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Assessment Type");
        tableModel.addColumn("Score");
        tableModel.addColumn("Grade");

        // Create the JTable with the model
        assessmentTable = new JTable(tableModel);
        formatTable(assessmentTable);

        // Set row height and column widths
        assessmentTable.setRowHeight(30);
        assessmentTable.getColumnModel().getColumn(0).setPreferredWidth(200);
        assessmentTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        assessmentTable.getColumnModel().getColumn(2).setPreferredWidth(150);

        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setFont(new Font("Arial", Font.PLAIN, 30));
        assessmentTable.setDefaultRenderer(Object.class, cellRenderer);

        // Create course dropdown
        courseDropdown = new JComboBox<>();
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

        // Fetch and populate course names from the database
        populateCourseDropdown();
    }

    private void populateCourseDropdown() {
        // Fetch course names from the database
        String[] courseNames = fetchCourseNamesFromDatabase();

        // Add course names to the dropdown
        for (String courseName : courseNames) {
            courseDropdown.addItem(courseName);
        }

        // Set default selection (if any)
        if (courseNames.length > 0) {
            courseDropdown.setSelectedIndex(0);
            // Update table based on the selected course
            updateTable(courseNames[0]);
        }
    }

    private String[] fetchCourseNamesFromDatabase() {
        List<String> courseList = new ArrayList<>();

        try {
            // Establish a database connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://instructorform", "your_username", "your_password");

            // SQL query to retrieve course names
            String query = "SELECT course_name FROM your_course_table"; // Replace with your actual table name

            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                // Add course names to the list
                while (resultSet.next()) {
                    String courseName = resultSet.getString("course_name");
                    courseList.add(courseName);
                }
            }
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
        // Clear the table
        clearTable();

        // Fetch assessment data for the selected course from the database
        List<AssessmentData> assessmentDataList = fetchAssessmentDataFromDatabase(selectedCourse);

        // Add fetched data to the table
        for (AssessmentData assessmentData : assessmentDataList) {
            addRow(assessmentData.getAssessmentType(), assessmentData.getScore(), assessmentData.getGrade());
        }
    }
    private List<AssessmentData> fetchAssessmentDataFromDatabase(String selectedCourse) {
        List<AssessmentData> assessmentDataList = new ArrayList<>();

        try {
            // Establish a database connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://instructorform", "your_username", "your_password");

            // SQL query to retrieve assessment data based on the selected course
            String query = "SELECT assessment_type, score, grade FROM your_assessment_table WHERE course_name = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, selectedCourse);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    // Add assessment data to the list
                    while (resultSet.next()) {
                        String assessmentType = resultSet.getString("assessment_type");
                        int score = resultSet.getInt("score");
                        String grade = resultSet.getString("grade");

                        assessmentDataList.add(new AssessmentData(assessmentType, score, grade));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching assessment data from the database: " + e.getMessage());
        }
        return assessmentDataList;
    }

    private void addRow(String assessmentType, int score, String letterGrade) {
        tableModel.addRow(new Object[]{assessmentType, score, letterGrade});
    }

    private void clearTable() {
        tableModel.setRowCount(0);
    }

    private class AssessmentData {
        private final String assessmentType;
        private final int score;
        private final String grade;
        public AssessmentData(String assessmentType, int score, String grade) {
            this.assessmentType = assessmentType;
            this.score = score;
            this.grade = grade;
        }
        public String getAssessmentType() {
            return assessmentType;
        }
        public int getScore() {
            return score;
        }
        public String getGrade() {
            return grade;
        }
    }
    private void formatTable(JTable table) {
        // Your formatting code for JTable goes here
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
}
