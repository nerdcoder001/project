package com.querybridge.answer_service.controller;


import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import com.querybridge.answer_service.dto.AnswerWithVotesDTO;
import com.querybridge.answer_service.dto.CreateAnswerRequest;
import com.querybridge.answer_service.dto.VoteResponse;
import com.querybridge.answer_service.entity.Answer;
import com.querybridge.answer_service.repository.AnswerRepository;
import com.querybridge.answer_service.service.AnswerService;

@RestController
@RequestMapping("/api/answers")
public class AnswerController {

    private final AnswerService answerService;
    private final AnswerRepository answerRepository;

    public AnswerController(AnswerService answerService, AnswerRepository answerRepository) {
        this.answerService = answerService;
        this.answerRepository = answerRepository;
    }

    @PostMapping
    public ResponseEntity<Answer> create(@RequestBody CreateAnswerRequest request) {
        return ResponseEntity.ok(answerService.createAnswer(request));
    }

    @GetMapping("/query/{queryId}")
    public ResponseEntity<List<AnswerWithVotesDTO>> getByQuery(
            @PathVariable Long queryId,
            @RequestHeader(value = "X-User-Email", required = false) String userEmail) {
        return ResponseEntity.ok(answerService.getAnswersWithVotes(queryId, userEmail));
    }
    
    @GetMapping("/count/{queryId}")
    public ResponseEntity<Long> getAnswerCount(@PathVariable Long queryId) {
        long count = answerRepository.findByQueryId(queryId).size();
        return ResponseEntity.ok(count);
    }
    
    @PostMapping("/{id}/vote")
    public ResponseEntity<VoteResponse> vote(
            @PathVariable Long id,
            @RequestHeader("X-User-Email") String userEmail) {
        return ResponseEntity.ok(answerService.toggleVote(id, userEmail));
    }
    
    @DeleteMapping("/{id}/vote")
    public ResponseEntity<VoteResponse> unvote(
            @PathVariable Long id,
            @RequestHeader("X-User-Email") String userEmail) {
        // Toggle vote works for both adding and removing
        return ResponseEntity.ok(answerService.toggleVote(id, userEmail));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAnswer(
            @PathVariable Long id,
            @RequestHeader(value = "X-User-Role", defaultValue = "USER") String role) {
        
        if (!"ADMIN".equals(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied: Admin only");
        }
        answerService.deleteAnswer(id);
        return ResponseEntity.ok().build();
    }
}
