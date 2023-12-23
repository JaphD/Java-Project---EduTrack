package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import javax.swing.ImageIcon;

abstract class Page extends JFrame{
    protected JFrame frame;
    private String title;
    private int backgroundColorRed, backgroundColorGreen, backgroundColorBlue;
    private int width, height;
    Page(String title,int width,int height, int backgroundColorRed, int backgroundColorGreen,int backgroundColorBlue) {
        this.frame = new JFrame(title);
        this.title = title;
        this.width = width;
        this.height = height;
        this.backgroundColorRed = backgroundColorRed;
        this.backgroundColorGreen = backgroundColorGreen;
        this.backgroundColorBlue = backgroundColorBlue;

        initializeFrame(false);
    }
    Page(String title, int backgroundColorRed, int backgroundColorGreen, int backgroundColorBlue)  {
        this.frame = new JFrame(title);
        this.title = title;
        this.backgroundColorRed = backgroundColorRed;
        this.backgroundColorGreen = backgroundColorGreen;
        this.backgroundColorBlue = backgroundColorBlue;

        setExtendedState();
        initializeFrame(true);
        }
    private void initializeFrame(boolean resizable){
        frame.setTitle(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null); // to center on the screen
        frame.getContentPane().setBackground(new Color(backgroundColorRed, backgroundColorGreen, backgroundColorBlue));
        frame.setResizable(resizable);

        ImageIcon icon = new ImageIcon("C:\\Users\\Japhe\\OneDrive\\Desktop\\Java\\EduTrack\\src\\GUI\\icon2.jpeg");
        frame.setIconImage(icon.getImage());
    }
    private void setExtendedState(){
            setSize(Toolkit.getDefaultToolkit().getScreenSize());
            setExtendedState(JFrame.MAXIMIZED_BOTH);
        }
    }



