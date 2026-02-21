package com.querybridge.query_service.dto;

import java.time.LocalDateTime;

public class QueryWithCountsDTO {
    
    private Long id;
    private String name;
    private String title;
    private String body;
    private LocalDateTime createdAt;
    private long voteCount;
    private long answerCount;
    private boolean isVoted;

    // Constructors
    public QueryWithCountsDTO() {}

    public QueryWithCountsDTO(Long id, String name, String title, String body, 
                              LocalDateTime createdAt, long voteCount, long answerCount, boolean isVoted) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.body = body;
        this.createdAt = createdAt;
        this.voteCount = voteCount;
        this.answerCount = answerCount;
        this.isVoted = isVoted;
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
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

    public long getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(long answerCount) {
        this.answerCount = answerCount;
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
