package com.example.backend.dto;

import com.example.backend.entities.EmergencyTypeEnum;
import com.example.backend.entities.User;

public class EmergencyBookingResponseDto {
    private EmergencyTypeEnum emergencyTypeEnum;
    private String address;
    private double latitude;
    private double longitude;
    private User user;
    private String message;

    // Constructor,
    public EmergencyBookingResponseDto (EmergencyTypeEnum emergencyTypeEnum,User user, String address, double latitude, double longitude, String message) {
        this.emergencyTypeEnum = emergencyTypeEnum;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.user = user;
        this.message = message;
    }

    // Getters, and Setters
    public EmergencyTypeEnum getEmergencyTypeEnum() {
        return emergencyTypeEnum;
    }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public User getUser(){
        return user;
    }
    public String getAddress() {
        return address;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

}
