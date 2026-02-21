package com.querybridge.query_service.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.querybridge.query_service.dto.CreateQueryRequest;
import com.querybridge.query_service.dto.QueryWithCountsDTO;
import com.querybridge.query_service.dto.VoteResponse;
import com.querybridge.query_service.entity.Query;
import com.querybridge.query_service.entity.Vote;
import com.querybridge.query_service.repository.QueryRepository;
import com.querybridge.query_service.repository.VoteRepository;

@Service
public class QueryService {

    private final QueryRepository queryRepository;
    private final VoteRepository voteRepository;
    private final RestTemplate restTemplate;

    public QueryService(QueryRepository queryRepository, VoteRepository voteRepository, RestTemplate restTemplate) {
        this.queryRepository = queryRepository;
        this.voteRepository = voteRepository;
        this.restTemplate = restTemplate;
    }

    public Query createQuery(CreateQueryRequest request) {
    	
//    	String url = "http://auth-service/auth/users/" + request.getName();
//    	restTemplate.getForObject(url, Object.class);
        // Create and save the query directly
        // No auth validation needed since queries are public
        Query query = new Query();
        
        // Ensure name is never null or empty (database constraint)
        String name = request.getName();
        if (name == null || name.trim().isEmpty()) {
            name = "Anonymous";
        }
        query.setName(name);
        query.setTitle(request.getTitle());
        query.setBody(request.getBody());

        return queryRepository.save(query);
    }
    
    public List<QueryWithCountsDTO> getAllQueries(String userEmail) {
        List<Query> queries = queryRepository.findAll();
        List<QueryWithCountsDTO> result = new ArrayList<>();
        
        for (Query query : queries) {
            long voteCount = voteRepository.countByQueryId(query.getId());
            boolean isVoted = false;
            
            if (userEmail != null && !userEmail.isEmpty()) {
                isVoted = voteRepository.findByQueryIdAndUserEmail(query.getId(), userEmail).isPresent();
            }
            
            // Fetch answer count from Answer Service
            long answerCount = 0;
            try {
                String url = "http://answer-service/api/answers/count/" + query.getId();
                Long count = restTemplate.getForObject(url, Long.class);
                answerCount = (count != null) ? count : 0;
            } catch (Exception e) {
                System.err.println("Failed to fetch answer count for query " + query.getId() + ": " + e.getMessage());
                // Continue with answerCount = 0
            }
            
            QueryWithCountsDTO dto = new QueryWithCountsDTO(
                query.getId(),
                query.getName(),
                query.getTitle(),
                query.getBody(),
                query.getCreatedAt(),
                voteCount,
                answerCount,
                isVoted
            );
            result.add(dto);
        }
        
        return result;
    }

    public VoteResponse toggleVote(Long queryId, String userEmail) {
        // Verify query exists
        Query query = queryRepository.findById(queryId)
                .orElseThrow(() -> new RuntimeException("Query not found with id: " + queryId));
        
        // Check if user has already voted
        var existingVote = voteRepository.findByQueryIdAndUserEmail(queryId, userEmail);
        
        if (existingVote.isPresent()) {
            // Remove vote
            voteRepository.deleteByQueryIdAndUserEmail(queryId, userEmail);
            long newCount = voteRepository.countByQueryId(queryId);
            return new VoteResponse(newCount, false);
        } else {
            // Add vote
            Vote vote = new Vote();
            vote.setQueryId(queryId);
            vote.setUserEmail(userEmail);
            voteRepository.save(vote);
            long newCount = voteRepository.countByQueryId(queryId);
            return new VoteResponse(newCount, true);
        }
    }

    public Query getQueryByUserName(String username) {
        return queryRepository.findByName(username)
                .orElseThrow(() -> new RuntimeException("Query not found with id: " + username));
    }
    
    @Transactional
    public void deleteQuery(Long id) {
        // Verify query exists
        Query query = queryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Query not found with id: " + id));
        
        // Delete all votes associated with this query
        voteRepository.deleteByQueryId(id);
        
        // Delete the query
        queryRepository.delete(query);
    }
}
