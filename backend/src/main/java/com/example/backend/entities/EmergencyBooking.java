package com.example.backend.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.Optional;

@Entity
public class EmergencyBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EmergencyTypeEnum emergencyTypeEnum;
    private String address;
    private double latitude;
    private double longitude;
    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne()
    @JoinColumn(name = "ambulance_id")
    private Ambulance ambulance; // reference to an ambulance

    private String status; // status of booking e.g., "PENDING", "ASSIGNED", "COMPLETED"

    //Constructor for getter and setters

    public EmergencyBooking(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser(){
        return user;
    }

    public void setUser(User user){
        this.user = user;
    }
    public EmergencyTypeEnum getEmergencyTypeEnum() {
        return emergencyTypeEnum;
    }

    public void setEmergencyTypeEnum(EmergencyTypeEnum emergencyTypeEnum) {
        this.emergencyTypeEnum = emergencyTypeEnum;
    }

    public String getAddress() {
        return address;
    }

    public EmergencyBooking setAddress(String address) {
        this.address = address;
        return this;
    }

    public double getLatitude() {
        return latitude;
    }

    public EmergencyBooking setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public double getLongitude() {
        return longitude;
    }

    public EmergencyBooking setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public EmergencyBooking setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Ambulance getAmbulance(){
        return ambulance;
    }

    public void setAmbulance(Ambulance ambulance){
        this.ambulance = ambulance;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }
}
