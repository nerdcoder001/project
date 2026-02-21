package com.querybridge.query_service.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.querybridge.query_service.dto.CreateQueryRequest;
import com.querybridge.query_service.dto.QueryWithCountsDTO;
import com.querybridge.query_service.dto.VoteResponse;
import com.querybridge.query_service.entity.Query;
import com.querybridge.query_service.service.QueryService;

@RestController
@RequestMapping("/api/queries")
public class QueryController {

    private final QueryService queryService;

    public QueryController(QueryService queryService) {
        this.queryService = queryService;
    }

    @PostMapping
    public ResponseEntity<Query> create(@RequestBody CreateQueryRequest request) {
        return ResponseEntity.ok(queryService.createQuery(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Query> getById(@PathVariable String UserName) {
        return ResponseEntity.ok(
                queryService.getQueryByUserName(UserName)
        );
    }
    
    @GetMapping
    public ResponseEntity<List<QueryWithCountsDTO>> getAllQueries(
            @RequestHeader(value = "X-User-Email", required = false) String userEmail) {
        return ResponseEntity.ok(queryService.getAllQueries(userEmail));
    }
    
    @PostMapping("/{id}/vote")
    public ResponseEntity<VoteResponse> vote(
            @PathVariable Long id, 
            @RequestHeader("X-User-Email") String userEmail) {
        return ResponseEntity.ok(queryService.toggleVote(id, userEmail));
    }
    
    @DeleteMapping("/{id}/vote")
    public ResponseEntity<VoteResponse> unvote(
            @PathVariable Long id,
            @RequestHeader("X-User-Email") String userEmail) {
        // Toggle vote works for both adding and removing
        return ResponseEntity.ok(queryService.toggleVote(id, userEmail));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuery(
            @PathVariable Long id,
            @RequestHeader(value = "X-User-Role", defaultValue = "USER") String role) {
        
        if (!"ADMIN".equals(role)) {
            return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).body("Access Denied: Admin only");
        }
        queryService.deleteQuery(id);
        return ResponseEntity.ok().build();
    }
}
