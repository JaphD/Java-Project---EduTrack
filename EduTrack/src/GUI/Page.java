package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
abstract class Page extends JFrame {
    protected final JFrame page;
    protected final String title;
    protected JPanel inputPanel;
    protected JLabel imageLabel = createImageLabel();
    private final int backgroundColorRed, backgroundColorGreen, backgroundColorBlue;
    private final int width, height;
    private final boolean resizable;
    protected String email;
    protected static final String ip = "192.168.0.200";
    protected GridBagConstraints constraints;
    Page(String title, boolean resizable, int width, int height, int backgroundColorRed, int backgroundColorGreen, int backgroundColorBlue) {
        this.page = new JFrame(title);
        this.title = title;
        this.resizable = resizable;
        this.width = width;
        this.height = height;
        this.backgroundColorRed = backgroundColorRed;
        this.backgroundColorGreen = backgroundColorGreen;
        this.backgroundColorBlue = backgroundColorBlue;

        initializePage();
    }
    private void initializePage() {
        page.setTitle(title);
        page.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        page.setLayout(new BorderLayout());
        if (resizable) {
            page.setSize(width, height);
        } else {
            setSize(Toolkit.getDefaultToolkit().getScreenSize());
            setExtendedState(JFrame.MAXIMIZED_BOTH);
        }
        page.setLocationRelativeTo(null); // to center on the screen
        page.getContentPane().setBackground(new Color(backgroundColorRed, backgroundColorGreen, backgroundColorBlue));
        page.setResizable(resizable);

        ImageIcon icon = createImageIcon("icon2.jpeg");
        if (icon != null) {
            page.setIconImage(icon.getImage());
        }
    }
    protected ImageIcon createImageIcon(String filename){
        URL imgUrl = getClass().getClassLoader().getResource(filename);
        if (imgUrl != null) {
            return new ImageIcon(imgUrl);
        } else {
            throw new IllegalArgumentException("Couldn't find file: " + filename);
        }
    }


    protected JLabel createImageLabel(){
        ImageIcon imageIcon = createImageIcon("icon2.png"); // Path to your image
        if (imageIcon != null) {
            this.imageLabel = new JLabel(imageIcon);
            imageLabel.setHorizontalAlignment(JLabel.CENTER);
            imageLabel.setVerticalAlignment(JLabel.CENTER);
            imageLabel.setOpaque(true);
            Image scaledImage = imageIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaledImage));
        }
        return imageLabel;
    }
    void addFormField(String labelText, JComponent field, String placeholder, int gridY) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridy = gridY;
        constraints.gridx = 0;
        constraints.insets = new Insets(5, 0, 5, 10); // Add spacing to the right

        JLabel label = new JLabel(labelText);
        inputPanel.add(label, constraints);

        constraints.gridx = 1;
        constraints.insets = new Insets(5, 0, 5, 0); // Add spacing to the left

        field.setToolTipText(placeholder);

        inputPanel.add(field, constraints);
    }
    protected boolean areNotNull(String username, String password) {
        return username != null && !username.trim().isEmpty() &&
                password != null && !password.trim().isEmpty();
    }
    protected boolean areNotNull(String username, String password, String securityKey) {
        return username != null && !username.trim().isEmpty() &&
                password != null && !password.trim().isEmpty() &&
                securityKey != null && !securityKey.trim().isEmpty();
    }
    void formatButton(JButton button) {
        button.setFocusable(false);
        button.setFont(new Font("Arial", Font.BOLD, 30));
        button.setForeground(Color.white);
        button.setBackground(new Color(70, 130, 180)); // Set color to a shade of blue
    }
    void formatMenuItem(JMenuItem menuItem) {
        menuItem.setFont(new Font("Arial", Font.PLAIN, 16));
        menuItem.setForeground(new Color(70, 130, 180));
        menuItem.setBackground(Color.WHITE);
        menuItem.setHorizontalAlignment(SwingConstants.LEADING);
    }
    void formatLabel(JLabel label) {
        label.setFont(new Font("Arial", Font.BOLD, 30));
        label.setForeground(new Color(70, 130, 180));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setAlignmentY(JLabel.CENTER);
        label.setBackground(Color.white);
        label.setOpaque(true);
    }
    protected void handleException(String message, Exception ex){
        JOptionPane.showMessageDialog(this, message + " : " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
    }
    protected void navigateToPage(Class<?> pageClass) {
        try {
            pageClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        this.dispose();
    }
    abstract void actionPerformed(ActionEvent e);
}



