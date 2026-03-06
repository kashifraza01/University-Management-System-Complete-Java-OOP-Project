package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.TimerTask;

public class Project extends JFrame implements ActionListener {

    private ImageIcon[] sliderImages;
    private int currentImageIndex = 0;
    private int nextImageIndex = 1;
    private float alpha = 1.0f;
    private java.util.Timer fadeTimer;
    private final int FADE_DELAY = 40;
    private final int FADE_DURATION = 1000;
    private ImagePanel imagePanel;

    // Colors and font
    private final Color MENU_BG = new Color(30, 30, 47);
    private final Color TEXT_COLOR = new Color(224, 224, 224);
    private final Color HOVER_COLOR = new Color(58, 58, 85);
    private final Font MENU_FONT = new Font("Segoe UI", Font.PLAIN, 14);

    public Project() {
        setTitle("University Management System");
        setLayout(new BorderLayout());

        // Load and scale background images
        String[] imagePaths = {
                "icons/slider1.jpg",
                "icons/slider2.jpg",
                "icons/slider3.jpg",
                "icons/slider4.jpg",
                "icons/slider5.jpg"
        };

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        sliderImages = new ImageIcon[imagePaths.length];

        for (int i = 0; i < imagePaths.length; i++) {
            try {
                ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource(imagePaths[i]));
                Image img = icon.getImage().getScaledInstance(screenSize.width, screenSize.height, Image.SCALE_SMOOTH);
                sliderImages[i] = new ImageIcon(img);
            } catch (Exception e) {
                System.out.println("Error loading image: " + imagePaths[i]);
            }
        }

        imagePanel = new ImagePanel();
        imagePanel.setPreferredSize(screenSize);
        add(imagePanel, BorderLayout.CENTER);

        javax.swing.Timer slideTimer = new javax.swing.Timer(3000, e -> startFadeTransition());
        slideTimer.start();

        // Menu bar setup
        JMenuBar mb = new JMenuBar();
        mb.setBackground(MENU_BG);

        String[][] menus = {
                { "New Information", "New Faculty Information", "New Student Information" },
                { "View Details", "View Faculty Details", "View Student Details" },
                { "Examination", "Examination Results", "Enter Marks" },
                { "Update Details", "Update Faculty Details", "Update Student Details" },
                { "Fee Details", "Fee Structure", "Student Fee Form" },
                { "Utility", "Notepad", "Calculator" },
                { "About", "About" },
                { "Exit", "Exit" }
        };

        for (String[] section : menus) {
            JMenu menu = new JMenu(section[0]);
            styleMenu(menu);

            for (int i = 1; i < section.length; i++) {
                JMenuItem item = new JMenuItem(section[i]);
                styleMenuItem(item);
                menu.add(item);
                item.addActionListener(this);
            }
            mb.add(menu);
        }

        setJMenuBar(mb);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void startFadeTransition() {
        alpha = 1.0f;
        nextImageIndex = (currentImageIndex + 1) % sliderImages.length;

        if (fadeTimer != null)
            fadeTimer.cancel();
        fadeTimer = new java.util.Timer();

        fadeTimer.scheduleAtFixedRate(new TimerTask() {
            long startTime = System.currentTimeMillis();

            public void run() {
                long elapsed = System.currentTimeMillis() - startTime;
                alpha = 1.0f - Math.min(1.0f, (float) elapsed / FADE_DURATION);
                imagePanel.repaint();

                if (alpha <= 0.0f) {
                    currentImageIndex = nextImageIndex;
                    alpha = 1.0f;
                    fadeTimer.cancel();
                }
            }
        }, 0, FADE_DELAY);
    }

    private class ImagePanel extends JPanel {
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            Image current = sliderImages[currentImageIndex].getImage();
            g2d.drawImage(current, 0, 0, getWidth(), getHeight(), this);

            if (currentImageIndex != nextImageIndex && alpha < 1.0f) {
                Composite old = g2d.getComposite();
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f - alpha));
                Image next = sliderImages[nextImageIndex].getImage();
                g2d.drawImage(next, 0, 0, getWidth(), getHeight(), this);
                g2d.setComposite(old);
            }
        }
    }

    private void styleMenu(JMenu menu) {
        menu.setFont(MENU_FONT);
        menu.setOpaque(true);
        menu.setBackground(MENU_BG);
        menu.setForeground(TEXT_COLOR);
        menu.setBorderPainted(false);
    }

    private void styleMenuItem(JMenuItem item) {
        item.setFont(MENU_FONT);
        item.setOpaque(true);
        item.setBackground(MENU_BG);
        item.setForeground(TEXT_COLOR);
        item.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));

        item.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                item.setBackground(HOVER_COLOR);
            }

            public void mouseExited(MouseEvent evt) {
                item.setBackground(MENU_BG);
            }
        });
    }

    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();

        try {
            switch (command) {
                case "Exit":
                    setVisible(false);
                    break;
                case "Calculator":
                    Runtime.getRuntime().exec("calc");
                    break;
                case "Notepad":
                    Runtime.getRuntime().exec("notepad");
                    break;
                case "New Faculty Information":
                    new AddTeacher();
                    break;
                case "New Student Information":
                    new AddStudent();
                    break;
                case "View Faculty Details":
                    new TeacherDetails();
                    break;
                case "View Student Details":
                    new StudentDetails();
                    break;
                case "Update Faculty Details":
                    new UpdateTeacher();
                    break;
                case "Update Student Details":
                    new UpdateStudent();
                    break;
                case "Enter Marks":
                    new EnterMarks();
                    break;
                case "Examination Results":
                    new ExaminationDetails();
                    break;
                case "Fee Structure":
                    new FeeStructure();
                    break;
                case "Student Fee Form":
                    new StudentFeeForm();
                    break;
                case "About":
                    new About();
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "Unknown command: " + command);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error executing command: " + command + "\n" + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Project();
    }
}
