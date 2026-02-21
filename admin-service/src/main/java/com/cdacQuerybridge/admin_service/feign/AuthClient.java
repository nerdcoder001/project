package com.cdacQuerybridge.admin_service.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cdacQuerybridge.admin_service.dto.UserDto;

@FeignClient(name = "AUTH-SERVICE")
public interface AuthClient {

    @GetMapping("/auth/users")
    List<UserDto> getAllUsers();

    @DeleteMapping("/auth/users/{email}")
    void deleteUser(@PathVariable String email);
}
