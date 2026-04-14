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

        Note newNote = new Note(title, content, now, creator);
        DatabaseManager.insertNote(newNote);

        List<Note> allNotes = DatabaseManager.getAllNotes();
        Note lastNnote = allNotes.getLast();

        System.out.println("\nCreated note:");
        System.out.println(lastNnote);

        scanner.close();



    }
}