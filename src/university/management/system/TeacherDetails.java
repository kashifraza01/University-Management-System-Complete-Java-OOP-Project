package university.management.system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class TeacherDetails extends JFrame implements ActionListener {

    Choice cEmpId;
    JTable table;
    JButton searchButton, printButton, updateButton, addButton, cancelButton, exportButton;
    DefaultTableModel model;

    TeacherDetails() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel heading = new JLabel("Search by Employee Id");
        heading.setBounds(20, 20, 150, 20);
        add(heading);

        cEmpId = new Choice();
        cEmpId.setBounds(180, 20, 150, 20);
        add(cEmpId);

        // Load employee IDs into Choice
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("SELECT empId FROM teacher");
            while (rs.next()) {
                cEmpId.add(rs.getString("empId"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Set up table
        table = new JTable();
        model = new DefaultTableModel();
        table.setModel(model);

        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(0, 100, 900, 600);
        add(jsp);

        // Buttons
        searchButton = new JButton("Search");
        searchButton.setBounds(20, 70, 80, 20);
        searchButton.addActionListener(this);
        add(searchButton);

        printButton = new JButton("Print");
        printButton.setBounds(120, 70, 80, 20);
        printButton.addActionListener(this);
        add(printButton);

        addButton = new JButton("Add");
        addButton.setBounds(220, 70, 80, 20);
        addButton.addActionListener(this);
        add(addButton);

        updateButton = new JButton("Update");
        updateButton.setBounds(320, 70, 80, 20);
        updateButton.addActionListener(this);
        add(updateButton);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(420, 70, 80, 20);
        cancelButton.addActionListener(this);
        add(cancelButton);

        exportButton = new JButton("Export CSV");
        exportButton.setBounds(520, 70, 100, 20);
        exportButton.addActionListener(this);
        add(exportButton);

        setSize(900, 700);
        setLocation(300, 100);
        setVisible(true);

        // Load all teachers initially
        loadTeacherData("SELECT * FROM teacher");
    }

    // Load teacher data into the table
    private void loadTeacherData(String query) {
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery(query);
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Clear table first
            model.setRowCount(0);
            model.setColumnCount(0);

            // Set column names
            for (int i = 1; i <= columnCount; i++) {
                model.addColumn(metaData.getColumnName(i));
            }

            // Add rows
            while (rs.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    rowData[i - 1] = rs.getObject(i);
                }
                model.addRow(rowData);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == searchButton) {
            String empId = cEmpId.getSelectedItem();
            String query = "SELECT * FROM teacher WHERE empId = '" + empId + "'";
            loadTeacherData(query);
        } else if (ae.getSource() == printButton) {
            try {
                table.print();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == addButton) {
            setVisible(false);
            new AddTeacher(); // Ensure this class exists
        } else if (ae.getSource() == updateButton) {
            setVisible(false);
            new UpdateTeacher(); // Ensure this class exists
        } else if (ae.getSource() == cancelButton) {
            setVisible(false);
        } else if (ae.getSource() == exportButton) {
            exportToCSV();
        }
    }

    private void exportToCSV() {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save Teacher Data");
            fileChooser.setSelectedFile(new java.io.File("teacher_data.csv"));

            int userSelection = fileChooser.showSaveDialog(this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                java.io.File fileToSave = fileChooser.getSelectedFile();
                java.io.FileWriter fw = new java.io.FileWriter(fileToSave);

                // Write headers
                for (int i = 0; i < model.getColumnCount(); i++) {
                    fw.write(model.getColumnName(i));
                    if (i < model.getColumnCount() - 1)
                        fw.write(",");
                }
                fw.write("\n");

                // Write data
                for (int i = 0; i < model.getRowCount(); i++) {
                    for (int j = 0; j < model.getColumnCount(); j++) {
                        Object value = model.getValueAt(i, j);
                        fw.write(value != null ? value.toString() : "");
                        if (j < model.getColumnCount() - 1)
                            fw.write(",");
                    }
                    fw.write("\n");
                }

                fw.close();
                JOptionPane.showMessageDialog(this, "Data exported successfully to " + fileToSave.getAbsolutePath());
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error exporting data: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new TeacherDetails();
    }
}
