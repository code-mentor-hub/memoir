package memoir;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

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
  
    static Scanner scanner = new Scanner(System.in);
    static Map<String, String> notes = new HashMap<>();

    public static void main(String[] args) {
      connectDB();

        boolean running = true;
        while (running) {
            showMenu();
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    printNotes();
                    break;
                case "2":
                    searchNote();
                    break;
                case "3":
                    addNote();
                    break;
                case "4":
                    deleteNote();
                    break;
                case "5":
                    modifyNote();
                    break;
                case "6":
                    System.out.println("Exiting...");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
      scanner.close(); 
    } 

    static void showMenu() {
        System.out.println("1. Print all notes");
        System.out.println("2. Search note");
        System.out.println("3. Add note");
        System.out.println("4. Delete note");
        System.out.println("5. Modify note");
        System.out.println("6. Exit");
        System.out.print("Choose option: ");
    }

    static void printNotes() {
        if (notes.isEmpty()) {
            System.out.println("No notes available.");
            return;
        }

        for (String title : notes.keySet()) {
            System.out.println("Title: " + title);
            System.out.println("Content: " + notes.get(title));
        }
    }

    static void searchNote() {
        System.out.print("Enter note id: ");
        int id = scanner.nextInt();

        if (notes.containsKey(name)) {
            System.out.println("Content: " + notes.get(name));
        } else {
            System.out.println("Error: No results found");
        }
    }

    static void addNote() {
        System.out.print("Enter note id: ");
        int id = scanner.nextInt();

        System.out.print("Enter note content: ");
        String content = scanner.nextLine();

        notes.put(name, content);
        System.out.println("Note saved.");
    }

    static void deleteNote() {
        System.out.print("Enter note id to delete: ");
         int id = scanner.nextInt();

        if (notes.containsKey(name)) {
            notes.remove(name);
            System.out.println("Note deleted.");
        } else {
            System.out.println("Error: Note not found");
        }
    }

    static void modifyNote() {
        System.out.print("Enter note id to modify: ");
         int id = scanner.nextInt();

        if (notes.containsKey(name)) {
            System.out.print("Enter new content: ");
            String newContent = scanner.nextLine();

            notes.put(name, newContent);
            System.out.println("Note updated.");
        } else {
            System.out.println("Error: Note not found");
        }
      
    }
}
