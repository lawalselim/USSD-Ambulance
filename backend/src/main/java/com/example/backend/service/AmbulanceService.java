package com.example.backend.service;

import com.example.backend.entities.Address;
import com.example.backend.entities.Ambulance;
import com.example.backend.repository.AmbulanceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AmbulanceService {
    @Autowired
    private AmbulanceRepository ambulanceRepository;
    @Transactional
    public Ambulance assignAmbulanceToAddress(Address address) {
        // Implementation logic
        // Find the first available ambulance (not currently assigned)
        Optional<Ambulance> availableAmbulance = ambulanceRepository.findByIsAssigned(false).stream().findFirst();
        if (availableAmbulance.isPresent()) {
            Ambulance ambulance = availableAmbulance.get();
            ambulance.setIsAssigned(true); // Mark as assigned
            ambulanceRepository.save(ambulance);
            return ambulance;
        } else {
            // Handle the case where no ambulances are available
            // This might involve creating a new ambulance or implementing a waitlist
            throw new RuntimeException("No available ambulances");
        }
    }
}
