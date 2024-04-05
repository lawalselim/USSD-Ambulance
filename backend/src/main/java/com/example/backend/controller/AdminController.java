package com.example.backend.controller;

import com.example.backend.dto.RegisterUserDto;
import com.example.backend.entities.EmergencyBooking;
import com.example.backend.entities.User;
import com.example.backend.service.AdminService;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/admins/")
@PreAuthorize("hasRole('SUPER_ADMIN')")
@RestController
public class AdminController {
    @Autowired
    private AdminService adminService;

    private final UserService userService;

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

    @GetMapping("/bookings/{id}")
    public EmergencyBooking getBookingById(@PathVariable Long id) {
        return adminService.getBookingById(id);
    }


    @GetMapping("/dashboard")
    public Map<String, Long> getDashboardStats() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("totalUsers", adminService.getTotalUsers());
        stats.put("totalBookings", adminService.getTotalBookings());
        //stats.put("totalFailedBookings", adminService.getTotalFailedBookings());
        return stats;
    }

}


//    @PreAuthorize("isAuthenticated()" )
//    @GetMapping("/bookings")
//    public String getAllBookings() {
//        //return adminService.getAllBookings();
//        return "Hello";
//    }