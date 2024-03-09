package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentQuizRoomPage extends StudentHomePage implements ActionListener {
    // Fields
    private String[] questions = {
            "Which company created Java?",
            "Which year was java created?",
            "What was Java originally called?",
            "Who is credited with creating java?"
    };
    private String[][] options = {
            {"Sun Microsystems", "Starbucks", "Microsoft", "Alphabet"},
            {"1989", "1996", "1972", "1492"},
            {"Apple", "Latte", "Oak", "Koffiking"},
            {"Steve Jobs", "Bill Gates", "James Gosling", "Matt Damon"}
    };
    private char[] answers = {'A','B','C','C'};
    private char [] choices = new char[questions.length];
    private char answer;
    private int counter, index, correct_guesses=0, total_questions=questions.length, result, seconds=10;

    // Components
    private JTextField textfield = new JTextField();
    private JTextArea textarea= new JTextArea();
    private JButton buttonA=new JButton(), buttonB=new JButton(), buttonC= new JButton(), buttonD=new JButton(), previousButton=new JButton(), nextButton=new JButton(),
            submitButton=new JButton(), revealAnswer= new JButton();
    private JLabel answer_labelA=new JLabel(), answer_labelB=new JLabel(), answer_labelC=new JLabel(), answer_labelD=new JLabel(), time_label=new JLabel(), seconds_left=new JLabel();
    private JTextField number_right= new JTextField(), percentage= new JTextField();
    private int second=60, minute=10, delay = 1000; //milliseconds
    Timer timer = new Timer(1000, (ActionEvent e) -> {
        seconds--;
        seconds_left.setText(String.valueOf(seconds));
        if (seconds <= 0) {
            results();
            revealAnswer.setEnabled(true);
            submitButton.setEnabled(false);
        }
    });
    StudentQuizRoomPage() {
        // Next Question Button
        super("Student Quiz Room");
        // Frame setup
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(675,675);
        getContentPane().setBackground(new Color(211, 211, 211));
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);


        textfield.setBounds(0,0,650,50);
        textfield.setBackground(new Color(25,25,25));
        textfield.setForeground((new Color(70, 130, 180)));
        textfield.setFont(new Font("Arial", Font.PLAIN, 30));
        textfield.setBorder(BorderFactory.createBevelBorder(1));
        textfield.setHorizontalAlignment(JTextField.CENTER);
        textfield.setEditable(false);

        textarea.setBounds(0,50,650,50);
        textarea.setLineWrap(true);
        textarea.setWrapStyleWord(true);
        textarea.setBackground(new Color(25,25,25));
        textarea.setForeground((new Color(70, 130, 180)));
        textarea.setFont(new Font("Arial", Font.PLAIN, 25));
        textarea.setBorder(BorderFactory.createBevelBorder(1));
        textarea.setEditable(false);

        nextButton.setBounds(400,550,100,30);
        nextButton.setFont(new Font("Arial", Font.BOLD, 15));
        nextButton.setFocusable(false);
        nextButton.addActionListener(this);
        nextButton.setText("Next");

        submitButton.setBounds(400,580,100,30);
        submitButton.setFont(new Font("Arial", Font.BOLD, 15));
        submitButton.setFocusable(false);
        submitButton.addActionListener(this);
        submitButton.setText("Submit");

        revealAnswer.setBounds(400,490,100,30);
        revealAnswer.setFont(new Font("Arial", Font.BOLD, 15));
        revealAnswer.setFocusable(false);
        revealAnswer.addActionListener(this);
        revealAnswer.setText("reveal");

        previousButton.setBounds(400,520,100,30);
        previousButton.setFont(new Font("Arial", Font.BOLD, 15));
        previousButton.setFocusable(false);
        previousButton.addActionListener(this);
        previousButton.setText("Previous");


        answer_labelA.setBounds(125,100,500,100);
        answer_labelA.setBackground(new Color(50,50,50));
        answer_labelA.setForeground(new Color(70, 130, 180));
        answer_labelA.setFont(new Font("Arial", Font.PLAIN, 35));

        answer_labelB.setBounds(125,200,500,100);
        answer_labelB.setBackground(new Color(50,50,50));
        answer_labelB.setForeground(new Color(70, 130, 180));
        answer_labelB.setFont(new Font("Arial", Font.PLAIN, 35));

        answer_labelC.setBounds(125,300,500,100);
        answer_labelC.setBackground(new Color(50,50,50));
        answer_labelC.setForeground(new Color(70, 130, 180));
        answer_labelC.setFont(new Font("Arial", Font.PLAIN, 35));

        answer_labelD.setBounds(125,400,500,100);
        answer_labelD.setBackground(new Color(50,50,50));
        answer_labelD.setForeground(new Color(70, 130, 180));
        answer_labelD.setFont(new Font("Arial", Font.PLAIN, 35));

        seconds_left.setBounds(535,510,100,100);
        seconds_left.setBackground(new Color(25,25,25));
        seconds_left.setForeground(new Color(70, 130, 180));
        seconds_left.setFont(new Font("Arial", Font.BOLD,60));
        seconds_left.setBorder(BorderFactory.createBevelBorder(1));
        seconds_left.setOpaque(true);
        seconds_left.setHorizontalAlignment(JTextField.CENTER);
        seconds_left.setText(String.valueOf(seconds));

        time_label.setBounds(535, 475,100,25);
        time_label.setBackground(new Color(50,50,50));
        time_label.setForeground(new Color(70, 130, 180));
        time_label.setFont(new Font("Arial", Font.BOLD,20));
        time_label.setBorder(BorderFactory.createBevelBorder(1));
        time_label.setOpaque(true);
        time_label.setHorizontalAlignment(JTextField.CENTER);
        time_label.setText("Timer");

        number_right.setBounds(450,225,200,100);
        number_right.setBackground(new Color(25,25,25));
        number_right.setForeground(new Color(70, 130, 180));
        number_right.setFont(new Font("Arial", Font.BOLD,50));
        number_right.setBorder(BorderFactory.createBevelBorder(1));
        number_right.setEditable(false);

        percentage.setBounds(450,325,200,100);
        percentage.setBackground(new Color(50,50,50));
        percentage.setForeground(new Color(70, 130, 180));
        percentage.setFont(new Font("Arial", Font.BOLD, 50));
        percentage.setBorder(BorderFactory.createBevelBorder(1));
        percentage.setHorizontalAlignment(JTextField.CENTER);
        percentage.setEditable(false);
        setButtons();


        ImageIcon icon = createImageIcon("icon2.jpeg");
        if (icon != null) {
            setIconImage(icon.getImage());
        }

        add(time_label);
        add(revealAnswer);
        add(seconds_left);
        add(answer_labelA);
        add(answer_labelB);
        add(answer_labelC);
        add(answer_labelD);
        add(previousButton);
        add(submitButton);
        add(nextButton);
        add(buttonA);
        add(buttonB);
        add(buttonC);
        add(buttonD);
        add(textarea);
        add(textfield);
        setVisible(true);
        nextQuestion();
        timer.start();
        revealAnswer.setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == nextButton) {
                nextQuestion();
            }
            if (e.getSource().equals(buttonA)) {
                answer = 'A';
                if (answer == answers[index]) {
                    choices[index] = answer;
                }
                nextQuestion();
            }
            if (e.getSource() == buttonB) {
                answer = 'B';
                if (answer == answers[index]) {
                    choices[index] = answer;
                }
                nextQuestion();
            }
            if (e.getSource() == buttonC) {
                answer = 'C';
                if (answer == answers[index]) {
                    choices[index] = answer;
                }
                nextQuestion();
            }

            if (e.getSource() == buttonD) {
                answer = 'D';
                if (answer == answers[index]) {
                    choices[index] = answer;
                }
                nextQuestion();
            }
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(this, "You have reached the end of the quiz", "Message", JOptionPane.ERROR_MESSAGE);
        }
        if (e.getSource() == previousButton) {
            if (index > 0) {
                index--;
                textfield.setText("Question " + (index) + "/" + questions.length);
                textarea.setText(questions[index]);
                answer_labelA.setText(options[index][0]);
                answer_labelB.setText(options[index][1]);
                answer_labelC.setText(options[index][2]);
                answer_labelD.setText(options[index][3]);
                nextButton.setEnabled(true);
                resetText();
            }
        }
        if (e.getSource() == submitButton) {
            results();
            revealAnswer.setEnabled(true);
        }
        if (e.getSource() == revealAnswer) {

            if (answers[index] == 'A') {
                answer_labelA.setForeground(new Color(0, 250, 80));
                answer_labelA.setFont(new Font("Arial", Font.PLAIN, 35));
            }
            if (answers[index] == 'B') {
                answer_labelB.setForeground(new Color(0, 250, 80));
                answer_labelB.setFont(new Font("Arial", Font.PLAIN, 35));
            }
            if (answers[index] == 'C') {
                answer_labelC.setForeground(new Color(0, 250, 80));
                answer_labelC.setFont(new Font("Arial", Font.PLAIN, 35));
            }
            if (answers[index] == 'D') {
                answer_labelD.setForeground(new Color(0, 250, 80));
                answer_labelD.setFont(new Font("Arial", Font.PLAIN, 35));

            }
        }

    }
    public void nextQuestion() {
        if (index < questions.length){
            index++;
            textfield.setText("Question " + (index) + "/" + questions.length);
            textarea.setText(questions[index-1]);
            answer_labelA.setText(options[index-1][0]);
            answer_labelB.setText(options[index-1][1]);
            answer_labelC.setText(options[index-1][2]);
            answer_labelD.setText(options[index-1][3]);
            nextButton.setEnabled(true);
            resetText();
        } else if (index == questions.length) {
            nextButton.setEnabled(false);

        }
    }
    public void setButtons(){
        Color buttonColor = new Color(70,130,180);
        Font buttonFont = new Font("Arial", Font.BOLD, 35);

        buttonA.setBounds(0,100,100,100);
        buttonA.setBackground(buttonColor);
        buttonA.setForeground(Color.WHITE);
        buttonA.setFont(buttonFont);
        buttonA.setFocusable(false);
        buttonA.addActionListener(this);
        buttonA.setText("A");

        // Button B
        buttonB.setBounds(0, 200, 100, 100);
        buttonB.setBackground(buttonColor);
        buttonB.setForeground(Color.WHITE); // White text
        buttonB.setFont(buttonFont);
        buttonB.setFocusable(false);
        buttonB.addActionListener(this);
        buttonB.setText("B");

        // Button C
        buttonC.setBounds(0, 300, 100, 100);
        buttonC.setBackground(buttonColor);
        buttonC.setForeground(Color.WHITE); // White text
        buttonC.setFont(buttonFont);
        buttonC.setFocusable(false);
        buttonC.addActionListener(this);
        buttonC.setText("C");

        // Button D
        buttonD.setBounds(0, 400, 100, 100);
        buttonD.setBackground(buttonColor);
        buttonD.setForeground(Color.WHITE); // White text
        buttonD.setFont(buttonFont);
        buttonD.setFocusable(false);
        buttonD.addActionListener(this);
        buttonD.setText("D");
    }
    public void resetText()
    {
        answer_labelA.setForeground(new Color(70, 130, 180));
        answer_labelB.setForeground(new Color(70, 130, 180));
        answer_labelC.setForeground(new Color(70, 130, 180));
        answer_labelD.setForeground(new Color(70, 130, 180));
    }
    public void results() {
        buttonA.setEnabled(false);
        buttonB.setEnabled(false);
        buttonC.setEnabled(false);
        buttonD.setEnabled(false);
        submitButton.setEnabled(false);
        timer.stop();

        for(int j = 0; j<questions.length; j++)
        {
            if(choices[j]==answers[j])
            {
                counter++;
            }
        }

        result=(int)((counter/(double)total_questions)*100);
        textfield.setText("Results");
        textarea.setText("");
        answer_labelA.setText("");
        answer_labelB.setText("");
        answer_labelC.setText("");
        answer_labelD.setText("");

        number_right.setText("("+counter+"/"+total_questions+")");
        percentage.setText(result+"%");

        add(number_right);
        add(percentage);
    }
}

