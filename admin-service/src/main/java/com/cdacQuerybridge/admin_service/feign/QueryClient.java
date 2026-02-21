package com.cdacQuerybridge.admin_service.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cdacQuerybridge.admin_service.dto.QueryDto;

@FeignClient(name = "QUERY-SERVICE")
public interface QueryClient {

    @GetMapping("/queries")
    List<QueryDto> getAllQueries();

    @DeleteMapping("/queries/{id}")
    void deleteQuery(@PathVariable Long id);
}
