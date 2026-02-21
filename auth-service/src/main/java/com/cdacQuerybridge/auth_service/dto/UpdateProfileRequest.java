package com.cdacQuerybridge.auth_service.dto;

public class UpdateProfileRequest {
    
    private String name;
    private String designation;
    private String bio;

    // Constructors
    public UpdateProfileRequest() {}

    public UpdateProfileRequest(String name, String designation, String bio) {
        this.name = name;
        this.designation = designation;
        this.bio = bio;
    }

    // Getters & Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
