package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class UpdateTeacher extends JFrame implements ActionListener {
    JTextField tfaddress, tfphone, tfemail;
    JLabel labelEmpId, labelname, labelfname, labeldob, labelx, labelxii, labelnic, labeldegree;
    JComboBox<String> cbcampus;
    JButton submit, cancel;
    Choice cEmpId;

    UpdateTeacher() {
        setSize(900, 650);
        setLocation(350, 50);
        setLayout(null);

        JLabel heading = new JLabel("Update Teacher Details");
        heading.setBounds(50, 10, 500, 50);
        heading.setFont(new Font("Tahoma", Font.ITALIC, 35));
        add(heading);

        JLabel lblrollnumber = new JLabel("Select Employee Id");
        lblrollnumber.setBounds(50, 100, 200, 20);
        lblrollnumber.setFont(new Font("serif", Font.PLAIN, 20));
        add(lblrollnumber);

        cEmpId = new Choice();
        cEmpId.setBounds(250, 100, 200, 20);
        add(cEmpId);

        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("select * from teacher");
            while (rs.next()) {
                cEmpId.add(rs.getString("empId"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        addLabel("Name", 50, 150);
        labelname = addValueLabel(200, 150);

        addLabel("Father's Name", 400, 150);
        labelfname = addValueLabel(600, 150);

        addLabel("Employee Id", 50, 200);
        labelEmpId = addValueLabel(200, 200);

        addLabel("Date of Birth", 400, 200);
        labeldob = addValueLabel(600, 200);

        addLabel("Address", 50, 250);
        tfaddress = addTextField(200, 250);

        addLabel("Phone", 400, 250);
        tfphone = addTextField(600, 250);

        addLabel("Email Id", 50, 300);
        tfemail = addTextField(200, 300);

        addLabel("Degree (Education)", 400, 300);
        labelx = addValueLabel(600, 300);

        addLabel("GPA", 50, 350);
        labelxii = addValueLabel(200, 350);

        addLabel("NIC Number", 400, 350);
        labelnic = addValueLabel(600, 350);

        addLabel("Degree", 50, 400);
        labeldegree = addValueLabel(200, 400);

        addLabel("Campus", 400, 400);
        String[] campusOptions = { "Airport Campus", "Main Campus", "North Campus" };
        cbcampus = new JComboBox<>(campusOptions);
        cbcampus.setBounds(600, 400, 150, 30);
        cbcampus.setBackground(Color.WHITE);
        add(cbcampus);

        loadTeacherDetails(cEmpId.getSelectedItem());

        cEmpId.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ie) {
                loadTeacherDetails(cEmpId.getSelectedItem());
            }
        });

        submit = new JButton("Update");
        submit.setBounds(250, 500, 120, 30);
        submit.setBackground(Color.BLACK);
        submit.setForeground(Color.WHITE);
        submit.setFont(new Font("Tahoma", Font.BOLD, 15));
        submit.addActionListener(this);
        add(submit);

        cancel = new JButton("Cancel");
        cancel.setBounds(450, 500, 120, 30);
        cancel.setBackground(Color.BLACK);
        cancel.setForeground(Color.WHITE);
        cancel.setFont(new Font("Tahoma", Font.BOLD, 15));
        cancel.addActionListener(this);
        add(cancel);

        setVisible(true);
    }

    private void loadTeacherDetails(String empId) {
        try {
            Conn c = new Conn();
            String query = "select * from teacher where empId='" + empId + "'";
            ResultSet rs = c.s.executeQuery(query);
            if (rs.next()) {
                labelname.setText(rs.getString("name"));
                labelfname.setText(rs.getString("fname"));
                labeldob.setText(rs.getString("dob"));
                tfaddress.setText(rs.getString("address"));
                tfphone.setText(rs.getString("phone"));
                tfemail.setText(rs.getString("email"));
                labelx.setText(rs.getString("education"));
                labelxii.setText(rs.getString("gpa"));
                labelnic.setText(rs.getString("nic"));
                labelEmpId.setText(rs.getString("empId"));
                labeldegree.setText(rs.getString("education"));
                cbcampus.setSelectedItem(rs.getString("campus"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addLabel(String text, int x, int y) {
        JLabel lbl = new JLabel(text);
        lbl.setBounds(x, y, 200, 30);
        lbl.setFont(new Font("serif", Font.BOLD, 20));
        add(lbl);
    }

    private JLabel addValueLabel(int x, int y) {
        JLabel label = new JLabel();
        label.setBounds(x, y, 150, 30);
        label.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(label);
        return label;
    }

    private JTextField addTextField(int x, int y) {
        JTextField tf = new JTextField();
        tf.setBounds(x, y, 150, 30);
        add(tf);
        return tf;
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == submit) {
            try {
                Conn con = new Conn();
                String query = "UPDATE teacher SET address = ?, phone = ?, email = ?, campus = ? WHERE empId = ?";
                PreparedStatement ps = con.c.prepareStatement(query);
                ps.setString(1, tfaddress.getText());
                ps.setString(2, tfphone.getText());
                ps.setString(3, tfemail.getText());
                ps.setString(4, (String) cbcampus.getSelectedItem());
                ps.setString(5, labelEmpId.getText());

                int rowsUpdated = ps.executeUpdate();
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(null, "Teacher Details Updated Successfully");
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Update failed. No rows affected.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            }
        } else {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new UpdateTeacher();
    }
}
