package com.cdacQuerybridge.support.service;


import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.cdacQuerybridge.support.dto.CreateSupportRequest;
import com.cdacQuerybridge.support.entity.SupportRequest;
import com.cdacQuerybridge.support.repository.SupportRepository;

@Service
public class SupportService {

    private final SupportRepository repo;

    public SupportService(SupportRepository repo) {
        this.repo = repo;
    }
    
    public SupportRequest createSupportRequest(CreateSupportRequest req) {
        SupportRequest entity = new SupportRequest();
        entity.setName(req.getName());
        entity.setEmail(req.getEmail());
        entity.setRole(req.getRole());
        entity.setDesignation(req.getDesignation());
        entity.setIssue(req.getIssue());
        entity.setDescription(req.getDescription());
        entity.setCreatedAt(LocalDateTime.now());
        entity.setResolved(false);

        return repo.save(entity);
    }
    
    public java.util.List<SupportRequest> getAllTickets() {
        return repo.findAll();
    }
    
    public void resolveTicket(Long id) {
        SupportRequest s = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        s.setResolved(true);
        repo.save(s);
    }
}
