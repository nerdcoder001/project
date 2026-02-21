package com.querybridge.answer_service.entity;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "answers")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long queryId;

    @Column(nullable = false)
//    private Long userId;
    private String username;

    @Column(nullable = false, length = 3000)
    private String content;
    
    @Column(nullable = true)
    private String comment;

    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // getters & setters
    
    public Long getId() { return id; }
    public Long getQueryId() { return queryId; }
//    public Long getUserId() { return userId; }
    public String getContent() { return content; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    
    
 

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setId(Long id) { this.id = id; }
    public void setQueryId(Long queryId) { this.queryId = queryId; }
//    public void setUserId(Long userId) { this.userId = userId; }
    public void setContent(String content) { this.content = content; }
}
