package com.cdacQuerybridge.admin_service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdacQuerybridge.admin_service.dto.QueryDto;
import com.cdacQuerybridge.admin_service.dto.SupportDto;
import com.cdacQuerybridge.admin_service.dto.UserDto;
import com.cdacQuerybridge.admin_service.service.AdminService;
import com.cdacQuerybridge.admin_service.util.JwtUtil;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class AdminController {

    private final AdminService service;

    public AdminController(AdminService service) {
        this.service = service;
    }

    private void adminCheck(String header) {
        // TODO: Fix JWT validation later
        // String token = header.replace("Bearer ", "");
        // JwtUtil.validateAdmin(token);
    }

    @GetMapping("/users")
    public List<UserDto> users(@RequestHeader("Authorization") String h) {
        // adminCheck(h); // Temporarily disabled
        return service.users();
    }

    @DeleteMapping("/users/{email}")
    public void deleteUser(@RequestHeader("Authorization") String h,
                           @PathVariable String email) {
        // adminCheck(h); // Temporarily disabled
        service.deleteUser(email);
    }

    @GetMapping("/queries")
    public List<QueryDto> queries(@RequestHeader("Authorization") String h) {
        // adminCheck(h); // Temporarily disabled
        return service.queries();
    }

    @DeleteMapping("/queries/{id}")
    public void deleteQuery(@RequestHeader("Authorization") String h,
                            @PathVariable Long id) {
        // adminCheck(h); // Temporarily disabled
        service.deleteQuery(id);
    }

    @DeleteMapping("/answers/{id}")
    public void deleteAnswer(@RequestHeader("Authorization") String h,
                             @PathVariable Long id) {
        // adminCheck(h); // Temporarily disabled
        service.deleteAnswer(id);
    }

    @GetMapping("/support")
    public List<SupportDto> support(@RequestHeader("Authorization") String h) {
        // adminCheck(h); // Temporarily disabled
        return service.tickets();
    }

    @PutMapping("/support/{id}/resolve")
    public void resolve(@RequestHeader("Authorization") String h,
                        @PathVariable Long id) {
        // adminCheck(h); // Temporarily disabled
        service.resolve(id);
    }
}
