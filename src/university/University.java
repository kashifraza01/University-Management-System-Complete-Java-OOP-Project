package university;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Central management class for the University.
 * Orchestrates all entities: students, professors, courses, departments.
 * Demonstrates composition and high-level management operations.
 */
public class University {
    private String name;
    private List<Student> students;
    private List<Professor> professors;
    private List<Course> courses;
    private List<Department> departments;
    private List<Enrollment> enrollments;

    public University(String name) {
        this.name = name;
        this.students = new ArrayList<>();
        this.professors = new ArrayList<>();
        this.courses = new ArrayList<>();
        this.departments = new ArrayList<>();
        this.enrollments = new ArrayList<>();
    }

    public String getName() { return name; }

    // ── Student operations ──────────────────────────────────────────────────

    public void addStudent(Student student) {
        students.add(student);
    }

    public Student findStudent(String id) {
        for (Student s : students) {
            if (s.getId().equals(id)) return s;
        }
        return null;
    }

    public boolean removeStudent(String id) {
        Student s = findStudent(id);
        if (s == null) return false;
        students.remove(s);
        return true;
    }

    public List<Student> getStudents() { return Collections.unmodifiableList(students); }

    // ── Professor operations ────────────────────────────────────────────────

    public void addProfessor(Professor professor) {
        professors.add(professor);
    }

    public Professor findProfessor(String id) {
        for (Professor p : professors) {
            if (p.getId().equals(id)) return p;
        }
        return null;
    }

    public boolean removeProfessor(String id) {
        Professor p = findProfessor(id);
        if (p == null) return false;
        professors.remove(p);
        return true;
    }

    public List<Professor> getProfessors() { return Collections.unmodifiableList(professors); }

    // ── Course operations ───────────────────────────────────────────────────

    public void addCourse(Course course) {
        courses.add(course);
    }

    public Course findCourse(String courseCode) {
        for (Course c : courses) {
            if (c.getCourseCode().equals(courseCode)) return c;
        }
        return null;
    }

    public boolean removeCourse(String courseCode) {
        Course c = findCourse(courseCode);
        if (c == null) return false;
        courses.remove(c);
        return true;
    }

    public List<Course> getCourses() { return Collections.unmodifiableList(courses); }

    // ── Department operations ───────────────────────────────────────────────

    public void addDepartment(Department department) {
        departments.add(department);
    }

    public Department findDepartment(String code) {
        for (Department d : departments) {
            if (d.getCode().equals(code)) return d;
        }
        return null;
    }

    public List<Department> getDepartments() { return Collections.unmodifiableList(departments); }

    // ── Enrollment operations ───────────────────────────────────────────────

    /**
     * Enrolls a student in a course for a given semester.
     *
     * @return the created Enrollment, or null if the course is full or
     *         the student is already enrolled in that course/semester.
     */
    public Enrollment enrollStudent(String studentId, String courseCode, String semester) {
        Student student = findStudent(studentId);
        Course course = findCourse(courseCode);

        if (student == null) {
            System.out.println("Student not found: " + studentId);
            return null;
        }
        if (course == null) {
            System.out.println("Course not found: " + courseCode);
            return null;
        }
        if (!course.hasCapacity()) {
            System.out.println("Course " + courseCode + " is full.");
            return null;
        }

        // Prevent duplicate enrollment
        for (Enrollment e : enrollments) {
            if (e.getStudent().getId().equals(studentId)
                    && e.getCourse().getCourseCode().equals(courseCode)
                    && e.getSemester().equals(semester)) {
                System.out.println("Student already enrolled in " + courseCode + " for " + semester);
                return null;
            }
        }

        Enrollment enrollment = new Enrollment(student, course, semester);
        enrollments.add(enrollment);
        student.addEnrollment(enrollment);
        course.addEnrollment(enrollment);
        return enrollment;
    }

    /**
     * Assigns a grade to an existing enrollment.
     */
    public boolean assignGrade(String studentId, String courseCode, String semester, double grade) {
        for (Enrollment e : enrollments) {
            if (e.getStudent().getId().equals(studentId)
                    && e.getCourse().getCourseCode().equals(courseCode)
                    && e.getSemester().equals(semester)) {
                e.setGrade(grade);
                return true;
            }
        }
        System.out.println("Enrollment not found for student " + studentId + " in " + courseCode);
        return false;
    }

    public List<Enrollment> getEnrollments() { return Collections.unmodifiableList(enrollments); }

    // ── Assign instructor ───────────────────────────────────────────────────

    public boolean assignInstructor(String professorId, String courseCode) {
        Professor professor = findProfessor(professorId);
        Course course = findCourse(courseCode);
        if (professor == null || course == null) return false;
        course.setInstructor(professor);
        professor.assignCourse(course);
        return true;
    }

    // ── Reporting ───────────────────────────────────────────────────────────

    public void printStudentReport(String studentId) {
        Student student = findStudent(studentId);
        if (student == null) {
            System.out.println("Student not found: " + studentId);
            return;
        }
        System.out.println("\n=== Student Report ===");
        System.out.println(student);
        if (student.getEnrollments().isEmpty()) {
            System.out.println("  No enrollments.");
        } else {
            for (Enrollment e : student.getEnrollments()) {
                System.out.println("  " + e);
            }
        }
    }

    public void printCourseReport(String courseCode) {
        Course course = findCourse(courseCode);
        if (course == null) {
            System.out.println("Course not found: " + courseCode);
            return;
        }
        System.out.println("\n=== Course Report ===");
        System.out.println(course);
        if (course.getEnrollments().isEmpty()) {
            System.out.println("  No students enrolled.");
        } else {
            for (Enrollment e : course.getEnrollments()) {
                System.out.println("  " + e.getStudent().getName()
                        + " | Grade: " + e.getGrade()
                        + " (" + e.getLetterGrade() + ")");
            }
        }
    }

    public void printDepartmentReport(String departmentCode) {
        Department dept = findDepartment(departmentCode);
        if (dept == null) {
            System.out.println("Department not found: " + departmentCode);
            return;
        }
        System.out.println("\n=== Department Report ===");
        System.out.println(dept);
        System.out.println("  Professors:");
        for (Professor p : dept.getProfessors()) {
            System.out.println("    " + p);
        }
        System.out.println("  Courses:");
        for (Course c : dept.getCourses()) {
            System.out.println("    " + c);
        }
    }

    public void printUniversitySummary() {
        System.out.println("\n=== " + name + " Summary ===");
        System.out.println("  Departments : " + departments.size());
        System.out.println("  Professors  : " + professors.size());
        System.out.println("  Courses     : " + courses.size());
        System.out.println("  Students    : " + students.size());
        System.out.println("  Enrollments : " + enrollments.size());
    }
}
