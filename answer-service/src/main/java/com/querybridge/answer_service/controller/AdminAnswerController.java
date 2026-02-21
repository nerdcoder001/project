package com.querybridge.answer_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.querybridge.answer_service.service.AnswerService;

/**
 * Separate controller for admin-service compatibility
 * Handles requests at /answers/{id} path (without /api prefix)
 */
@RestController
@RequestMapping("/answers")
public class AdminAnswerController {

    private final AnswerService answerService;

    public AdminAnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @GetMapping("/query/{queryId}")
    public ResponseEntity<java.util.List<com.querybridge.answer_service.dto.AnswerWithVotesDTO>> getAnswersByQueryId(@PathVariable Long queryId) {
        return ResponseEntity.ok(answerService.getAnswersWithVotes(queryId, null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnswer(@PathVariable Long id) {
        answerService.deleteAnswer(id);
        return ResponseEntity.ok().build();
    }
}
