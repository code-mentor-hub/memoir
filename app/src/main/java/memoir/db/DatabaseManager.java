package memoir.db;

import memoir.model.Note;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseManager {

    private static final Logger LOGGER =
            Logger.getLogger(DatabaseManager.class.getName());

    private static final String DB_URL = "jdbc:sqlite:memoir.db";

    public static Connection getConnection() throws SQLException {
        LOGGER.fine("Opening database connection");
        return DriverManager.getConnection(DB_URL);
    }

    public static void initializeDatabase() {
        LOGGER.info("Initializing database");

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             InputStream input = DatabaseManager.class
                     .getClassLoader()
                     .getResourceAsStream("schema.sql")) {

            if (input == null) {
                LOGGER.severe("schema.sql not found in resources");
                return;
            }

            String sql = new String(input.readAllBytes(), StandardCharsets.UTF_8);

            if (!sql.trim().isEmpty()) {
                statement.executeUpdate(sql);
                LOGGER.info("Database initialized successfully");
            } else {
                LOGGER.warning("schema.sql is empty");
            }

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Database initialization failed", e);
        }
    }

    public static void insertNote(Note note) {
        String sql =
                "INSERT INTO notes (title, content, created_at, created_by) VALUES (?, ?, ?, ?)";

        LOGGER.info(() -> "Inserting note: " + note.getTitle());

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, note.getTitle());
            stmt.setString(2, note.getContent());
            stmt.setString(3, note.getCreatedAt().toString());
            stmt.setString(4, note.getCreatedBy());

            int rows = stmt.executeUpdate();

            LOGGER.info(() -> "Inserted note successfully. Rows affected: " + rows);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Insert failed", e);
        }
    }

    public static Note findNoteById(int id) {
        String sql = "SELECT * FROM notes WHERE id = ?";

        LOGGER.info(() -> "Searching for note with id=" + id);

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Note note = mapRow(rs);

                LOGGER.info(() -> "Found note with id=" + id);

                return note;
            } else {
                LOGGER.warning(() -> "Note not found. id=" + id);
            }

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Find failed for id=" + id, e);
        }

        return null;
    }

    public static void deleteNoteById(int id) {
        String sql = "DELETE FROM notes WHERE id = ?";

        LOGGER.info(() -> "Deleting note with id=" + id);

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                LOGGER.info(() -> "Deleted note with id=" + id);
            } else {
                LOGGER.warning(() -> "No note found to delete. id=" + id);
            }

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Delete failed for id=" + id, e);
        }
    }

    public static void modifyNoteById(int id, Note updatedNote) {
        String sql = "UPDATE notes SET content = ? WHERE id = ?";

        LOGGER.info(() -> "Updating note with id=" + id);

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, updatedNote.getContent());
            stmt.setInt(2, id);

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                LOGGER.info(() -> "Updated note with id=" + id);
            } else {
                LOGGER.warning(() -> "No note found to update. id=" + id);
            }

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Modify failed for id=" + id, e);
        }
    }

    public static List<Note> getAllNotes() {
        String sql = "SELECT * FROM notes";

        LOGGER.info("Loading all notes");

        List<Note> notes = new ArrayList<>();

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                notes.add(mapRow(rs));
            }

            LOGGER.info(() ->
                    "Loaded " + notes.size() + " notes from database");

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Get all notes failed", e);
        }

        if (notes.isEmpty()) {
            LOGGER.info("No notes found");
        }

        return notes;
    }

    private static Note mapRow(ResultSet rs) throws SQLException {
        LOGGER.fine("Mapping database row to Note object");

        return new Note(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("content"),
                LocalDateTime.parse(rs.getString("created_at")),
                rs.getString("created_by")
        );
    }
}
