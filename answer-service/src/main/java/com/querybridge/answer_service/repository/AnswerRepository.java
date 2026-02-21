package com.querybridge.answer_service.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.querybridge.answer_service.entity.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByQueryId(Long queryId);
    Optional<Answer> findByUsername(String username);
}
