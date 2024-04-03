package com.example.backend.service;

import com.example.backend.entities.EmergencyBooking;
import com.example.backend.entities.User;
import com.example.backend.repository.EmergencyBookingRepository;
import com.example.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmergencyBookingRepository emergencyBookingRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<EmergencyBooking> getAllBookings() {
        return emergencyBookingRepository.findAll();
    }

    public EmergencyBooking getBookingById(Long id) {
        // Handle case where booking is not found
        return emergencyBookingRepository.findById(id).orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    public long getTotalUsers() {
        return userRepository.count();
    }

    public long getTotalBookings() {
        return emergencyBookingRepository.count();
    }

    /*
    public long getTotalFailedBookings() {
        // Assuming "FAILED" is a valid status. Adjust accordingly.
        return bookingRepository.countByStatus("FAILED");
    }

     */

}
