import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter note id: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter note title: ");
        String title = scanner.nextLine();

        System.out.print("Enter note content: ");
        String content = scanner.nextLine();

        LocalDateTime now = LocalDateTime.now();

        Note note = new Note(id, title, content, now, now);

        System.out.println("\nCreated note:");
        System.out.println(note);

        scanner.close();
    }
}