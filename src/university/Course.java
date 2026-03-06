package university;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents an academic course offered by the university.
 */
public class Course {
    private String courseCode;
    private String title;
    private int creditHours;
    private Professor instructor;
    private int maxCapacity;
    private List<Enrollment> enrollments;

    public Course(String courseCode, String title, int creditHours, int maxCapacity) {
        this.courseCode = courseCode;
        this.title = title;
        this.creditHours = creditHours;
        this.maxCapacity = maxCapacity;
        this.enrollments = new ArrayList<>();
    }

    public String getCourseCode() { return courseCode; }
    public String getTitle() { return title; }
    public int getCreditHours() { return creditHours; }
    public int getMaxCapacity() { return maxCapacity; }
    public Professor getInstructor() { return instructor; }

    public void setInstructor(Professor instructor) {
        this.instructor = instructor;
    }

    public List<Enrollment> getEnrollments() { return Collections.unmodifiableList(enrollments); }

    public int getEnrolledCount() { return enrollments.size(); }

    public boolean hasCapacity() { return enrollments.size() < maxCapacity; }

    public boolean addEnrollment(Enrollment enrollment) {
        if (!hasCapacity()) return false;
        enrollments.add(enrollment);
        return true;
    }

    public void removeEnrollment(Enrollment enrollment) {
        enrollments.remove(enrollment);
    }

    @Override
    public String toString() {
        String instructorName = (instructor != null) ? instructor.getName() : "TBA";
        return String.format("Course [%s] %s | Credits: %d | Instructor: %s | Enrolled: %d/%d",
                courseCode, title, creditHours, instructorName, enrollments.size(), maxCapacity);
    }
}
