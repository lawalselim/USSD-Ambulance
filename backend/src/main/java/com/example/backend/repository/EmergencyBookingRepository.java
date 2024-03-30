package com.example.backend.repository;

import com.example.backend.entities.EmergencyBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmergencyBookingRepository extends JpaRepository<EmergencyBooking, Long> {


}
