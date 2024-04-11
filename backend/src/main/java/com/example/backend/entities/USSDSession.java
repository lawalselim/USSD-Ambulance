package com.example.backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class USSDSession {
    @Id
    private String sessionId;
    private String phoneNumber;
    private EmergencyTypeEnum emergencyType;
    private String location;
    private Integer step;

    // Constructor
    public USSDSession(String sessionId, String phoneNumber, int step) {
        // Set fields based on arguments
        this.sessionId = sessionId;
        this.phoneNumber = phoneNumber;
        this.step = step;
    }

    public USSDSession() {

    }

    // Adding getters
    public String getSessionId() { return sessionId; }
    public String getPhoneNumber() { return phoneNumber; }
    public EmergencyTypeEnum getEmergencyType() { return emergencyType; }
    public String getLocation() { return location; }
    public Integer getStep() { return step; }


    // and setters

    public void setSessionId(String sessionId) { this.sessionId = sessionId; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setEmergencyType(EmergencyTypeEnum emergencyType) {
        this.emergencyType = emergencyType;
    }
    public void setLocation(String location) { this.location = location; }
    public void setStep(Integer step) { this.step = step; }


}
