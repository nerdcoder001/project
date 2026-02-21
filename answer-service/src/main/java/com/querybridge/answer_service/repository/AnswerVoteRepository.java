package com.querybridge.answer_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.querybridge.answer_service.entity.AnswerVote;

public interface AnswerVoteRepository extends JpaRepository<AnswerVote, Long> {
    
    Optional<AnswerVote> findByAnswerIdAndUserEmail(Long answerId, String userEmail);
    
    long countByAnswerId(Long answerId);
    
    @Modifying
    @Transactional
    @Query("DELETE FROM AnswerVote v WHERE v.answerId = :answerId AND v.userEmail = :userEmail")
    void deleteByAnswerIdAndUserEmail(@Param("answerId") Long answerId, @Param("userEmail") String userEmail);

    @Modifying
    @Transactional
    @Query("DELETE FROM AnswerVote v WHERE v.answerId = :answerId")
    void deleteByAnswerId(@Param("answerId") Long answerId);

}
