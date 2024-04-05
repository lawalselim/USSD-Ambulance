package com.example.backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Ambulance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String licensePlate;
    private boolean isAssigned;

    // Constructors, getters, and setters

    // Default Constructor
    public Ambulance() {
    }

    // Parameterized Constructor for convenience
    public Ambulance(String licensePlate, boolean isAssigned) {
        this.licensePlate = licensePlate;
        this.isAssigned = isAssigned;
    }

    // Getter and Setter for id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getter and Setter for licensePlate
    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    // Getter and Setter for isAssigned
    public boolean getIsAssigned() {
        return isAssigned;
    }

    public void setIsAssigned(boolean isAssigned) {
        this.isAssigned = isAssigned;
    }
}
