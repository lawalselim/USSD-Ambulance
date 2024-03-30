package com.example.backend.repository;

import com.example.backend.entities.EmergencyType;
import com.example.backend.entities.EmergencyTypeEnum;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmergencyTypeRepository extends CrudRepository<EmergencyType,Integer> {
    Optional<EmergencyType>findByName(EmergencyTypeEnum name);
}
