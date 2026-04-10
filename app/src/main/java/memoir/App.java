package memoir;

import java.utils.Scanner;

public class App {
  
    public static void main(String[] args) {
        DatabaseManager.initializeDatabase();

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter note id: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter note title: ");
        String title = scanner.nextLine();

        System.out.print("Enter note content: ");
        String content = scanner.nextLine();

        System.out.print("Enter name who writes the note?: ");
        String creator = scanner.nextLine();

        LocalDateTime now = LocalDateTime.now();

        Note note = new Note(id, title, content, now, creator);

        System.out.println("\nCreated note:");
        System.out.println(note);

        scanner.close();

    }
}

