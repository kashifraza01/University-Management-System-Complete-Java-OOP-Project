package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Marks extends JFrame {

    private JLabel[] subjectLabels = new JLabel[5];
    private JLabel[] markLabels = new JLabel[5];
    private JLabel lblTotalMarks, lblPercentage, lblGrade;
    private JButton btnBack;
    private String rollno, semester;

    public Marks(String rollno, String semester) {
        this.rollno = rollno;
        this.semester = semester;

        setTitle("Student Marks");
        setSize(600, 400);
        setLocation(300, 100);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        JLabel lblRollNo = new JLabel("Roll No: " + rollno);
        lblRollNo.setBounds(50, 30, 300, 20);
        add(lblRollNo);

        JLabel lblSemester = new JLabel("Semester: " + semester);
        lblSemester.setBounds(50, 60, 300, 20);
        add(lblSemester);

        try {
            Conn c = new Conn();

            // Get subjects
            String subjectQuery = "SELECT s.subject1, s.subject2, s.subject3, s.subject4, s.subject5 FROM subject s INNER JOIN student st ON s.template_id = st.degree WHERE st.rollno = ? AND s.semester = ?";
            PreparedStatement pstSubjects = c.c.prepareStatement(subjectQuery);
            pstSubjects.setString(1, rollno);
            pstSubjects.setString(2, semester);
            ResultSet rsSubjects = pstSubjects.executeQuery();

            String[] subjects = new String[5];
            if (rsSubjects.next()) {
                for (int i = 0; i < 5; i++) {
                    subjects[i] = rsSubjects.getString("subject" + (i + 1));
                }
            } else {
                JOptionPane.showMessageDialog(null, "No subject data found.");
                return;
            }

            // Get marks
            String marksQuery = "SELECT marks1, marks2, marks3, marks4, marks5 FROM marks WHERE rollno = ? AND semester = ?";
            PreparedStatement pstMarks = c.c.prepareStatement(marksQuery);
            pstMarks.setString(1, rollno);
            pstMarks.setString(2, semester);
            ResultSet rsMarks = pstMarks.executeQuery();

            int[] marks = new int[5];
            int total = 0;

            if (rsMarks.next()) {
                for (int i = 0; i < 5; i++) {
                    marks[i] = rsMarks.getInt("marks" + (i + 1));
                    total += marks[i];
                }
            } else {
                JOptionPane.showMessageDialog(null, "No marks data found.");
                return;
            }

            int yPosition = 100;
            for (int i = 0; i < 5; i++) {
                subjectLabels[i] = new JLabel(subjects[i] + ": ");
                subjectLabels[i].setBounds(50, yPosition, 200, 20);
                add(subjectLabels[i]);

                markLabels[i] = new JLabel(String.valueOf(marks[i]));
                markLabels[i].setBounds(300, yPosition, 100, 20);
                add(markLabels[i]);

                yPosition += 30;
            }

            double percentage = total / 5.0;
            String grade = getGrade(percentage);

            lblTotalMarks = new JLabel("Total: " + total + " / 500");
            lblTotalMarks.setBounds(50, yPosition + 20, 300, 20);
            add(lblTotalMarks);

            lblPercentage = new JLabel("Percentage: " + String.format("%.2f", percentage) + "%");
            lblPercentage.setBounds(50, yPosition + 50, 300, 20);
            add(lblPercentage);

            lblGrade = new JLabel("Grade: " + grade);
            lblGrade.setBounds(50, yPosition + 80, 300, 20);
            add(lblGrade);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching marks or subjects.");
        }

        btnBack = new JButton("Back");
        btnBack.setBounds(400, 320, 100, 30);
        btnBack.addActionListener(e -> setVisible(false));
        add(btnBack);

        setVisible(true);
    }

    private String getGrade(double percentage) {
        if (percentage >= 85) return "A+";
        else if (percentage >= 75) return "A";
        else if (percentage >= 65) return "B";
        else if (percentage >= 50) return "C";
        else return "F";
    }

    public static void main(String[] args) {
        new Marks("15331234", "Semester 5");
    }
}
