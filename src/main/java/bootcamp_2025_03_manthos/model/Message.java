package bootcamp_2025_03_manthos.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import bootcamp_2025_03_manthos.model.ChatThread;

import java.sql.Timestamp;

@Entity
@Table(name = "messages")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Basic
    @Column(name= "created_at", nullable = false)
    private Timestamp createdAt;

    @Column(name = "isLLMGenerated", nullable = false)
    private boolean isLLMGenerated;

    @Column(name = "completion_model")
    public String completionModel;

    @ManyToOne
    @JoinColumn(name = "thread_id")
    @JsonBackReference
    private ChatThread thread;

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = new Timestamp(System.currentTimeMillis());
        }
    }

}
