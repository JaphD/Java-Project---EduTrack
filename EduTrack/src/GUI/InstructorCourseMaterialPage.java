package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class InstructorCourseMaterialPage extends InstructorHomePage implements ActionListener {
    private final JLabel titleLabel;
    private final JPanel titlePanel;
    private JButton uploadButton;
    private JFileChooser fileChooser;

    InstructorCourseMaterialPage() {
        super("Course Material");

        Border border = BorderFactory.createEtchedBorder();

        // Title Label
        this.titleLabel = new JLabel("Course Material");
        formatLabel(titleLabel);
        titleLabel.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        // Title Panel
        this.titlePanel = new JPanel(new BorderLayout());
        titlePanel.setPreferredSize(new Dimension(0, 80));
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        // Upload Button
        uploadButton = new JButton("Upload");
        formatSmallButton(uploadButton); // Changed to formatSmallButton
        uploadButton.addActionListener(this);

        // Set up layout
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(titlePanel, BorderLayout.NORTH);

        topPanel.add(uploadButton, BorderLayout.SOUTH); // Removed dropdownPanel

        add(topPanel, BorderLayout.NORTH);

        setVisible(true);

        // File chooser initialization
        fileChooser = new JFileChooser();
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

    private void formatSmallButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 16)); // Changed font size
        button.setForeground(new Color(255, 255, 255));
        button.setBackground(new Color(70, 130, 180));
        button.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Reduced padding
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == uploadButton) {
            int result = fileChooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();

                // TODO: Upload the file to the database (you need to implement this part)
                // Example: uploadToDatabase(selectedFile);
                JOptionPane.showMessageDialog(this, "File uploaded successfully");
            }
        }
    }
}



