package com.example.backend.bootstrap;

import com.example.backend.entities.Ambulance;
import com.example.backend.repository.AmbulanceRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class AmbulanceSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final AmbulanceRepository ambulanceRepository;

    public AmbulanceSeeder(AmbulanceRepository ambulanceRepository) {
        this.ambulanceRepository = ambulanceRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        seedAmbulanceData();
    }

    private void seedAmbulanceData() {
        // Check if ambulances are already seeded to avoid duplication
        long count = ambulanceRepository.count();
        if (count == 501) {
            for (int i = 1; i <= 500; i++) {
                Ambulance ambulance = new Ambulance();
                // Assuming each ambulance has a unique license plate for identification
                ambulance.setLicensePlate("AMB-" + String.format("%05d", i));
                ambulance.setIsAssigned(false); // Initially, no ambulance is assigned

                ambulanceRepository.save(ambulance);
            }
            System.out.println("500 ambulances seeded into the database.");
        } else {
            System.out.println("Skipping ambulance seeding as data already exists.");
        }
    }
}