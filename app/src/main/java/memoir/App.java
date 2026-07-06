package memoir;

import memoir.db.DatabaseManager;
import memoir.model.Note;

import java.time.LocalDateTime;
import java.util.Scanner;

public class App {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        DatabaseManager.initializeDatabase();
        boolean running = true;
        while (running) {
            showMenu();
            String choice = SCANNER.nextLine();

            switch (choice) {
                case "1":
                    DatabaseManager.getAllNotes();
                    break;
                case "2":
                    System.out.println("Please enter the note id to search: ");
                    int id = Integer.parseInt(SCANNER.nextLine().trim());
                    DatabaseManager.findNoteById(id);
                    break;
                case "3":
                    System.out.println("Please enter the note title: ");
                    String title = SCANNER.nextLine();
                    System.out.println("Please enter the note content: ");
                    String content = SCANNER.nextLine();
                    System.out.println("Please enter the name of author: ");
                    String author = SCANNER.nextLine();

                    Note note = new Note(title, content, LocalDateTime.now(), author);
                    DatabaseManager.insertNote(note);
                    break;
                case "4":
                    System.out.println("Please enter the note id to delete: ");
                    int deleteId = Integer.parseInt(SCANNER.nextLine().trim());
                    DatabaseManager.deleteNoteById(deleteId);
                    break;
                case "5":
                    System.out.println("Please enter the note id to modify: ");
                    int modifyId = Integer.parseInt(SCANNER.nextLine().trim());
                    System.out.println("Please enter new title: ");
                    String newTitle = SCANNER.nextLine();
                    System.out.println("Please enter new content: ");
                    String newContent = SCANNER.nextLine();
                    System.out.println("Please enter new author: ");
                    String newAuthor = SCANNER.nextLine();
                    Note updatedNote = new Note(newTitle, newContent, LocalDateTime.now(), newAuthor);
                    DatabaseManager.modifyNoteById(modifyId, updatedNote);
                    break;
                case "6":
                    System.out.println("Exiting...");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
        SCANNER.close();
    }

    private static void showMenu() {
        System.out.println("1. Print all notes");
        System.out.println("2. Search note");
        System.out.println("3. Add note");
        System.out.println("4. Delete note");
        System.out.println("5. Modify note");
        System.out.println("6. Exit");
        System.out.print("Choose option: ");
    }
}
