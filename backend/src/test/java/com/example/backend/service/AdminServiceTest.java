package com.example.backend.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.backend.entities.Ambulance;
import com.example.backend.entities.EmergencyBooking;
import com.example.backend.entities.User;
import com.example.backend.repository.AmbulanceRepository;
import com.example.backend.repository.EmergencyBookingRepository;
import com.example.backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmergencyBookingRepository emergencyBookingRepository;

    @Mock
    private AmbulanceRepository ambulanceRepository;

    @InjectMocks
    private AdminService adminService;

    @Test
    public void testGetAllUsers() {
        // Setup
        User user1 = new User();  // Assume user setup
        User user2 = new User();  // Assume user setup
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        // Execution
        List<User> users = adminService.getAllUsers();

        // Verification
        assertNotNull(users);
        assertEquals(2, users.size());
    }

    @Test
    public void testGetAllBookings() {
        // Setup
        EmergencyBooking booking1 = new EmergencyBooking();  // Assume booking setup
        EmergencyBooking booking2 = new EmergencyBooking();  // Assume booking setup
        when(emergencyBookingRepository.findAll()).thenReturn(Arrays.asList(booking1, booking2));

        // Execution
        List<EmergencyBooking> bookings = adminService.getAllBookings();

        // Verification
        assertNotNull(bookings);
        assertEquals(2, bookings.size());
    }

    @Test
    public void testGetAllAmbulance() {
        // Setup
        Ambulance ambulance1 = new Ambulance();  // Assume ambulance setup
        Ambulance ambulance2 = new Ambulance();  // Assume ambulance setup
        when(ambulanceRepository.findAll()).thenReturn(Arrays.asList(ambulance1, ambulance2));

        // Execution
        List<Ambulance> ambulances = adminService.getAllAmbulance();

        // Verification
        assertNotNull(ambulances);
        assertEquals(2, ambulances.size());
    }

    @Test
    public void testGetBookingByIdNotFound() {
        // Setup
        Long bookingId = 1L;
        when(emergencyBookingRepository.findById(bookingId)).thenReturn(Optional.empty());

        // Execution & Verification
        Exception exception = assertThrows(RuntimeException.class, () -> {
            adminService.getBookingById(bookingId);
        });
        assertEquals("Booking not found", exception.getMessage());
    }

    @Test
    public void testGetTotalUsers() {
        // Setup
        when(userRepository.count()).thenReturn(10L);

        // Execution
        long totalUsers = adminService.getTotalUsers();

        // Verification
        assertEquals(10, totalUsers);
    }

    @Test
    public void testGetTotalBookings() {
        // Setup
        when(emergencyBookingRepository.count()).thenReturn(5L);

        // Execution
        long totalBookings = adminService.getTotalBookings();

        // Verification
        assertEquals(5, totalBookings);
    }

    @Test
    public void testGetTotalAmbulances() {
        // Setup
        when(ambulanceRepository.count()).thenReturn(3L);

        // Execution
        long totalAmbulances = adminService.getTotalAmbulances();

        // Verification
        assertEquals(3, totalAmbulances);
    }
}
