package memoir;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.time.LocalDateTime;
import memoir.model.Note;

public class App {
      public static void connectDB() {

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
        connectDB();

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter note id: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter note title: ");
        String title = scanner.nextLine();

        System.out.print("Enter note content: ");
        String content = scanner.nextLine();

        System.out.print("Enter name who writes note?: ");
        String creator = scanner.nextLine();

        LocalDateTime now = LocalDateTime.now();

        Note note = new Note(id, title, content, now, creator);

        System.out.println("\nCreated note:");
        System.out.println(note);

        scanner.close();

    }
}

