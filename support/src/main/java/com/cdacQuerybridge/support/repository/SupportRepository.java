package com.cdacQuerybridge.support.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.cdacQuerybridge.support.entity.SupportRequest;

public interface SupportRepository extends JpaRepository<SupportRequest, Long> {
}
