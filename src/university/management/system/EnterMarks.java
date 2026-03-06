package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class EnterMarks extends JFrame implements ActionListener {

    Choice crollno;
    JComboBox<String> cbsemester;
    JTextField tfmarks1, tfmarks2, tfmarks3, tfmarks4, tfmarks5;
    JButton submit, cancel;

    EnterMarks() {
        setSize(1000, 500);
        setLocation(300, 150);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        JLabel heading = new JLabel("Enter Marks of Student");
        heading.setBounds(50, 0, 500, 50);
        heading.setFont(new Font("Tahoma", Font.BOLD, 20));
        add(heading);

        JLabel lblrollnumber = new JLabel("Select Roll Number");
        lblrollnumber.setBounds(50, 70, 150, 20);
        add(lblrollnumber);

        crollno = new Choice();
        crollno.setBounds(200, 70, 150, 20);
        add(crollno);

        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("SELECT rollno FROM student");
            while (rs.next()) {
                crollno.add(rs.getString("rollno"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JLabel lblsemester = new JLabel("Select Semester");
        lblsemester.setBounds(50, 120, 150, 20);
        add(lblsemester);

        String semester[] = {"Semester 1", "Semester 2", "Semester 3", "Semester 4", "Semester 5", "Semester 6"};
        cbsemester = new JComboBox<>(semester);
        cbsemester.setBounds(200, 120, 150, 20);
        add(cbsemester);

        // Marks labels and fields
        String[] labels = {"Marks 1", "Marks 2", "Marks 3", "Marks 4", "Marks 5"};
        JTextField[] fields = new JTextField[5];

        for (int i = 0; i < labels.length; i++) {
            JLabel lbl = new JLabel(labels[i]);
            lbl.setBounds(50, 170 + i * 40, 150, 20);
            add(lbl);

            fields[i] = new JTextField();
            fields[i].setBounds(200, 170 + i * 40, 150, 20);
            add(fields[i]);
        }

        tfmarks1 = fields[0];
        tfmarks2 = fields[1];
        tfmarks3 = fields[2];
        tfmarks4 = fields[3];
        tfmarks5 = fields[4];

        submit = new JButton("Submit");
        submit.setBounds(70, 400, 100, 25);
        submit.addActionListener(this);
        add(submit);

        cancel = new JButton("Cancel");
        cancel.setBounds(200, 400, 100, 25);
        cancel.addActionListener(this);
        add(cancel);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == submit) {
            String rollno = crollno.getSelectedItem();
            String semester = (String) cbsemester.getSelectedItem();
            int[] marks = new int[5];

            try {
                marks[0] = Integer.parseInt(tfmarks1.getText());
                marks[1] = Integer.parseInt(tfmarks2.getText());
                marks[2] = Integer.parseInt(tfmarks3.getText());
                marks[3] = Integer.parseInt(tfmarks4.getText());
                marks[4] = Integer.parseInt(tfmarks5.getText());

                Conn c = new Conn();
                String query = "INSERT INTO marks (rollno, semester, marks1, marks2, marks3, marks4, marks5) VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pst = c.c.prepareStatement(query);
                pst.setString(1, rollno);
                pst.setString(2, semester);
                for (int i = 0; i < 5; i++) {
                    pst.setInt(i + 3, marks[i]);
                }
                pst.executeUpdate();

                JOptionPane.showMessageDialog(null, "Marks inserted successfully");
                setVisible(false);

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter valid marks (numeric values)");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error inserting marks");
            }

        } else {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new EnterMarks();
    }
}
