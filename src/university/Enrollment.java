package university;

/**
 * Represents a student's enrollment in a course, including their grade.
 * Demonstrates association between Student and Course.
 */
public class Enrollment {
    private Student student;
    private Course course;
    private double grade;       // -1 indicates no grade assigned yet
    private String semester;

    public Enrollment(Student student, Course course, String semester) {
        this.student = student;
        this.course = course;
        this.semester = semester;
        this.grade = -1;
    }

    public Student getStudent() { return student; }
    public Course getCourse() { return course; }
    public String getSemester() { return semester; }

    public double getGrade() { return grade; }

    public void setGrade(double grade) {
        if (grade < 0.0 || grade > 4.0) {
            throw new IllegalArgumentException("Grade must be between 0.0 and 4.0");
        }
        this.grade = grade;
    }

    public String getLetterGrade() {
        if (grade < 0) return "N/A";
        if (grade >= 3.7) return "A";
        if (grade >= 3.3) return "A-";
        if (grade >= 3.0) return "B+";
        if (grade >= 2.7) return "B";
        if (grade >= 2.3) return "B-";
        if (grade >= 2.0) return "C+";
        if (grade >= 1.7) return "C";
        if (grade >= 1.3) return "C-";
        if (grade >= 1.0) return "D";
        return "F";
    }

    @Override
    public String toString() {
        return String.format("Enrollment: %s -> %s [%s] | Grade: %.1f (%s)",
                student.getName(), course.getTitle(), semester, grade, getLetterGrade());
    }
}
