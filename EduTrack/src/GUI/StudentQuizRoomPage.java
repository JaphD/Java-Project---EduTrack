package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;




public class StudentQuizRoomPage extends StudentHomePage implements ActionListener {
    String[] questions = {
            "Which company created Java?",
            "Which year was java created?",
            "What was Java originally called?",
            "Who is credited with creating java?"
    };
    String[][] options = {
            {"Sun Microsystems", "Starbucks", "Microsoft", "Alphabet"},
            {"1989", "1996", "1972", "1492"},
            {"Apple", "Latte", "Oak", "Koffiking"},
            {"Steve Jobs", "Bill Gates", "James Gosling", "Matt Damon"}
    };
    char[] answers = {'A', 'B', 'C', 'C'};
    char[] choices = new char[questions.length];
    char guess;
    char answer;
    int counter;
    int index;
    int correct_guesses=0;
    int total_questions=questions.length;
    int result;
    int seconds=10;
    JTextField textfield = new JTextField();
    JTextArea textarea= new JTextArea();
    JButton buttonA=new JButton();
    JButton buttonB=new JButton();
    JButton buttonC= new JButton();
    JButton buttonD=new JButton();
    JButton previousButton=new JButton();
    JButton nextButton=new JButton();
    JButton submitButton=new JButton();
    JLabel answer_labelA=new JLabel();
    JLabel answer_labelB=new JLabel();
    JLabel answer_labelC=new JLabel();
    JLabel answer_labelD=new JLabel();
    JLabel time_label=new JLabel();
    JLabel seconds_left=new JLabel();
    JTextField number_right= new JTextField();
    JTextField percentage= new JTextField();
    ButtonGroup buttonGroup = new ButtonGroup();
    JRadioButton option1 = new JRadioButton("Option 1");
    JRadioButton option2 = new JRadioButton("Option 2");
    JRadioButton option3 = new JRadioButton("Option 3");
    JRadioButton option4=new JRadioButton("Option 4");

    public StudentQuizRoomPage() {
        super("EduTrack - Quiz Room");

        setSize(670,670);
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

        previousButton.setBounds(400,520,100,30);
        previousButton.setFont(new Font("Arial", Font.BOLD, 15));
        previousButton.setFocusable(false);
        previousButton.addActionListener(this);
        previousButton.setText("Previous");
        buttonGroup.add(option1);
        buttonGroup.add(option2);
        buttonGroup.add(option3);
        buttonGroup.add(option4);


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

        number_right.setBounds(225,225,200,100);
        number_right.setBackground(new Color(25,25,25));
        number_right.setForeground(new Color(70, 130, 180));
        number_right.setFont(new Font("Arial", Font.BOLD,50));
        number_right.setBorder(BorderFactory.createBevelBorder(1));
        number_right.setEditable(false);

        percentage.setBounds(225,325,200,100);
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
        add(seconds_left);
        add(answer_labelA);
        add(answer_labelB);
        add(answer_labelC);
        add(answer_labelD);
        add(previousButton);
        add(submitButton);
        add(nextButton);
        add(option1);
        add(option2);
        add(option3);
        add(option4);
        add(textarea);
        add(textfield);
        setVisible(true);
        add(option1);
        nextQuestion();
    }
    private void initializeUI(){

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==nextButton){
            if (index < questions.length) {
                index++;
                textfield.setText("Question " + (index + 1)+"/"+ questions.length);
                textarea.setText(questions[index-1]);
                answer_labelA.setText(options[index][0]);
                answer_labelB.setText(options[index][1]);
                answer_labelC.setText(options[index][2]);
                answer_labelD.setText(options[index][3]);
            }
            else {
                results();

            }
        }
        if (e.getSource() == option1) {
            answer='A';
            if (answer == answers[index]) {
                correct_guesses++;
                choices[index]= answer;
            }
            nextQuestion();
        }
        if (e.getSource() == option2) {
            answer = 'B';
            if (answer == answers[index]) {
                correct_guesses++;
                choices[index]= answer;
            }
            nextQuestion();
        }
        if (e.getSource() == option3) {
            answer = 'C';
            if (answer == answers[index]) {
                correct_guesses++;
                choices[index]= answer;
            }
            nextQuestion();
        }

        if (e.getSource() == option4) {
            answer = 'D';
            if (answer == answers[index]) {
                choices[index]= answer;
            }
            nextQuestion();
        }


        if(e.getSource()==previousButton){
            if (index>0) {
                index--;
                textfield.setText("Question " + (index+1)+"/"+ questions.length);
                textarea.setText(questions[index]);
                answer_labelA.setText(options[index][0]);
                answer_labelB.setText(options[index][1]);
                answer_labelC.setText(options[index][2]);
                answer_labelD.setText(options[index][3]);
            }
        }
        if(e.getSource()==submitButton){
            results();
        }
    }
    public void nextQuestion(){
        if(index<questions.length-1) {
            index++;
            textfield.setText("Question " + (index) + "/" + questions.length);
            textarea.setText(questions[index]);
            answer_labelA.setText(options[index][0]);
            answer_labelB.setText(options[index][1]);
            answer_labelC.setText(options[index][2]);
            answer_labelD.setText(options[index][3]);
        }
        else{
            option1.setEnabled(false);
            option2.setEnabled(false);
            option3.setEnabled(false);
            option4.setEnabled(false);
            results();
        }
    }
    public void setButtons(){
        option1.setBounds(0,100,100,100);
        option1.setFont(new Font("Arial", Font.BOLD, 35));
        option1.setFocusable(false);
        option1.addActionListener(this);
        option1.setText("A");


        option2.setBounds(0,200,100,100);
        option2.setFont(new Font("Arial", Font.BOLD, 35));
        option2.setFocusable(false);
        option2.addActionListener(this);
        option2.setText("B");


        option3.setBounds(0,300,100,100);
        option3.setFont(new Font("Arial", Font.BOLD, 35));
        option3.setFocusable(false);
        option3.addActionListener(this);
        option3.setText("C");

        option4.setBounds(0,400,100,100);
        option4.setFont(new Font("Arial", Font.BOLD, 35));
        option4.setFocusable(false);
        option4.addActionListener(this);
        option4.setText("D");

    }
    public void results() {
        buttonA.setEnabled(false);
        buttonB.setEnabled(false);
        buttonC.setEnabled(false);
        buttonD.setEnabled(false);
        submitButton.setEnabled(false);

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