package com.example.backend.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Table(name ="emergencytype")
@Entity
public class EmergencyType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Integer id;

    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private EmergencyTypeEnum name;

    @Column(nullable = false)
    private String description;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    public EmergencyType setName(EmergencyTypeEnum name) {
        this.name = name;
        return this;
    }

    public EmergencyType setDescription(String description) {
        this.description = description;
        return this;
    }

    public EmergencyTypeEnum getName() {
        return name;
    }
}
