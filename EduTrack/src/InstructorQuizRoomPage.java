package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InstructorQuizRoomPage /*extends InstructorHomePage */ extends JFrame{

    private JTextField questionInput;
    private JTextField optionA;
    private JTextField optionB;
    private JTextField optionC;
    private JTextField optionD;
    private JLabel questionLabel;
    private JLabel labelA;
    private JLabel labelB;
    private JLabel labelC;
    private JLabel labelD;
    private JPanel questionPanel;
    private JPanel topPanel;
    private JButton submitButton;

    public InstructorQuizRoomPage() {
        super("EduTrack - Instructor Quiz Room");
        Font font = new Font("Arial", Font.BOLD, 16);

        questionLabel = new JLabel("Question: ");
        questionInput = new JTextField(50);
        labelA = new JLabel("A");
        optionA = new JTextField(50);
        labelB = new JLabel("B");
        optionB = new JTextField(50);
        labelC = new JLabel("C");
        optionC = new JTextField(50);
        labelD = new JLabel("D");
        optionD = new JTextField(50);

        questionLabel.setFont(font);
        labelA.setFont(font);
        labelB.setFont(font);
        labelC.setFont(font);
        labelD.setFont(font);
        questionInput.setFont(font);
        optionA.setFont(font);
        optionB.setFont(font);
        optionC.setFont(font);
        optionD.setFont(font);

        Border border = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2);
        questionInput.setBorder(border);
        optionA.setBorder(border);
        optionB.setBorder(border);
        optionC.setBorder(border);
        optionD.setBorder(border);

        topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1, 2, 5, 5));
        topPanel.add(questionLabel);
        topPanel.add(questionInput);

        topPanel.setBackground(new Color(70, 130, 180));

        questionPanel = new JPanel();
        questionPanel.setLayout(new GridLayout(4, 2, 5, 5));
        questionPanel.add(labelA);
        questionPanel.add(optionA);
        questionPanel.add(labelB);
        questionPanel.add(optionB);
        questionPanel.add(labelC);
        questionPanel.add(optionC);
        questionPanel.add(labelD);
        questionPanel.add(optionD);

        questionPanel.setBackground(Color.white);

        questionLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        labelA.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        submitButton = new JButton("Submit");
        submitButton.setFont(font);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String question = questionInput.getText();
                String optionAValue = optionA.getText();
                String optionBValue = optionB.getText();
                String optionCValue = optionC.getText();
                String optionDValue = optionD.getText();

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydatabase", "username", "password");

                    String query = "INSERT INTO questions (question, optionA, optionB, optionC, optionD) VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, question);
                    preparedStatement.setString(2, optionAValue);
                    preparedStatement.setString(3, optionBValue);
                    preparedStatement.setString(4, optionCValue);
                    preparedStatement.setString(5, optionDValue);
                    preparedStatement.executeUpdate();


                    preparedStatement.close();
                    connection.close();


                    //JOptionPane.showMessageDialog(InstructorQuestionInput.this, "Question added to database successfully!");

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex1) {

                    //JOptionPane.showMessageDialog(InstructorQuestionInput.this, "Error adding question to database: " + ex1.getMessage());
                }
            }
        });

        submitButton.setBackground(new Color(70, 130, 180));
        add(topPanel, BorderLayout.NORTH);
        add(questionPanel, BorderLayout.CENTER);
        add(submitButton, BorderLayout.SOUTH);
        //setTitle("Instructor Question Input");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        }
    }

