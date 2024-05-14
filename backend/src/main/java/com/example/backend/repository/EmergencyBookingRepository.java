package com.example.backend.repository;

import com.example.backend.entities.EmergencyBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EmergencyBookingRepository extends JpaRepository<EmergencyBooking, Long> {

    @Query("SELECT e FROM EmergencyBooking e ORDER BY e.createdAt DESC")
    List<EmergencyBooking> findTop5NewBookings();

    @Query("SELECT e.address, COUNT(e),e.longitude, e.latitude FROM EmergencyBooking e GROUP BY e.address, e.longitude,e.latitude ORDER BY COUNT(e) DESC")
    List<Object[]> findTopLocations();

    @Query("SELECT e.emergencyTypeEnum, COUNT(e) FROM EmergencyBooking e GROUP BY e.emergencyTypeEnum ORDER BY COUNT(e) DESC")
    List<Object[]> findEmergencyTypeFrequencies();

    // Query latest request on the Map
    @Query("SELECT e FROM EmergencyBooking e ORDER BY e.createdAt DESC")
    List<EmergencyBooking> findLatestEmergencyRequests();

    //this is to  find address with the most emergency request
    @Query("SELECT e.address, COUNT(e) AS requestCount FROM EmergencyBooking e GROUP BY e.address ORDER BY requestCount DESC")
    List<Object[]> findTopEmergencyAddresses();

    //This fetches address with most emergencybooking by Type {HEART_ATTACK, STROKE, FAINT}
    @Query("SELECT e.emergencyTypeEnum, e.address, COUNT(e) AS requestCount FROM EmergencyBooking e GROUP BY e.emergencyTypeEnum, e.address ORDER BY e.emergencyTypeEnum, requestCount DESC")
    List<Object[]> findTopEmergencyAddressesByType();
}
