package com.example.backend.service;

import com.example.backend.dto.EmergencyBookingCreateDto;
import com.example.backend.dto.EmergencyBookingResponseDto;
import com.example.backend.entities.EmergencyBooking;
import com.example.backend.entities.User;
import com.example.backend.exceptions.UserNotFoundException;
import com.example.backend.exceptions.GeocodingException;
import com.example.backend.repository.EmergencyBookingRepository;
import com.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class EmergencyBookingService {
    private static final Logger logger = LoggerFactory.getLogger(EmergencyBookingService.class);
    @Autowired
    private GoogleMapsService googleMapsService;

    @Autowired
    private EmergencyBookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public EmergencyBookingResponseDto bookAmbulance(EmergencyBookingCreateDto createDto) throws UserNotFoundException, GeocodingException {
        Optional<User> userOptional = Optional.empty();

        // Determine if email or phone number is provided and use it to find the user
        if (createDto.getEmail() != null && !createDto.getEmail().isEmpty()) {
            userOptional = userRepository.findByEmail(createDto.getEmail());
        } else if (createDto.getPhoneNumber() != null && !createDto.getPhoneNumber().isEmpty()) {
            userOptional = userRepository.findByPhoneNumber(createDto.getPhoneNumber());
        }

        User user = userOptional.orElseThrow(() ->
                new UserNotFoundException("User not found with provided email/phone number"));

        double[] coordinates;
        try {
            coordinates = googleMapsService.getCoordinates(createDto.getAddress());
            logger.info("Geocoding successful for address: {}", createDto.getAddress());
        } catch (Exception e) {
            logger.error("Geocoding failed for address: {}", createDto.getAddress(), e);
            throw new GeocodingException("Failed to geocode address: " + createDto.getAddress(), e);
        }



        /*User user = userRepository.findByEmail(createDto.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + createDto.getEmail()));

        double[] coordinates;
        try {
            coordinates = googleMapsService.getCoordinates(createDto.getAddress());
        } catch (Exception e) { // Assume this catches exceptions specific to geocoding failures
            throw new GeocodingException("Failed to geocode address: " + createDto.getAddress(), e);
        }
        /
         */

        EmergencyBooking booking = new EmergencyBooking();
        booking.setUser(user);
        booking.setEmergencyTypeEnum(createDto.getEmergencyTypeEnum()); // Assuming this is the correct method name
        booking.setAddress(createDto.getAddress());
        booking.setLatitude(coordinates[0]);
        booking.setLongitude(coordinates[1]);


        booking = bookingRepository.save(booking); // Persist the booking
        logger.info("Emergency booking saved successfully with ID: {}", booking.getId()); // log response message in console

        String successMessage = String.format("Your ambulance has been booked successfully, and your location has been identified. Latitude: %f, Longitude: %f",
                coordinates[0], coordinates[1]);
        // The EmergencyBookingResponseDto's constructor is expected to match these parameters
        return new EmergencyBookingResponseDto(booking.getEmergencyTypeEnum(), user, booking.getAddress(), booking.getLatitude(), booking.getLongitude(),successMessage);
    }
}
