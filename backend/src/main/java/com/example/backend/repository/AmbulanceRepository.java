package com.example.backend.repository;

import com.example.backend.entities.Ambulance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AmbulanceRepository extends JpaRepository<Ambulance, Long> {
    List<Ambulance> findByIsAssigned(boolean isAssigned);
}
