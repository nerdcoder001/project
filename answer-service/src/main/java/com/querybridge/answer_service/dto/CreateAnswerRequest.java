package com.querybridge.answer_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateAnswerRequest {

    @NotNull
    private Long queryId;

    @NotBlank
    private String username;
    
    private String comment;
    @NotBlank
    private String content;

    public Long getQueryId() { return queryId; }
//    public Long getUserId() { return userId; }
    public String getContent() { return content; }

    public String getUsername() {
		return username;
	}
    public String getComment() {
    	        return comment;  }
    public void setComment(String comment) {
    	        this.comment = comment;
    }
	public void setUsername(String username) {
		this.username = username;
	}
	public void setQueryId(Long queryId) { this.queryId = queryId; }
//    public void setUserId(Long userId) { this.userId = userId; }
    public void setContent(String content) { this.content = content; }
}
