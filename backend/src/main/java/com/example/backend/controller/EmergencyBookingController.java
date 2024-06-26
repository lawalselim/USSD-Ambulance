package com.example.backend.controller;

import com.example.backend.dto.EmergencyBookingCreateDto;
import com.example.backend.dto.EmergencyBookingResponseDto;
import com.example.backend.service.EmergencyBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@PreAuthorize("hasRole('USER')")
@RequestMapping("/api/ambulance/")
public class EmergencyBookingController {

    @Autowired
    private EmergencyBookingService bookingService;


    @PostMapping("/book")
    public EmergencyBookingResponseDto bookAmbulance(@RequestBody EmergencyBookingCreateDto createDto) throws Exception {
        return bookingService.bookAmbulance(createDto);
    }

}
