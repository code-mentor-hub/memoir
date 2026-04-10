package memoir;

import memoir.db.DatabaseManager;
import memoir.model.Note;

import java.util.List;
import java.util.Scanner;
import java.time.LocalDateTime;

public class App {

    public static void main(String[] args) {
        DatabaseManager.initializeDatabase();

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter note title: ");
        String title = scanner.nextLine();

        System.out.print("Enter note content: ");
        String content = scanner.nextLine();

        System.out.print("Enter the name of creator: ");
        String creator = scanner.nextLine();

        LocalDateTime now = LocalDateTime.now();

        DatabaseManager.insertNote(title, content, now, creator);

        List<Note> allNotes = DatabaseManager.getAllNotes();
        Note note = allNotes.get(allNotes.size() - 1);

        System.out.println("\nCreated note:");
        System.out.println(note);

        scanner.close();
    }
}