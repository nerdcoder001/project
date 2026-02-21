package com.querybridge.answer_service.dto;

import java.time.LocalDateTime;

public class AnswerWithVotesDTO {
    
    private Long id;
    private Long queryId;
    private String username;
    private String content;
    private String comment;
    private LocalDateTime createdAt;
    private long voteCount;
    private boolean isVoted;

    // Constructors
    public AnswerWithVotesDTO() {}

    public AnswerWithVotesDTO(Long id, Long queryId, String username, String content, 
                              String comment, LocalDateTime createdAt, long voteCount, boolean isVoted) {
        this.id = id;
        this.queryId = queryId;
        this.username = username;
        this.content = content;
        this.comment = comment;
        this.createdAt = createdAt;
        this.voteCount = voteCount;
        this.isVoted = isVoted;
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQueryId() {
        return queryId;
    }

    public void setQueryId(Long queryId) {
        this.queryId = queryId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public long getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(long voteCount) {
        this.voteCount = voteCount;
    }

    public boolean isVoted() {
        return isVoted;
    }

    public void setVoted(boolean voted) {
        isVoted = voted;
    }
    
    // Add getter for JSON serialization
    public boolean getVoted() {
        return isVoted;
    }
}
