package com.cdacQuerybridge.admin_service.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.cdacQuerybridge.admin_service.dto.SupportDto;

@FeignClient(name = "SUPPORT-SERVICE")
public interface SupportClient {

    @GetMapping("/support")
    List<SupportDto> getAllTickets();

    @PutMapping("/support/{id}/resolve")
    void markResolved(@PathVariable Long id);
}
