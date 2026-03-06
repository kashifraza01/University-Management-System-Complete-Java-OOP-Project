package university.management.system;

import java.sql.*;

public class Conn {
    public Connection c;
    public Statement s;

    public Conn() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/universitymanagementsystem",
                    "root",
                    "12345");
            s = c.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Database connection failed.");
        }
    }
}
