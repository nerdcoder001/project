package com.cdacQuerybridge.support.dto;


public class CreateSupportRequest {
    public String name;
    public String email;
    public String role;
    public String designation;
    public String issue;       // Section/Page where issue occurred
    public String description; // Detailed description of the problem
    
    // Getters & Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    
    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }
    
    public String getIssue() { return issue; }
    public void setIssue(String issue) { this.issue = issue; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
