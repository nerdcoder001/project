package com.cdacQuerybridge.auth_service.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.cdacQuerybridge.auth_service.dto.AuthResponse;
import com.cdacQuerybridge.auth_service.dto.LoginRequest;
import com.cdacQuerybridge.auth_service.dto.RegisterRequest;
import com.cdacQuerybridge.auth_service.dto.UpdateProfileRequest;
import com.cdacQuerybridge.auth_service.entity.User;
import com.cdacQuerybridge.auth_service.mail.EmailService;
import com.cdacQuerybridge.auth_service.repository.UserRepository;
import com.cdacQuerybridge.auth_service.util.JwtUtil;
import com.cdacQuerybridge.auth_service.util.PasswordUtil;
import com.cdacQuerybridge.auth_service.dto.OtpRequest;
import com.cdacQuerybridge.auth_service.dto.PasswordResetRequest;
import com.cdacQuerybridge.auth_service.util.CaptchaUtil;
import com.cdacQuerybridge.auth_service.util.OtpUtil;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;

@Service
public class AuthService {

    private final UserRepository repo;
    private final EmailService emailService;

    public AuthService(UserRepository repo ,EmailService emailService) {
    	
        this.repo = repo;
        this.emailService = emailService;
    }

    // 🔥 AUTO CREATE ADMIN (ONE TIME)
    @PostConstruct
    public void createAdminIfNotExists() {

        String adminEmail = "admin@querybridge.com";

        if (repo.findByEmail(adminEmail).isPresent()) {
            return; // admin already exists
        }

        User admin = new User();
        admin.setName("Super Admin");
        admin.setEmail(adminEmail);
        admin.setPassword(PasswordUtil.hash("admin123"));
        admin.setRole("ADMIN");
        admin.setDesignation("System Admin");

        repo.save(admin);

        System.out.println("✅ Default ADMIN created: admin@querybridge.com / admin123");
    }

    // 🔒 REGISTER → USER ONLY
    public void register(RegisterRequest req, HttpSession session) {

        String sessionCaptcha = (String) session.getAttribute("CAPTCHA");

        if (sessionCaptcha == null ||
            !sessionCaptcha.equalsIgnoreCase(req.captcha)) {
            throw new RuntimeException("Invalid captcha");
        }

        if (repo.findByEmail(req.email).isPresent())
            throw new RuntimeException("Email already exists");

        User user = new User();
        user.setName(req.name);
        user.setEmail(req.email);
        user.setPassword(PasswordUtil.hash(req.password));
        user.setRole("USER");
        user.setDesignation(req.designation);

        repo.save(user);
        session.removeAttribute("CAPTCHA");
    }

    // 🔑 LOGIN → USER + ADMIN
    public AuthResponse login(LoginRequest req, HttpSession session) {

        String sessionCaptcha = (String) session.getAttribute("CAPTCHA");

        if (sessionCaptcha == null ||
            !sessionCaptcha.equalsIgnoreCase(req.captcha)) {
            throw new RuntimeException("Invalid captcha");
        }

        User user = repo.findByEmail(req.email)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!PasswordUtil.match(req.password, user.getPassword()))
            throw new RuntimeException("Invalid credentials");

        session.removeAttribute("CAPTCHA");

        String token = JwtUtil.generateToken(
                user.getEmail(), user.getRole());

        return new AuthResponse(token, user.getRole());
    }
    

    public void sendOtp(OtpRequest req, HttpSession session) {

        String sessionCaptcha = (String) session.getAttribute("CAPTCHA");

        if (sessionCaptcha == null || !sessionCaptcha.equalsIgnoreCase(req.captcha))
            throw new RuntimeException("Invalid captcha");

        String otp = OtpUtil.generateOtp();

        User entity = repo.findByEmail(req.email)
                .orElseThrow(() -> new RuntimeException("User not found"));


        entity.setEmail(req.email);
        entity.setOtpHash(PasswordUtil.hash(otp));
        entity.setExpiryTime(LocalDateTime.now().plusMinutes(5));
        entity.setAttempts(0);

        repo.save(entity);
        emailService.sendOtp(req.email, otp);
    }

    public void resetPassword(PasswordResetRequest req) {

       User otpEntity = repo.findByEmail(req.email)
                .orElseThrow(() -> new RuntimeException("OTP not found"));

        if (otpEntity.getAttempts() >= 3)
            throw new RuntimeException("OTP attempts exceeded");

        if (LocalDateTime.now().isAfter(otpEntity.getExpiryTime()))
            throw new RuntimeException("OTP expired");

        if (!PasswordUtil.match(req.otp, otpEntity.getOtpHash())) {
            otpEntity.setAttempts(otpEntity.getAttempts() + 1);
            repo.save(otpEntity);
            throw new RuntimeException("Invalid OTP");
        }

        if (!req.newPassword.equals(req.confirmPassword))
            throw new RuntimeException("Passwords do not match");

        // � FIX: Hash and Update Password
        otpEntity.setPassword(PasswordUtil.hash(req.newPassword));
        
        // Clear OTP fields
        otpEntity.setOtpHash(null);
        otpEntity.setExpiryTime(null);
        otpEntity.setAttempts(0);

        repo.save(otpEntity);
        System.out.println("✅ PASSWORD RESET SUCCESSFUL FOR: " + req.email);
        System.out.println("✅ NEW HASHED PASSWORD: " + otpEntity.getPassword());
    }
    
    // 👤 GET USER PROFILE
    public User getUserProfile(String email) {
        return repo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    
    // ✏️ UPDATE PROFILE (name, designation, bio only - email and role are read-only)
    public User updateProfile(String email, UpdateProfileRequest request) {
        User user = repo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Update only allowed fields
        if (request.getName() != null && !request.getName().trim().isEmpty()) {
            user.setName(request.getName());
        }
        if (request.getDesignation() != null) {
            user.setDesignation(request.getDesignation());
        }
        if (request.getBio() != null) {
            user.setBio(request.getBio());
        }
        
        return repo.save(user);
    }
    
    // 🗑️ GET ALL USERS (for admin)
    public java.util.List<User> getAllUsers() {
        return repo.findAll();
    }
    
    // 🗑️ DELETE USER (admin only)
    public void deleteUser(String email) {
        User user = repo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        repo.delete(user);
    }
}
