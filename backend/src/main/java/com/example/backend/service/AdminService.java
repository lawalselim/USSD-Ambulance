package com.example.backend.service;

import com.example.backend.entities.Address;
import com.example.backend.entities.EmergencyBooking;
import com.example.backend.entities.User;
import com.example.backend.repository.AddressRepository;
import com.example.backend.dto.AddressDto;
import com.example.backend.repository.AmbulanceRepository;
import com.example.backend.repository.EmergencyBookingRepository;
import com.example.backend.entities.Ambulance;
import com.example.backend.dto.AmbulanceCreateDto;
import com.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class AdminService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AmbulanceRepository ambulanceRepository;

    @Autowired
    private EmergencyBookingRepository emergencyBookingRepository;

    @Autowired
    private AddressRepository addressRepository;

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

    //Create an ambulance
    public Ambulance createAmbulance(AmbulanceCreateDto ambulanceCreateDto) {
        Ambulance ambulance = new Ambulance();
        ambulance.setLicensePlate(ambulanceCreateDto.getLicensePlate());
        ambulance.setIsAssigned(ambulanceCreateDto.getIsAssigned());
        return ambulanceRepository.save(ambulance);
    }

    //create an address by admin

    @Transactional
    public Address createAddress(AddressDto addressDto) {
        Address address = new Address();
        address.setLocation(addressDto.getLocation());
        // Optionally initialize assignedAmbulances with 0 if not provided
        address.setAssignedAmbulances(addressDto.getAssignedAmbulances() != null ? addressDto.getAssignedAmbulances() : 0);
        return addressRepository.save(address);
    }

}
