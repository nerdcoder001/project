package com.cdacQuerybridge.auth_service.dto;


public class PasswordResetRequest {
    public String email;
    public String otp;
    public String newPassword;
    public String confirmPassword;
}
