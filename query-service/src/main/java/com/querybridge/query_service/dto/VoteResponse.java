package com.querybridge.query_service.dto;

public class VoteResponse {
    
    private long voteCount;
    private boolean isVoted;

    public VoteResponse() {}

    public VoteResponse(long voteCount, boolean isVoted) {
        this.voteCount = voteCount;
        this.isVoted = isVoted;
    }

    // Getters & Setters
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
