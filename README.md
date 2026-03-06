# University Management System — Java OOP

A console-based University Management System implemented in Java, demonstrating core Object-Oriented Programming principles.

## OOP Concepts Demonstrated

| Concept | Where |
|---|---|
| **Abstraction** | `Person` (abstract class), `Gradable` (interface) |
| **Encapsulation** | Private fields with getters/setters in every class |
| **Inheritance** | `Student` and `Professor` extend `Person` |
| **Polymorphism** | `getRole()`, `toString()`, and `calculateGPA()` dispatched at runtime |

## Project Structure

```
src/university/
├── Person.java        # Abstract base class for all persons
├── Gradable.java      # Interface for gradable entities
├── Student.java       # Extends Person, implements Gradable
├── Professor.java     # Extends Person
├── Course.java        # Academic course with capacity management
├── Enrollment.java    # Links a Student to a Course with a grade
├── Department.java    # Groups professors and courses
├── University.java    # Central manager (add/find/enroll/grade/report)
└── Main.java          # Entry point with a full demo
```

## How to Build and Run

```bash
# Compile
javac -d out src/university/*.java

# Run
java -cp out university.Main
```

## Features

- **Student management** – add, find, remove students; track major and enrollment year
- **Professor management** – add professors with rank and specialization; assign to courses
- **Course management** – create courses with credit hours and capacity limits
- **Department management** – group professors and courses; assign a department head
- **Enrollment** – enroll students in courses (with duplicate and capacity checks)
- **Grading** – assign 0.0–4.0 GPA-scale grades; auto-calculate letter grades and GPA
- **Reporting** – per-student, per-course, per-department, and university-wide summaries