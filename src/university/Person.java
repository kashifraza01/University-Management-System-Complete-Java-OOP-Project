package university;

/**
 * Abstract base class representing a person in the university system.
 * Demonstrates abstraction and encapsulation.
 */
public abstract class Person {
    private String id;
    private String name;
    private String email;
    private int age;

    public Person(String id, String name, String email, int age) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public int getAge() { return age; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setAge(int age) { this.age = age; }

    /**
     * Abstract method forcing subclasses to describe their role.
     */
    public abstract String getRole();

    @Override
    public String toString() {
        return String.format("[%s] ID: %s | Name: %s | Email: %s | Age: %d",
                getRole(), id, name, email, age);
    }
}
