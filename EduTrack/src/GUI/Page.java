package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.net.URL;
import javax.swing.ImageIcon;

abstract class Page extends JFrame {
    protected JFrame page;
    private String title;
    private int backgroundColorRed, backgroundColorGreen, backgroundColorBlue;
    private int width, height;
    Page(String title, int width, int height, int backgroundColorRed, int backgroundColorGreen, int backgroundColorBlue) {
        this.page = new JFrame(title);
        this.title = title;
        this.width = width;
        this.height = height;
        this.backgroundColorRed = backgroundColorRed;
        this.backgroundColorGreen = backgroundColorGreen;
        this.backgroundColorBlue = backgroundColorBlue;

        initializePage(false);
    }
    Page(String title, int backgroundColorRed, int backgroundColorGreen, int backgroundColorBlue) {
        this.page = new JFrame(title);
        this.title = title;
        this.backgroundColorRed = backgroundColorRed;
        this.backgroundColorGreen = backgroundColorGreen;
        this.backgroundColorBlue = backgroundColorBlue;

        setExtendedState();
        initializePage(true);
    }
    private void initializePage(boolean resizable) {
        page.setTitle(title);
        page.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        page.setLayout(new BorderLayout());
        page.setSize(width, height);
        page.setLocationRelativeTo(null); // to center on the screen
        page.getContentPane().setBackground(new Color(backgroundColorRed, backgroundColorGreen, backgroundColorBlue));
        page.setResizable(resizable);
        
        ImageIcon icon = createImageIcon("icon2.jpeg");
        if (icon != null) {
            page.setIconImage(icon.getImage());
        }
    }
    private void setExtendedState() {
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
    protected ImageIcon createImageIcon(String filename){
        URL imgUrl = getClass().getClassLoader().getResource(filename);
        if (imgUrl != null) {
            return new ImageIcon(imgUrl);
        } else {
            System.err.println("Couldn't find file: " + filename);
            return null;
        }
    }
}



