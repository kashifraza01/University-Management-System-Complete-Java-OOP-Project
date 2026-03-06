package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;

public class ExaminationDetails extends JFrame implements ActionListener {

    JTextField search;
    JButton submit, cancel;
    JTable table;
    JComboBox<String> cbsemester;

    ExaminationDetails() {
        setSize(1000, 500);
        setLocation(300, 100);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        JLabel heading = new JLabel("Check Student Result");
        heading.setBounds(80, 15, 400, 50);
        heading.setFont(new Font("Tahoma", Font.BOLD, 24));
        add(heading);

        JLabel lblroll = new JLabel("Enter Roll Number");
        lblroll.setBounds(80, 80, 150, 25);
        lblroll.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblroll);

        search = new JTextField();
        search.setBounds(230, 80, 200, 25);
        search.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(search);

        JLabel lblsem = new JLabel("Select Semester");
        lblsem.setBounds(80, 120, 150, 25);
        lblsem.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblsem);

        String[] semesterOptions = {
            "Semester 1", "Semester 2", "Semester 3", "Semester 4",
            "Semester 5", "Semester 6"
        };

        cbsemester = new JComboBox<>(semesterOptions);
        cbsemester.setBounds(230, 120, 200, 25);
        add(cbsemester);

        table = new JTable();
        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(0, 200, 1000, 300);
        add(jsp);

        submit = new JButton("Search");
        submit.setBounds(470, 80, 100, 25);
        submit.addActionListener(this);
        add(submit);

        cancel = new JButton("Cancel");
        cancel.setBounds(470, 120, 100, 25);
        cancel.addActionListener(this);
        add(cancel);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == submit) {
            String rollno = search.getText();
            String semester = (String) cbsemester.getSelectedItem();

            if (rollno.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter Roll Number");
                return;
            }

            try {
                Conn c = new Conn();
                String query = "SELECT rollno, semester, marks1, marks2, marks3, marks4, marks5 FROM marks WHERE rollno = ? AND semester = ?";
                PreparedStatement pst = c.c.prepareStatement(query);
                pst.setString(1, rollno);
                pst.setString(2, semester);
                ResultSet rs = pst.executeQuery();

                if (!rs.isBeforeFirst()) {
                    JOptionPane.showMessageDialog(null, "No records found for this Roll Number and Semester.");
                }

                table.setModel(DbUtils.resultSetToTableModel(rs));

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error retrieving data.");
            }

        } else {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new ExaminationDetails();
    }
}
