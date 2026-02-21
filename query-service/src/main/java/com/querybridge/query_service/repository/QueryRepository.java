package com.querybridge.query_service.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.querybridge.query_service.entity.Query;

public interface QueryRepository extends JpaRepository<Query, Long> {
	Optional<Query> findByName(String name);
}
