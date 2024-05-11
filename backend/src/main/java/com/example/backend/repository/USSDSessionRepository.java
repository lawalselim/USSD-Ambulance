package com.example.backend.repository;

import com.example.backend.entities.USSDSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface USSDSessionRepository extends JpaRepository<USSDSession, String> {
}
