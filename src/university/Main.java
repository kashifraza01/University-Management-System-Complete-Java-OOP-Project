package university;

/**
 * Entry point for the University Management System.
 * Demonstrates all core OOP features:
 *   - Abstraction   : Person (abstract class), Gradable (interface)
 *   - Encapsulation : private fields with getters/setters in every class
 *   - Inheritance   : Student and Professor extend Person
 *   - Polymorphism  : Gradable.calculateGPA(), Person.getRole(), Person.toString()
 */
public class Main {

    public static void main(String[] args) {

        // ── Create the university ──────────────────────────────────────────
        University university = new University("Springfield University");

        // ── Create departments ─────────────────────────────────────────────
        Department cs = new Department("CS", "Computer Science");
        Department math = new Department("MATH", "Mathematics");
        university.addDepartment(cs);
        university.addDepartment(math);

        // ── Create professors ──────────────────────────────────────────────
        Professor prof1 = new Professor("P001", "Dr. Alan Turing",
                "a.turing@springfield.edu", 45, "Algorithms", "Full Professor");
        Professor prof2 = new Professor("P002", "Dr. Ada Lovelace",
                "a.lovelace@springfield.edu", 38, "Programming Languages", "Associate Professor");
        Professor prof3 = new Professor("P003", "Dr. Carl Gauss",
                "c.gauss@springfield.edu", 52, "Calculus", "Full Professor");

        university.addProfessor(prof1);
        university.addProfessor(prof2);
        university.addProfessor(prof3);

        cs.setHead(prof1);
        cs.addProfessor(prof2);
        math.setHead(prof3);

        // ── Create courses ─────────────────────────────────────────────────
        Course cs101 = new Course("CS101", "Introduction to Programming", 3, 30);
        Course cs201 = new Course("CS201", "Data Structures", 3, 25);
        Course math101 = new Course("MATH101", "Calculus I", 4, 35);

        university.addCourse(cs101);
        university.addCourse(cs201);
        university.addCourse(math101);

        cs.addCourse(cs101);
        cs.addCourse(cs201);
        math.addCourse(math101);

        // Assign instructors to courses
        university.assignInstructor("P002", "CS101");
        university.assignInstructor("P001", "CS201");
        university.assignInstructor("P003", "MATH101");

        // ── Create students ────────────────────────────────────────────────
        Student s1 = new Student("S001", "Alice Johnson", "alice@student.edu", 20, "Computer Science", 2023);
        Student s2 = new Student("S002", "Bob Smith", "bob@student.edu", 21, "Computer Science", 2022);
        Student s3 = new Student("S003", "Carol White", "carol@student.edu", 19, "Mathematics", 2024);

        university.addStudent(s1);
        university.addStudent(s2);
        university.addStudent(s3);

        // ── Enroll students ────────────────────────────────────────────────
        university.enrollStudent("S001", "CS101", "Fall-2024");
        university.enrollStudent("S001", "MATH101", "Fall-2024");
        university.enrollStudent("S002", "CS101", "Fall-2024");
        university.enrollStudent("S002", "CS201", "Fall-2024");
        university.enrollStudent("S003", "MATH101", "Fall-2024");
        // Attempt duplicate enrollment (should be blocked)
        university.enrollStudent("S001", "CS101", "Fall-2024");

        // ── Assign grades ──────────────────────────────────────────────────
        university.assignGrade("S001", "CS101", "Fall-2024", 3.7);
        university.assignGrade("S001", "MATH101", "Fall-2024", 3.3);
        university.assignGrade("S002", "CS101", "Fall-2024", 2.7);
        university.assignGrade("S002", "CS201", "Fall-2024", 3.0);
        university.assignGrade("S003", "MATH101", "Fall-2024", 4.0);

        // ── Print reports ──────────────────────────────────────────────────
        university.printUniversitySummary();

        university.printDepartmentReport("CS");
        university.printDepartmentReport("MATH");

        university.printStudentReport("S001");
        university.printStudentReport("S002");
        university.printStudentReport("S003");

        university.printCourseReport("CS101");
        university.printCourseReport("MATH101");

        // ── Polymorphism demo: iterate over mixed list ─────────────────────
        System.out.println("\n=== All Persons (Polymorphism Demo) ===");
        java.util.List<Person> allPersons = new java.util.ArrayList<>();
        allPersons.addAll(university.getProfessors());
        allPersons.addAll(university.getStudents());
        for (Person p : allPersons) {
            // getRole() and toString() are polymorphically dispatched
            System.out.println(p);
        }
    }
}
