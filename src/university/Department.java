package university;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents an academic department within the university.
 */
public class Department {
    private String name;
    private String code;
    private Professor head;
    private List<Professor> professors;
    private List<Course> courses;

    public Department(String code, String name) {
        this.code = code;
        this.name = name;
        this.professors = new ArrayList<>();
        this.courses = new ArrayList<>();
    }

    public String getName() { return name; }
    public String getCode() { return code; }
    public Professor getHead() { return head; }

    public void setHead(Professor head) {
        this.head = head;
        if (!professors.contains(head)) {
            professors.add(head);
        }
    }

    public List<Professor> getProfessors() { return Collections.unmodifiableList(professors); }
    public List<Course> getCourses() { return Collections.unmodifiableList(courses); }

    public void addProfessor(Professor professor) {
        if (!professors.contains(professor)) {
            professors.add(professor);
        }
    }

    public void removeProfessor(Professor professor) {
        professors.remove(professor);
    }

    public void addCourse(Course course) {
        if (!courses.contains(course)) {
            courses.add(course);
        }
    }

    public void removeCourse(Course course) {
        courses.remove(course);
    }

    @Override
    public String toString() {
        String headName = (head != null) ? head.getName() : "Vacant";
        return String.format("Department [%s] %s | Head: %s | Professors: %d | Courses: %d",
                code, name, headName, professors.size(), courses.size());
    }
}
