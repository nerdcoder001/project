package com.cdacQuerybridge.admin_service.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cdacQuerybridge.admin_service.dto.AnswerDto;

@FeignClient(name = "ANSWER-SERVICE")
public interface AnswerClient {

    @GetMapping("/answers/query/{questionId}")
    List<AnswerDto> getAnswers(@PathVariable Long questionId);

    @DeleteMapping("/answers/{answerId}")
    void deleteAnswer(@PathVariable Long answerId);
}
