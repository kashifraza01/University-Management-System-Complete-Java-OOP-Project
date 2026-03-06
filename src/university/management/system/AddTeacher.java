package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import com.toedter.calendar.JDateChooser;
import java.sql.*;

public class AddTeacher extends JFrame implements ActionListener {

    JTextField tfname, tffname, tfaddress, tfphone, tfemail, tfgpa, tfnic;
    JLabel labelempId;
    JDateChooser dcdob;
    JComboBox<String> cbdegree, cbcampus;
    JButton submit, cancel;

    public AddTeacher() {
        setSize(900, 700);
        setLocation(350, 50);
        setLayout(null);

        JLabel heading = new JLabel("New Teacher Details");
        heading.setBounds(310, 30, 500, 50);
        heading.setFont(new Font("serif", Font.BOLD, 30));
        add(heading);

        // Auto-generate employee ID
        String empId = "101" + (new Random().nextInt(9000) + 1000);

        addLabel("Name", 50, 150);
        tfname = addTextField(200, 150);

        addLabel("Father's Name", 400, 150);
        tffname = addTextField(600, 150);

        addLabel("Employee ID", 50, 200);
        labelempId = new JLabel(empId);
        labelempId.setBounds(200, 200, 200, 30);
        labelempId.setFont(new Font("serif", Font.BOLD, 20));
        add(labelempId);

        addLabel("Date of Birth", 400, 200);
        dcdob = new JDateChooser();
        dcdob.setBounds(600, 200, 150, 30);
        add(dcdob);

        addLabel("Address", 50, 250);
        tfaddress = addTextField(200, 250);

        addLabel("Phone", 400, 250);
        tfphone = addTextField(600, 250);

        addLabel("Email Id", 50, 300);
        tfemail = addTextField(200, 300);

        addLabel("GPA (0.0-4.0)", 400, 300);
        tfgpa = addTextField(600, 300);

        addLabel("NIC Number", 50, 350);
        tfnic = addTextField(200, 350);

        addLabel("Degree", 50, 400);
        String[] degree = { "CS", "BBA", "ADB", "SE" }; // Matches DB
        cbdegree = new JComboBox<>(degree);
        cbdegree.setBounds(200, 400, 150, 30);
        cbdegree.setBackground(Color.WHITE);
        add(cbdegree);

        addLabel("Campus", 400, 400);
        String[] campus = { "Airport Campus", "Main Campus", "North Campus" }; // Matches DB
        cbcampus = new JComboBox<>(campus);
        cbcampus.setBounds(600, 400, 150, 30);
        cbcampus.setBackground(Color.WHITE);
        add(cbcampus);

        submit = new JButton("Submit");
        submit.setBounds(250, 550, 120, 30);
        submit.setBackground(Color.BLACK);
        submit.setForeground(Color.WHITE);
        submit.setFont(new Font("Tahoma", Font.BOLD, 15));
        submit.addActionListener(this);
        add(submit);

        cancel = new JButton("Cancel");
        cancel.setBounds(450, 550, 120, 30);
        cancel.setBackground(Color.BLACK);
        cancel.setForeground(Color.WHITE);
        cancel.setFont(new Font("Tahoma", Font.BOLD, 15));
        cancel.addActionListener(this);
        add(cancel);

        setVisible(true);
    }

    private void addLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 200, 30);
        label.setFont(new Font("serif", Font.BOLD, 20));
        add(label);
    }

    private JTextField addTextField(int x, int y) {
        JTextField tf = new JTextField();
        tf.setBounds(x, y, 150, 30);
        add(tf);
        return tf;
    }

    private boolean validateFields() {
        if (tfname.getText().trim().isEmpty())
            return showError("Name is required.");
        if (tffname.getText().trim().isEmpty())
            return showError("Father's name is required.");
        if (dcdob.getDate() == null)
            return showError("Date of birth is required.");
        if (tfaddress.getText().trim().isEmpty())
            return showError("Address is required.");
        if (!tfphone.getText().matches("\\d{11}"))
            return showError("Phone number must be 11 digits.");
        if (!tfemail.getText().matches("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$"))
            return showError("Invalid email format.");
        if (tfgpa.getText().trim().isEmpty())
            return showError("GPA is required.");
        try {
            double gpa = Double.parseDouble(tfgpa.getText().trim());
            if (gpa < 0.0 || gpa > 4.0)
                return showError("GPA must be between 0.0 and 4.0.");
        } catch (NumberFormatException e) {
            return showError("GPA must be a valid number.");
        }
        if (!tfnic.getText().matches("\\d{5}-\\d{7}-\\d{1}"))
            return showError("NIC must be in format XXXXX-XXXXXXX-X.");
        return true;
    }

    private boolean showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Input Error", JOptionPane.ERROR_MESSAGE);
        return false;
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == submit) {
            if (!validateFields())
                return;

            try {
                Conn con = new Conn();
                String query = "INSERT INTO teacher (name, fname, empId, dob, address, phone, email, education, gpa, nic, campus) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pst = con.c.prepareStatement(query);

                pst.setString(1, tfname.getText());
                pst.setString(2, tffname.getText());
                pst.setString(3, labelempId.getText());
                pst.setDate(4, new java.sql.Date(dcdob.getDate().getTime()));
                pst.setString(5, tfaddress.getText());
                pst.setString(6, tfphone.getText());
                pst.setString(7, tfemail.getText());
                pst.setString(8, (String) cbdegree.getSelectedItem()); // Using degree as education
                pst.setString(9, tfgpa.getText().trim()); // GPA from form
                pst.setString(10, tfnic.getText());
                pst.setString(11, (String) cbcampus.getSelectedItem());

                pst.executeUpdate();

                JOptionPane.showMessageDialog(this, "Teacher added successfully.");
                setVisible(false);

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
            }
        } else {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new AddTeacher();
    }
}
