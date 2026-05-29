package memoir;

import memoir.db.DatabaseManager;
import memoir.model.Note;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {

    private static final Logger logger = Logger.getLogger(App.class.getName());

    static Scanner scanner = new Scanner(System.in);
    static Map<String, String> notes = new HashMap<>();

    public static void main(String[] args) {

        logger.info("Application started");

        try {
            logger.info("Initializing database...");
            DatabaseManager.initializeDatabase();
            logger.info("Database initialized successfully");

            boolean running = true;

            while (running) {
                showMenu();

                String choice = scanner.nextLine().trim();
                logger.info("User selected menu option: " + choice);

                switch (choice) {
                    case "1":
                        logger.info("Fetching all notes");
                        DatabaseManager.getAllNotes();
                        logger.info("Finished fetching notes");
                        break;

                    case "2":
                        try {
                            System.out.println("Please enter the note id to search: ");
                            int id = Integer.parseInt(scanner.nextLine().trim());

                            logger.info("Searching for note with ID: " + id);
                            DatabaseManager.findNoteById(id);
                        } catch (NumberFormatException e) {
                            logger.warning("Invalid ID entered for search");
                            System.out.println("Invalid ID. Please enter a number.");
                        }
                        break;

                    case "3":
                        System.out.println("Please enter the note title: ");
                        String title = scanner.nextLine();

                        System.out.println("Please enter the note content: ");
                        String content = scanner.nextLine();

                        System.out.println("Please enter the name of author: ");
                        String author = scanner.nextLine();

                        logger.info("Creating new note with title: " + title);

                        Note note = new Note(
                                title,
                                content,
                                LocalDateTime.now(),
                                author
                        );

                        DatabaseManager.insertNote(note);
                        logger.info("Note inserted successfully");
                        break;

                    case "4":
                        try {
                            System.out.println("Please enter the note id to delete: ");
                            int deleteId = Integer.parseInt(scanner.nextLine().trim());

                            logger.info("Deleting note with ID: " + deleteId);
                            DatabaseManager.deleteNote(deleteId);
                            logger.info("Delete operation completed");
                        } catch (NumberFormatException e) {
                            logger.warning("Invalid ID entered for deletion");
                            System.out.println("Invalid ID. Please enter a number.");
                        }
                        break;

                    case "5":
                        try {
                            System.out.println("Please enter the note id to modify: ");
                            int modifyId = Integer.parseInt(scanner.nextLine().trim());

                            System.out.println("Please enter new title: ");
                            String newTitle = scanner.nextLine();

                            System.out.println("Please enter new content: ");
                            String newContent = scanner.nextLine();

                            System.out.println("Please enter new author: ");
                            String newAuthor = scanner.nextLine();

                            logger.info("Modifying note with ID: " + modifyId);

                            Note updatedNote = new Note(
                                    newTitle,
                                    newContent,
                                    LocalDateTime.now(),
                                    newAuthor
                            );

                            DatabaseManager.modifyNote(modifyId, updatedNote);
                            logger.info("Note modified successfully");
                        } catch (NumberFormatException e) {
                            logger.warning("Invalid ID entered for modification");
                            System.out.println("Invalid ID. Please enter a number.");
                        }
                        break;

                    case "6":
                        logger.info("Application shutdown requested");
                        System.out.println("Exiting...");
                        running = false;
                        break;

                    default:
                        logger.warning("Invalid menu option selected: " + choice);
                        System.out.println("Invalid option.");
                }
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unexpected application error", e);
        } finally {
            logger.info("Closing scanner");
            scanner.close();

            logger.info("Application stopped");
        }
    }

    static void showMenu() {
        logger.fine("Displaying menu");

        System.out.println("1. Print all notes");
        System.out.println("2. Search note");
        System.out.println("3. Add note");
        System.out.println("4. Delete note");
        System.out.println("5. Modify note");
        System.out.println("6. Exit");
        System.out.print("Choose option: ");
    }

    static void printNotes() {
        logger.info("Printing notes");

        if (notes.isEmpty()) {
            logger.info("No notes available");
            System.out.println("No notes available.");
            return;
        }

        for (String title : notes.keySet()) {
            logger.fine("Printing note: " + title);

            System.out.println("Title: " + title);
            System.out.println("Content: " + notes.get(title));
        }
    }
}