package university.management.system;

import javax.swing.*;
import java.awt.*;

public class About extends JFrame {

    public About() {
        setSize(700, 500);
        setLocation(400, 150);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

       ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/Kashif DP.jpg"));
       Image i2 = i1.getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH);
       JLabel image = new JLabel(new ImageIcon(i2));
       image.setBounds(350, 0, 300, 200);
       add(image);

        JLabel heading = new JLabel("<html>University<br/>Management System</html>");
        heading.setBounds(70, 20, 300, 130);
        heading.setFont(new Font("Tahoma", Font.BOLD, 30));
        add(heading);

        JLabel name = new JLabel("Developed By: Kashif Raza");
        name.setBounds(70, 220, 550, 40);
        name.setFont(new Font("Tahoma", Font.BOLD, 30));
        add(name);

        JLabel contact = new JLabel("Contact: kashifraza@gmail.com");
        contact.setBounds(70, 360, 550, 40);
        contact.setFont(new Font("Tahoma", Font.PLAIN, 20));
        add(contact);
        setVisible(true);
    }

    public static void main(String[] args) {
        new About();
    }
}
