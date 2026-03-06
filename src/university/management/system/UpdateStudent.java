package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class UpdateStudent extends JFrame implements ActionListener {

    JTextField tfdegree, tfaddress, tfphone, tfemail, tfcampus;
    JLabel labelrollno;
    JButton submit, cancel;
    Choice crollno;
    JLabel labelname, labelfname, labeldob, labelx, labelxii, labelnic;

    UpdateStudent() {

        setSize(900, 650);
        setLocation(350, 50);
        setLayout(null);

        JLabel heading = new JLabel("Update Student Details");
        heading.setBounds(50, 10, 500, 50);
        heading.setFont(new Font("Tahoma", Font.ITALIC, 35));
        add(heading);

        JLabel lblrollnumber = new JLabel("Select Roll Number");
        lblrollnumber.setBounds(50, 100, 200, 20);
        lblrollnumber.setFont(new Font("serif", Font.PLAIN, 20));
        add(lblrollnumber);

        crollno = new Choice();
        crollno.setBounds(250, 100, 200, 20);
        add(crollno);

        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("select * from student");
            while (rs.next()) {
                crollno.add(rs.getString("rollno"));
            }
            c.c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        createLabel("Name", 50, 150);
        labelname = createDynamicLabel(200, 150);

        createLabel("Father's Name", 400, 150);
        labelfname = createDynamicLabel(600, 150);

        createLabel("Roll Number", 50, 200);
        labelrollno = createDynamicLabel(200, 200);

        createLabel("Date of Birth", 400, 200);
        labeldob = createDynamicLabel(600, 200);

        createLabel("Address", 50, 250);
        tfaddress = createTextField(200, 250);

        createLabel("Phone", 400, 250);
        tfphone = createTextField(600, 250);

        createLabel("Email Id", 50, 300);
        tfemail = createTextField(200, 300);

        createLabel("Class X (%)", 400, 300);
        labelx = createDynamicLabel(600, 300);

        createLabel("Class XII (%)", 50, 350);
        labelxii = createDynamicLabel(200, 350);

        createLabel("NIC Number", 400, 350);
        labelnic = createDynamicLabel(600, 350);

        createLabel("Degree", 50, 400);
        tfdegree = createTextField(200, 400);

        createLabel("Campus", 400, 400);
        tfcampus = createTextField(600, 400);

        // Populate student details when roll number is selected
        crollno.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ie) {
                loadStudentDetails();
            }
        });
        submit = new JButton("Update");
        submit.setBounds(250, 500, 120, 30);
        submit.setBackground(Color.BLACK);
        submit.setForeground(Color.WHITE);
        submit.addActionListener(this);
        submit.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(submit);

        cancel = new JButton("Cancel");
        cancel.setBounds(450, 500, 120, 30);
        cancel.setBackground(Color.BLACK);
        cancel.setForeground(Color.WHITE);
        cancel.addActionListener(this);
        cancel.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(cancel);

        // Load first student's details on form open
        if (crollno.getItemCount() > 0) {
            loadStudentDetails();
        }

        setVisible(true);
    }

    private JLabel createLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 200, 30);
        label.setFont(new Font("serif", Font.BOLD, 20));
        add(label);
        return label;
    }

    // Method to create dynamic labels that will hold fetched data
    private JLabel createDynamicLabel(int x, int y) {
        JLabel label = new JLabel();
        label.setBounds(x, y, 150, 30);
        label.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(label);
        return label;
    }

    private JTextField createTextField(int x, int y) {
        JTextField textField = new JTextField();
        textField.setBounds(x, y, 150, 30);
        add(textField);
        return textField;
    }

    private void loadStudentDetails() {
        try {
            Conn c = new Conn();
            String query = "select * from student where rollno='" + crollno.getSelectedItem() + "'";
            ResultSet rs = c.s.executeQuery(query);
            while (rs.next()) {
                labelname.setText(rs.getString("name"));
                labelfname.setText(rs.getString("fname"));
                labeldob.setText(rs.getString("dob"));
                tfaddress.setText(rs.getString("address"));
                tfphone.setText(rs.getString("phone"));
                tfemail.setText(rs.getString("email"));
                labelx.setText(rs.getString("class_x"));
                labelxii.setText(rs.getString("class_xii"));
                labelnic.setText(rs.getString("nic"));
                labelrollno.setText(rs.getString("rollno"));
                tfdegree.setText(rs.getString("degree"));
                tfcampus.setText(rs.getString("campus"));
            }
            c.c.close(); // Close connection after use
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == submit) {
            String rollno = labelrollno.getText();
            String address = tfaddress.getText();
            String phone = tfphone.getText();
            String email = tfemail.getText();
            String degree = tfdegree.getText();
            String campus = tfcampus.getText();

            try {
                String query = "update student set address='" + address + "', phone='" + phone + "', email='" + email
                        + "', degree='" + degree + "', campus='" + campus + "' where rollno='" + rollno + "'";
                Conn con = new Conn();
                con.s.executeUpdate(query);

                JOptionPane.showMessageDialog(null, "Student Details Updated Successfully");
                setVisible(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new UpdateStudent();
    }
}
