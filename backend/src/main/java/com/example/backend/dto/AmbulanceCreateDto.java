package com.example.backend.dto;

public class AmbulanceCreateDto {

    private String licensePlate;
    private boolean isAssigned;

    // Constructors
    public AmbulanceCreateDto() {}

    public AmbulanceCreateDto(String licensePlate, boolean isAssigned) {
        this.licensePlate = licensePlate;
        this.isAssigned = isAssigned;
    }

    // Getters and Setters
    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public boolean getIsAssigned() {
        return isAssigned;
    }

    public void setIsAssigned(boolean isAssigned) {
        this.isAssigned = isAssigned;
    }
}
