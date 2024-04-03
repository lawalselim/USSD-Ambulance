package com.example.backend.repository;

import com.example.backend.entities.EmergencyBooking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository  extends JpaRepository<EmergencyBooking, Long> {
}
