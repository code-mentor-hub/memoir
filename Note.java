
import java.time.LocalDateTime;
public class Note {

    private int id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime createdBy;

    public Note(int id, String title, String content, LocalDateTime createdAt, LocalDateTime createdBy) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
    }

    public Note(int id, String title, LocalDateTime createdAt, LocalDateTime createdBy) {
        this.id = id;
        this.title = title;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.content = "";
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getCreatedBy() {
        return createdBy;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setCreatedBy(LocalDateTime createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                ", createdBy=" + createdBy +
                '}';
    }
}