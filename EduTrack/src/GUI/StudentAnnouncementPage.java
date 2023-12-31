package GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Vector;

public class StudentAnnouncementPage extends StudentHomePage implements MouseListener {
    private final JLabel titleLabel;
    private final JPanel titlePanel;
    private final JPanel imagePanel;
    private final JPanel tablePanel;
    private final JPanel textPanel;

    public StudentAnnouncementPage() {
        super("Announcement");

        Border border = BorderFactory.createEtchedBorder();

        // Title Label
        this.titleLabel = new JLabel("Announcement");
        formatLabel(titleLabel);
        titleLabel.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        // Title Panel
        this.titlePanel = new JPanel(new BorderLayout());
        titlePanel.setPreferredSize(new Dimension(0, 80));
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        // Image Panel
        this.imagePanel = new JPanel();
        imagePanel.addMouseListener(this);
        imagePanel.setVisible(false);
        displayImage(imagePanel);

        // Table Panel
        this.tablePanel = new JPanel();
        tablePanel.addMouseListener(this);
        tablePanel.setVisible(false);
        displayTable(tablePanel);

        // Text Panel
        this.textPanel = new JPanel();
        textPanel.addMouseListener(this);
        textPanel.setVisible(false);
        displayText(textPanel);

        // Set up layout
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(titlePanel, BorderLayout.NORTH);

        this.add(topPanel, BorderLayout.NORTH);
        this.add(imagePanel, BorderLayout.WEST);
        this.add(tablePanel, BorderLayout.CENTER);
        this.add(textPanel, BorderLayout.EAST);
    }

    // ... (formatLabel, displayImage, displayText methods)
    private void formatLabel(JLabel label) {
        label.setFont(new Font("Arial", Font.BOLD, 30));
        label.setForeground(new Color(70, 130, 180));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setAlignmentY(JLabel.CENTER);
        label.setBackground(Color.white);
        label.setOpaque(true);
    }
    private void displayImage(JPanel panel) {
        try {
            // Load an example image (replace with your image URL)
            URL imageUrl = new URL("https://via.placeholder.com/200");
            BufferedImage image = ImageIO.read(imageUrl);
            JLabel imageLabel = new JLabel(new ImageIcon(image));
            panel.add(imageLabel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayText(JPanel panel) {
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        // Fetch text data from the database and set it in the text area
        String textData = fetchTextFromDatabase(); // Implement this method
        textArea.setText(textData);

        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane);
    }

    // Example method to fetch text data from the database
    private String fetchTextFromDatabase() {
        // Implement logic to fetch text data from the database
        // Replace the return statement with your actual logic
        return "This is a sample text.\nYou can add more information here.";
    }
    private void displayTable(JPanel panel) {
        try {
            // Establish a database connection (replace with your connection details)
            Connection connection = DriverManager.getConnection("jdbc:mysql://your_database_url", "your_username", "your_password");

            // Create a statement
            Statement statement = connection.createStatement();

            // Execute a query to retrieve data from the database (replace with your query)
            String query = "SELECT * FROM your_table";
            ResultSet resultSet = statement.executeQuery(query);

            // Get column names
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            Vector<String> columnNames = new Vector<>();
            for (int i = 1; i <= columnCount; i++) {
                columnNames.add(metaData.getColumnName(i));
            }

            // Get data rows
            Vector<Vector<Object>> data = new Vector<>();
            while (resultSet.next()) {
                Vector<Object> row = new Vector<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(resultSet.getObject(i));
                }
                data.add(row);
            }

            // Create a table
            JTable table = new JTable(new DefaultTableModel(data, columnNames));
            JScrollPane scrollPane = new JScrollPane(table);
            panel.add(scrollPane);

            // Close resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching table data: " + e.getMessage());
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == imagePanel) {
            imagePanel.setVisible(!imagePanel.isVisible());
        } else if (e.getSource() == tablePanel) {
            tablePanel.setVisible(!tablePanel.isVisible());
        } else if (e.getSource() == textPanel) {
            textPanel.setVisible(!textPanel.isVisible());
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    // ... (other MouseListener methods)
}
