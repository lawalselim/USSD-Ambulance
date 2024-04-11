package com.example.backend.service;

import com.example.backend.dto.EmergencyBookingCreateDto;
import com.example.backend.dto.EmergencyBookingResponseDto;
import com.example.backend.entities.*;
import com.example.backend.exceptions.UserNotFoundException;
import com.example.backend.exceptions.GeocodingException;
import com.example.backend.repository.AddressRepository;
import com.example.backend.repository.EmergencyTypeRepository;
import com.example.backend.repository.EmergencyBookingRepository;
import com.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AmbulanceService ambulanceService;

    private final EmergencyTypeRepository emergencyTypeRepository;

    @Autowired
    public EmergencyBookingService(EmergencyTypeRepository emergencyTypeRepository) {
        this.emergencyTypeRepository = emergencyTypeRepository;
    }


    @Transactional
    public EmergencyBookingResponseDto bookAmbulance(EmergencyBookingCreateDto createDto) throws UserNotFoundException, GeocodingException {
        // Determine if email or phone number is provided and use it to find the user
        Optional<User> userOptional = Optional.empty();

        if (createDto.getEmail() != null && !createDto.getEmail().isEmpty()) {
            userOptional = userRepository.findByEmail(createDto.getEmail());
        } else if (createDto.getPhoneNumber() != null && !createDto.getPhoneNumber().isEmpty()) {
            userOptional = userRepository.findByPhoneNumber(createDto.getPhoneNumber());
        }

        User user = userOptional.orElseThrow(() ->
                new UserNotFoundException("User not found with provided email/phone number"));

        // Geocoding logic...

        // In EmergencyBookingService
        double[] coordinates;
        if (createDto.getAddress() == null || createDto.getAddress().isEmpty()) {
            throw new GeocodingException("Address is empty or invalid.", null);
        }
        try {
            coordinates = googleMapsService.getCoordinates(createDto.getAddress());
            logger.info("Geocoding successful for address: {}", createDto.getAddress());
        } catch (Exception e) {
            logger.error("Geocoding failed for address: {}", createDto.getAddress(), e);
            throw new GeocodingException("Failed to geocode address: " + createDto.getAddress(), e);
        }


        // Check if the address exists, if not, create a new Address
        Address addressEntity = addressRepository.findByLocation(createDto.getAddress())
                .orElseGet(() -> {
                    Address newAddress = new Address();
                    newAddress.setLocation(createDto.getAddress());
                    // Assuming default value for assignedAmbulances is set within the Address entity
                    return addressRepository.save(newAddress);
                });
        // Assign an ambulance to the address
        Ambulance assignedAmbulance = ambulanceService.assignAmbulanceToAddress(addressEntity);

        EmergencyBooking booking = new EmergencyBooking();
        // Set user, emergency type, address details, and the assigned ambulance to the booking
        booking.setUser(user);
        booking.setEmergencyTypeEnum(createDto.getEmergencyTypeEnum());
        booking.setAddress(createDto.getAddress());
        booking.setLatitude(coordinates[0]);
        booking.setLongitude(coordinates[1]);
        booking.setAmbulance(assignedAmbulance);
        //Assuming a status field exists to track the booking status
        booking.setStatus("ASSIGNED");

        booking = bookingRepository.save(booking);
        logger.info("Emergency booking saved successfully with ID: {}", booking.getId());

        // Construct success message including geolocation
        String successMessage = String.format(
                "Your ambulance has been booked successfully. Location: %s. Latitude: %f, Longitude: %f",
                createDto.getAddress(), coordinates[0], coordinates[1]);

        return new EmergencyBookingResponseDto(
                booking.getEmergencyTypeEnum(), user, booking.getAddress(),
                booking.getLatitude(), booking.getLongitude(), successMessage
        );
    }

    public List<EmergencyType> getAllEmergencyTypes() {
        return emergencyTypeRepository.findAll();
    }
}