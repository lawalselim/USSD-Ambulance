package com.example.backend.repository;

import com.example.backend.entities.EmergencyType;
import com.example.backend.entities.EmergencyTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmergencyTypeRepository extends JpaRepository<EmergencyType, Long> {
    Optional<EmergencyType>findByName(EmergencyTypeEnum name);
}
