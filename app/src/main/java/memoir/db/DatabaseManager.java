package memoir.db;

import memoir.model.Note;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public static void insertNote(Note note) {
        String sql = "INSERT INTO notes (title, content, created_at, created_by) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, note.getTitle());
            stmt.setString(2, note.getContent());
            stmt.setString(3, note.getCreatedAt().toString());
            stmt.setString(4, note.getCreatedBy());
            stmt.executeUpdate();

        } catch (Exception e) {
            System.err.println("Insert failed: " + e.getMessage());
        }
    }

    public static Note findNoteById(int id) {
        String sql = "SELECT * FROM notes WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Note note = mapRow(rs);
                System.out.println(note);
                return note;
            } else {
                System.out.println("Note not found.");
            }
        } catch (Exception e) {
            System.err.println("Find failed: " + e.getMessage());
        }

        return null;
    }

    public static void deleteNoteById(int id) {
        String sql = "DELETE FROM notes WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (Exception e) {
            System.err.println("Delete failed: " + e.getMessage());
        }
    }

    public static void modifyNoteById(int id, String newContent) {
        String sql = "UPDATE notes SET content = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newContent);
            stmt.setInt(2, id);
            stmt.executeUpdate();

        } catch (Exception e) {
            System.err.println("Modify failed: " + e.getMessage());
        }
    }

    public static boolean noteExists(int id) {
        String sql = "SELECT 1 FROM notes WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (Exception e) {
            System.err.println("Exists check failed: " + e.getMessage());
        }
        return false;
    }

    public static List<Note> getAllNotes() {
        String sql = "SELECT * FROM notes";
        List<Note> notes = new ArrayList<>();
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                notes.add(mapRow(rs));
            }
        } catch (Exception e) {
            System.err.println("Get all failed: " + e.getMessage());
        }

        if (notes.isEmpty()) {
            System.out.println("No notes found.");
        } else {
            for (Note n : notes) {
                System.out.println(n);
            }
        }

        return notes;
    }
    private static Note mapRow(ResultSet rs) throws SQLException {
        return new Note(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("content"),
                LocalDateTime.parse(rs.getString("created_at")),
                rs.getString("created_by")
        );
    }
}


