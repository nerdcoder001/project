package com.cdacQuerybridge.auth_service.mail;


import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender sender;

    public EmailService(JavaMailSender sender) {
        this.sender = sender;
    }

    public void sendOtp(String to, String otp) {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(to);
        mail.setSubject("QueryBridge Password Reset OTP");
        mail.setText("Your OTP is: " + otp + "\nValid for 5 minutes.");

        sender.send(mail);
    }
}
