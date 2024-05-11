package com.example.backend.controller;

import com.example.backend.dto.RegisterUserDto;
import com.example.backend.entities.Ambulance;
import com.example.backend.entities.EmergencyBooking;
import com.example.backend.entities.User;
import com.example.backend.repository.EmergencyBookingRepository;
import com.example.backend.service.AdminService;
import com.example.backend.service.EmergencyBookingService;
import com.example.backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@RequestMapping("/admins/")
//@PreAuthorize("hasRole('SUPER_ADMIN')")
@RestController
@CrossOrigin(origins = "http://localhost:3000") // Enable CORS for all endpoints in this controller
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(EmergencyBookingService.class);
    @Autowired
    private AdminService adminService;

    @Autowired
    private EmergencyBookingRepository emergencyBookingRepository;

    private final UserService userService;

    private final CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<>();


    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    //@PreAuthorize("isAuthenticated()" )
    public ResponseEntity<User> createAdministrator(@RequestBody RegisterUserDto registerUserDto) {
        User createdAdmin = userService.createAdministrator(registerUserDto);

        return ResponseEntity.ok(createdAdmin);
    }


    @GetMapping("/users")
    public List<User> getAllUsers() {
        return adminService.getAllUsers();
    }
//    //@PreAuthorize("isAuthenticated()" )
//    @GetMapping("/bookings")
//    public String getAllBookings() {
//        //return adminService.getAllBookings();
//        return "Hello";
//    }

    @GetMapping("/bookings")
    public List<EmergencyBooking> getAllBookings() {
        return adminService.getAllBookings();

    }

    @GetMapping("/ambulances")
    public List<Ambulance> getAllAmbulance() {
        return adminService.getAllAmbulance();
    }

    @GetMapping("/booking/{id}")
    public EmergencyBooking getBookingById(@PathVariable Long id) {
        return adminService.getBookingById(id);
    }


    @GetMapping("/dashboard")
    public Map<String, Long> getDashboardStats() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("totalUsers", adminService.getTotalUsers());
        stats.put("totalBookings", adminService.getTotalBookings());
        stats.put("totalAmbulances", adminService.getTotalAmbulances());
        //stats.put("totalFailedBookings", adminService.getTotalFailedBookings());
        return stats;
    }

    @GetMapping("/top-locations")
    public List<Object[]> getTopEmergencyLocations() {
        return emergencyBookingRepository.findTopLocations();
    }

    @GetMapping("/emergency-type-frequencies")
    public List<Object[]> getEmergencyTypeFrequencies() {
        return emergencyBookingRepository.findEmergencyTypeFrequencies();
    }

    // Endpoint to query new request

    @GetMapping("/emergency/requests/stream")
    public SseEmitter streamEmergencyRequests() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        emitters.add(emitter);

        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));

        // Simulate data update - ideally, you should replace this with real data updates
        new Thread(() -> {
            try {
                while (true) {
                    emitter.send(emergencyBookingRepository.findLatestEmergencyRequests());
                    Thread.sleep(5000);  // update every 5 seconds
                }
            } catch (IOException | InterruptedException e) {
                emitter.completeWithError(e);
            }
        }).start();

        return emitter;
    }

    //The section gets addresses with the most emergenccy booking
    @GetMapping("/top-addresses")
    public Map<String, Object> getTopEmergencyAddresses() {
        List<Object[]> data = emergencyBookingRepository.findTopEmergencyAddresses();
        List<String> addresses = new ArrayList<>();
        List<Long> counts = new ArrayList<>();

        for (Object[] entry : data) {
            addresses.add((String) entry[0]);
            counts.add((Long) entry[1]);
        }

        Map<String, Object> chartData = new HashMap<>();
        chartData.put("labels", addresses);
        chartData.put("data", counts);

        return chartData;
    }

    @GetMapping("/top-addresses-by-type")
    public ResponseEntity<List<Object[]>> getTopEmergencyAddressesByType() {
        try {
            List<Object[]> results = emergencyBookingRepository.findTopEmergencyAddressesByType();
            if (results.isEmpty()) {
                logger.info("No data found for the top emergency addresses by type.");
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            logger.error("Failed to fetch top emergency addresses by type", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}

