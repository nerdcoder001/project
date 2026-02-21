package com.cdacQuerybridge.admin_service.dto;

import java.time.LocalDateTime;

public class SupportDto {
    public Long id;
    public String name;
    public String email;
    public String role;
    public String issue;
    public String description;
    public boolean resolved;
    public LocalDateTime createdAt;
}
