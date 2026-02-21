package com.cdacQuerybridge.auth_service.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cdacQuerybridge.auth_service.dto.AuthResponse;
import com.cdacQuerybridge.auth_service.dto.LoginRequest;
import com.cdacQuerybridge.auth_service.dto.RegisterRequest;
import com.cdacQuerybridge.auth_service.dto.UpdateProfileRequest;
import com.cdacQuerybridge.auth_service.dto.UserInfoDto;
import com.cdacQuerybridge.auth_service.entity.User;
import com.cdacQuerybridge.auth_service.repository.UserRepository;
import com.cdacQuerybridge.auth_service.service.AuthService;
import com.cdacQuerybridge.auth_service.util.CaptchaUtil;
import com.cdacQuerybridge.auth_service.util.JwtUtil;
import com.cdacQuerybridge.auth_service.dto.OtpRequest;
import com.cdacQuerybridge.auth_service.dto.PasswordResetRequest;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository repo;
    private final AuthService service;

    public AuthController(AuthService service, UserRepository repo) {
        this.service = service;
        this.repo = repo;
    }

    @GetMapping("/captcha")
    public String getCaptcha(HttpSession session) {
        String captcha = CaptchaUtil.generate();
        session.setAttribute("CAPTCHA", captcha);
        return captcha;
    }

    @PostMapping("/register")
    public Map<String, String> register(
            @RequestBody RegisterRequest request,
            HttpSession session) {

        service.register(request, session);

        Map<String, String> res = new HashMap<>();
        res.put("message", "User registered successfully");
        return res;
    }

    @PostMapping("/login")
    public AuthResponse login(
            @RequestBody LoginRequest request,
            HttpSession session) {

        return service.login(request, session);
    }

    @GetMapping("/user-info")
    public UserInfoDto getUserInfo(
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.replace("Bearer ", "");
        String email = JwtUtil.extractEmail(token);

        User user = repo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserInfoDto dto = new UserInfoDto();
        dto.name = user.getName();
        dto.email = user.getEmail();
        dto.role = user.getRole();
        dto.designation = user.getDesignation();
        return dto;
    }
    
    @PostMapping("/send-otp")
    public String sendOtp(@RequestBody OtpRequest req, HttpSession session) {
        service.sendOtp(req, session);
        return "OTP sent to email";
    }

    @PostMapping("/reset")
    public String reset(@RequestBody PasswordResetRequest req) {
        service.resetPassword(req);
        return "Password reset successful";
    }
    @GetMapping("/users/{name}")
    public UserInfoDto getUserByName(@PathVariable String name) {

        User user = repo.findByName(name)
            .orElseThrow(() -> new RuntimeException("User not found"));

        UserInfoDto dto = new UserInfoDto();
        dto.name = user.getName();
        dto.email = user.getEmail();
        dto.role = user.getRole();
        dto.designation = user.getDesignation();
        return dto;
    }
    
    // 👤 GET USER PROFILE
    @GetMapping("/profile")
    public User getProfile(@RequestParam String email) {
        return service.getUserProfile(email);
    }
    
    // ✏️ UPDATE PROFILE (name, designation, bio only)
    @PutMapping("/profile")
    public User updateProfile(
            @RequestParam String email,
            @RequestBody UpdateProfileRequest request) {
        return service.updateProfile(email, request);
    }
    
    // 🗑️ GET ALL USERS (for admin)
    @GetMapping("/users")
    public java.util.List<User> getAllUsers() {
        return service.getAllUsers();
    }
    
    // 🗑️ DELETE USER (admin only)
    @DeleteMapping("/users/{email}")
    public void deleteUser(@PathVariable String email) {
        service.deleteUser(email);
    }

}
