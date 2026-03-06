package university;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a professor in the university.
 * Demonstrates inheritance from Person.
 */
public class Professor extends Person {
    private String specialization;
    private String rank;        // e.g., Assistant, Associate, Full
    private List<Course> courses;

    public Professor(String id, String name, String email, int age,
                     String specialization, String rank) {
        super(id, name, email, age);
        this.specialization = specialization;
        this.rank = rank;
        this.courses = new ArrayList<>();
    }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }
    public String getRank() { return rank; }
    public void setRank(String rank) { this.rank = rank; }
    public List<Course> getCourses() { return Collections.unmodifiableList(courses); }

    public void assignCourse(Course course) {
        if (!courses.contains(course)) {
            courses.add(course);
        }
    }

    public void removeCourse(Course course) {
        courses.remove(course);
    }

    @Override
    public String getRole() { return "Professor"; }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Rank: %s | Specialization: %s | Courses: %d",
                rank, specialization, courses.size());
    }
}
