package com.example.backend.dto;

import com.example.backend.entities.EmergencyTypeEnum;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Email;

public class EmergencyBookingCreateDto {

    private EmergencyTypeEnum emergencyTypeEnum;
    private String address;
    private String phoneNumber;
    @NotNull
    @Email
    private String email;

    // Getters and Setters

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public EmergencyTypeEnum getEmergencyTypeEnum() {
        return emergencyTypeEnum;
    }

    public void setEmergencyType(EmergencyTypeEnum emergencyTypeEnum) {
        this.emergencyTypeEnum = emergencyTypeEnum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
