package com.example.backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String location;
    private Integer assignedAmbulances; // Number of ambulances currently assigned to this address

    // Constructors, getters, and setters

    // Default Constructor
    public Address() {
    }

    // Parameterized Constructor for convenience
    public Address(String location, Integer assignedAmbulances) {
        this.location = location;
        this.assignedAmbulances = assignedAmbulances;
    }

    // Getter and Setter for id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getter and Setter for location
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    // Getter and Setter for assignedAmbulances
    public Integer getAssignedAmbulances() {
        return assignedAmbulances;
    }

    public void setAssignedAmbulances(Integer assignedAmbulances) {
        this.assignedAmbulances = assignedAmbulances;
    }
}
