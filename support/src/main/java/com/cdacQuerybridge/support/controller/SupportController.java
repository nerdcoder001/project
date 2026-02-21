package com.cdacQuerybridge.support.controller;


import org.springframework.web.bind.annotation.*;

import com.cdacQuerybridge.support.dto.CreateSupportRequest;
import com.cdacQuerybridge.support.entity.SupportRequest;
import com.cdacQuerybridge.support.service.SupportService;

@RestController
@RequestMapping("/support")
public class SupportController {

    private final SupportService service;

    public SupportController(SupportService service) {
        this.service = service;
    }

    @PostMapping
    public SupportRequest createSupport(@RequestBody CreateSupportRequest request) {
        return service.createSupportRequest(request);
    }
    
    @GetMapping
    public java.util.List<SupportRequest> getAllTickets() {
        return service.getAllTickets();
    }
    
    @PutMapping("/{id}/resolve")
    public void resolveTicket(@PathVariable Long id) {
        service.resolveTicket(id);
    }
}
