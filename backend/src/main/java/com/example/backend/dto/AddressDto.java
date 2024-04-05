package com.example.backend.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AddressDto {

    private Long id; // Optional: used for updates

    @NotBlank(message = "Location is required")
    private String location;

    @NotNull(message = "Assigned ambulances cannot be null")
    @Min(value = 0, message = "Assigned ambulances must be non-negative")
    private Integer assignedAmbulances; // This could be optional depending on whether it is specified

    // Constructors
    public AddressDto() {}

    public AddressDto(String location, Integer assignedAmbulances) {
        this.location = location;
        this.assignedAmbulances = assignedAmbulances;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getAssignedAmbulances() {
        return assignedAmbulances;
    }

    public void setAssignedAmbulances(Integer assignedAmbulances) {
        this.assignedAmbulances = assignedAmbulances;
    }
}
