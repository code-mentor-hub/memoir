package memoir;

import java.sql.DriverManager;
import java.sql.SQLException;

public class App {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void connect() {

        String url = "jdbc:sqlite:./memoir.db";

        try (var conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                System.out.println("Connection to SQLite has been established.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        System.out.println(new App().getGreeting());
        connect();
    }
}
