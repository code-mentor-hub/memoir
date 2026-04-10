package memoir.db;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:memoir.db";

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(DB_URL);
    }

    public static void initializeDatabase() {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             InputStream input = DatabaseManager.class
                     .getClassLoader()
                     .getResourceAsStream("schema.sql")) {

            if (input == null) {
                System.err.println("Error: schema.sql not found in resources.");
                return;
            }

            String sql = new String(input.readAllBytes(), StandardCharsets.UTF_8);

            if (!sql.trim().isEmpty()) {
                statement.executeUpdate(sql);
                System.out.println("Database is ready!");
            }

        } catch (Exception e) {
            System.err.println("Database initialization failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}