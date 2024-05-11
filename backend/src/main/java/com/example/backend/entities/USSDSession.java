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
    private String name; // Add name field
    private String email; // Add email field
    private String password;

    // Constructor
    public USSDSession(String sessionId, String phoneNumber, int step) {
        // Set fields based on arguments
        this.sessionId = sessionId;
        this.phoneNumber = phoneNumber;
        this.step = step;
    }

    public USSDSession() {

    }

    public USSDSession(String sessionId) {
        this.sessionId = sessionId;
        // Set default values if necessary
        this.phoneNumber = "";  // Default or null, depending on your logic
        this.step = 1;          // Assuming '1' is the initial step if not provided
    }

    // Adding getters
    public String getSessionId() { return sessionId; }
    public String getPhoneNumber() { return phoneNumber; }
    public EmergencyTypeEnum getEmergencyType() { return emergencyType; }
    public String getLocation() { return location; }
    public Integer getStep() { return step; }

    public String getName() { return name; }
    public String getEmail() { return email; }

    public String getPassword(){
        return password;
    }


    // and setters

    public void setSessionId(String sessionId) { this.sessionId = sessionId; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setEmergencyType(EmergencyTypeEnum emergencyType) {
        this.emergencyType = emergencyType;
    }
    public void setLocation(String location) { this.location = location; }
    public void setStep(Integer step) { this.step = step; }

    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }

    public void setPassword(String password){this.password = password;}



}
