package bootcamp_2025_03_manthos.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "messages")
public class Message {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "content", nullable = false)
    private String content;

////    @Basic
////    @Column(name = "chatthread_id", nullable = false)
//    private Long chatThreadId;

    @Basic
    @Column(name= "created_at", nullable = false)
    private Timestamp createdAt;

    @Basic
    @Column(name = "isLLMGenerated", nullable = false)
    private boolean isLLMGenerated;

    @ManyToOne
    @JoinColumn(name = "chatthread_id")
    @JsonBackReference
    private ChatThread chatThread;


    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }  

    public boolean isLLMGenerated() {
        return isLLMGenerated;
    }

    public void setLLMGenerated(boolean LLMGenerated) {
        isLLMGenerated = LLMGenerated;
    }
}
