package com.cdacQuerybridge.support.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.cdacQuerybridge.support.dto.UserInfoDto;

@FeignClient(name = "AUTH-SERVICE")
public interface AuthClient {

    @GetMapping("/auth/user-info")
    UserInfoDto getUserInfo(
        @RequestHeader("Authorization") String token
    );
}

