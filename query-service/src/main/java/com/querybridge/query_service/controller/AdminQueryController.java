package com.querybridge.query_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.querybridge.query_service.service.QueryService;

/**
 * Separate controller for admin-service compatibility
 * Handles requests at /queries path (without /api prefix)
 */
@RestController
@RequestMapping("/queries")
public class AdminQueryController {

    private final QueryService queryService;

    public AdminQueryController(QueryService queryService) {
        this.queryService = queryService;
    }

    @GetMapping
    public ResponseEntity<java.util.List<com.querybridge.query_service.dto.QueryWithCountsDTO>> getAllQueries() {
        return ResponseEntity.ok(queryService.getAllQueries(null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuery(@PathVariable Long id) {
        queryService.deleteQuery(id);
        return ResponseEntity.ok().build();
    }
}
