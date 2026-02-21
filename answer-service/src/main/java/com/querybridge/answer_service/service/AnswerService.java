package com.querybridge.answer_service.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.querybridge.answer_service.dto.AnswerWithVotesDTO;
import com.querybridge.answer_service.dto.CreateAnswerRequest;
import com.querybridge.answer_service.dto.VoteResponse;
import com.querybridge.answer_service.entity.Answer;
import com.querybridge.answer_service.entity.AnswerVote;
import com.querybridge.answer_service.repository.AnswerRepository;
import com.querybridge.answer_service.repository.AnswerVoteRepository;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final AnswerVoteRepository answerVoteRepository;
    private final RestTemplate restTemplate;

    public AnswerService(AnswerRepository answerRepository, AnswerVoteRepository answerVoteRepository, RestTemplate restTemplate) {
        this.answerRepository = answerRepository;
        this.answerVoteRepository = answerVoteRepository;
        this.restTemplate = restTemplate;
    }

    public Answer createAnswer(CreateAnswerRequest request) {
        // Create answer directly - no external service validation
        // Services are public, so no auth check needed
        
        Answer answer = new Answer();
        
        // Ensure username is never null or empty (database constraint)
        String username = request.getUsername();
        if (username == null || username.trim().isEmpty()) {
            username = "Anonymous";
        }
        answer.setUsername(username);
        answer.setQueryId(request.getQueryId());
        answer.setContent(request.getContent());
        answer.setComment(request.getComment());

        return answerRepository.save(answer);
    }
    
    public List<AnswerWithVotesDTO> getAnswersWithVotes(Long queryId, String userEmail) {
        List<Answer> answers = answerRepository.findByQueryId(queryId);
        List<AnswerWithVotesDTO> result = new ArrayList<>();
        
        for (Answer answer : answers) {
            long voteCount = answerVoteRepository.countByAnswerId(answer.getId());
            boolean isVoted = false;
            
            if (userEmail != null && !userEmail.isEmpty()) {
                isVoted = answerVoteRepository.findByAnswerIdAndUserEmail(answer.getId(), userEmail).isPresent();
            }
            
            AnswerWithVotesDTO dto = new AnswerWithVotesDTO(
                answer.getId(),
                answer.getQueryId(),
                answer.getUsername(),
                answer.getContent(),
                answer.getComment(),
                answer.getCreatedAt(),
                voteCount,
                isVoted
            );
            result.add(dto);
        }
        
        return result;
    }
    
    public VoteResponse toggleVote(Long answerId, String userEmail) {
        // Verify answer exists
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new RuntimeException("Answer not found with id: " + answerId));
        
        // Check if user has already voted
        var existingVote = answerVoteRepository.findByAnswerIdAndUserEmail(answerId, userEmail);
        
        if (existingVote.isPresent()) {
            // Remove vote
            answerVoteRepository.deleteByAnswerIdAndUserEmail(answerId, userEmail);
            long newCount = answerVoteRepository.countByAnswerId(answerId);
            return new VoteResponse(newCount, false);
        } else {
            // Add vote
            AnswerVote vote = new AnswerVote();
            vote.setAnswerId(answerId);
            vote.setUserEmail(userEmail);
            answerVoteRepository.save(vote);
            long newCount = answerVoteRepository.countByAnswerId(answerId);
            return new VoteResponse(newCount, true);
        }
    }
    
    public void deleteAnswer(Long id) {
        // Verify answer exists
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Answer not found with id: " + id));
        
        // Delete all votes associated with this answer
        answerVoteRepository.deleteById(id);
        
        // Delete the answer
        answerRepository.delete(answer);
    }
}
