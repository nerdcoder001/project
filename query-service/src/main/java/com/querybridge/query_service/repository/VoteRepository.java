package com.querybridge.query_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.querybridge.query_service.entity.Vote;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    
    Optional<Vote> findByQueryIdAndUserEmail(Long queryId, String userEmail);
    
    long countByQueryId(Long queryId);
    
    @Modifying
    @Transactional
    @Query("DELETE FROM Vote v WHERE v.queryId = :queryId AND v.userEmail = :userEmail")
    void deleteByQueryIdAndUserEmail(@Param("queryId") Long queryId, @Param("userEmail") String userEmail);
    
    @Modifying
    @Transactional
    @Query("DELETE FROM Vote v WHERE v.queryId = :queryId")
    void deleteByQueryId(@Param("queryId") Long queryId);
}
