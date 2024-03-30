package com.example.backend.dto;

import com.example.backend.entities.EmergencyTypeEnum;
import com.example.backend.entities.User;

public class EmergencyBookingResponseDto {
    private EmergencyTypeEnum emergencyTypeEnum;
    private String address;
    private double latitude;
    private double longitude;
    private User user;

    // Constructor, Getters, and Setters
    public EmergencyBookingResponseDto (EmergencyTypeEnum emergencyTypeEnum,User user, String address, double latitude, double longitude) {
        this.emergencyTypeEnum = emergencyTypeEnum;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.user = user;
    }

    public EmergencyTypeEnum getEmergencyTypeEnum() {
        return emergencyTypeEnum;
    }

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
