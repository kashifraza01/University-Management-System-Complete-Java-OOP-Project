package university.management.system;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class StudentFeeForm extends JFrame implements ActionListener {

    JLabel lblRollNo, lblCourse, lblCampus, lblSemester, lblTotal;
    JTextField tfTotal;
    JButton btnSubmit, btnCancel;
    Choice chRollNo, chCourse, chCampus, chSemester;

    StudentFeeForm() {
        setSize(700, 500);
        setLocation(350, 150);
        setLayout(null);
        setTitle("Student Fee Form");

        JLabel heading = new JLabel("Student Fee Form");
        heading.setBounds(250, 30, 500, 40);
        heading.setFont(new Font("Tahoma", Font.BOLD, 20));
        add(heading);

        lblRollNo = new JLabel("Roll Number");
        lblRollNo.setBounds(100, 100, 100, 30);
        add(lblRollNo);

        chRollNo = new Choice();
        chRollNo.setBounds(250, 100, 200, 30);
        add(chRollNo);

        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("SELECT rollno FROM student");
            while (rs.next()) {
                chRollNo.add(rs.getString("rollno"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        lblCourse = new JLabel("Degree");
        lblCourse.setBounds(100, 150, 100, 30);
        add(lblCourse);

        chCourse = new Choice();
        chCourse.setBounds(250, 150, 200, 30);
        chCourse.add("BBA");
        chCourse.add("CS");
        chCourse.add("SE");
        chCourse.add("ADB");
        chCourse.add("MBA");
        chCourse.add("MBBS");
        chCourse.add("MCA");
        chCourse.add("Mcom");
        add(chCourse);

        lblCampus = new JLabel("Campus");
        lblCampus.setBounds(100, 200, 100, 30);
        add(lblCampus);

        chCampus = new Choice();
        chCampus.setBounds(250, 200, 200, 30);
        chCampus.add("Main Campus");
        chCampus.add("City Campus");
        chCampus.add("North Campus");
        add(chCampus);

        lblSemester = new JLabel("Semester");
        lblSemester.setBounds(100, 250, 100, 30);
        add(lblSemester);

        chSemester = new Choice();
        chSemester.setBounds(250, 250, 200, 30);
        for (int i = 1; i <= 8; i++) {
            chSemester.add("Semester " + i);
        }
        add(chSemester);

        lblTotal = new JLabel("Total Fee");
        lblTotal.setBounds(100, 300, 100, 30);
        add(lblTotal);

        tfTotal = new JTextField();
        tfTotal.setBounds(250, 300, 200, 30);
        add(tfTotal);

        btnSubmit = new JButton("Submit");
        btnSubmit.setBounds(100, 380, 100, 30);
        btnSubmit.addActionListener(this);
        add(btnSubmit);

        btnCancel = new JButton("Cancel");
        btnCancel.setBounds(250, 380, 100, 30);
        btnCancel.addActionListener(this);
        add(btnCancel);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/fee.jpg"));
        Image i2 = i1.getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel img = new JLabel(i3);
        img.setBounds(500, 100, 150, 150);
        add(img);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == btnSubmit) {
            String rollno = chRollNo.getSelectedItem();
            String degree = chCourse.getSelectedItem();
            String campus = chCampus.getSelectedItem();
            String semester = chSemester.getSelectedItem();
            String total = tfTotal.getText();

            if (total.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter total fee amount.");
                return;
            }

            try {
                Conn c = new Conn();

                PreparedStatement check = c.c.prepareStatement(
                    "SELECT * FROM collegefee WHERE rollno=? AND degree=? AND semester=?"
                );
                check.setString(1, rollno);
                check.setString(2, degree);
                check.setString(3, semester);

                ResultSet rs = check.executeQuery();

                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, "Fee already paid for this semester.");
                } else {
                    PreparedStatement pst = c.c.prepareStatement(
                        "INSERT INTO collegefee (rollno, degree, campus, semester, total) VALUES (?, ?, ?, ?, ?)"
                    );
                    pst.setString(1, rollno);
                    pst.setString(2, degree);
                    pst.setString(3, campus);
                    pst.setString(4, semester);
                    pst.setInt(5, Integer.parseInt(total));

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Fee submitted successfully.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error occurred while submitting fee.");
            }
        } else {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new StudentFeeForm();
    }
}
