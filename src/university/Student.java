package university;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a student enrolled in the university.
 * Demonstrates inheritance from Person and implementation of Gradable.
 */
public class Student extends Person implements Gradable {
    private String major;
    private int enrollmentYear;
    private List<Enrollment> enrollments;

    public Student(String id, String name, String email, int age,
                   String major, int enrollmentYear) {
        super(id, name, email, age);
        this.major = major;
        this.enrollmentYear = enrollmentYear;
        this.enrollments = new ArrayList<>();
    }

    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }
    public int getEnrollmentYear() { return enrollmentYear; }
    public List<Enrollment> getEnrollments() { return Collections.unmodifiableList(enrollments); }

    public void addEnrollment(Enrollment enrollment) {
        enrollments.add(enrollment);
    }

    @Override
    public String getRole() { return "Student"; }

    @Override
    public double calculateGPA() {
        if (enrollments.isEmpty()) return 0.0;
        double total = 0.0;
        int count = 0;
        for (Enrollment e : enrollments) {
            if (e.getGrade() >= 0) {
                total += e.getGrade();
                count++;
            }
        }
        return count == 0 ? 0.0 : total / count;
    }

    @Override
    public String getAcademicStatus() {
        double gpa = calculateGPA();
        if (gpa >= 3.5) return "Distinction";
        if (gpa >= 3.0) return "Good Standing";
        if (gpa >= 2.0) return "Satisfactory";
        if (gpa > 0.0) return "Probation";
        return "No grades yet";
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Major: %s | Year: %d | GPA: %.2f | Status: %s",
                major, enrollmentYear, calculateGPA(), getAcademicStatus());
    }
}
