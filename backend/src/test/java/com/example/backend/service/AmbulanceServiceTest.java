package com.example.backend.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.backend.entities.Address;
import com.example.backend.entities.Ambulance;
import com.example.backend.repository.AmbulanceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AmbulanceServiceTest {

    @Mock
    private AmbulanceRepository ambulanceRepository;

    @InjectMocks
    private AmbulanceService ambulanceService;

    @Test
    public void testAssignAmbulanceToAddressSuccess() {
        // Setup
        Ambulance ambulance = new Ambulance();
        ambulance.setIsAssigned(false);
        when(ambulanceRepository.findByIsAssigned(false)).thenReturn(Collections.singletonList(ambulance));

        // Execution
        Ambulance assignedAmbulance = ambulanceService.assignAmbulanceToAddress(new Address());

        // Verification
        assertTrue(assignedAmbulance.getIsAssigned());
        verify(ambulanceRepository, times(1)).save(assignedAmbulance);
    }

    @Test
    public void testAssignAmbulanceToAddressNoneAvailable() {
        // Setup
        when(ambulanceRepository.findByIsAssigned(false)).thenReturn(Collections.emptyList());

        // Execution & Verification
        Exception exception = assertThrows(RuntimeException.class, () -> {
            ambulanceService.assignAmbulanceToAddress(new Address());
        });
        assertEquals("No available ambulances", exception.getMessage());
    }
}
