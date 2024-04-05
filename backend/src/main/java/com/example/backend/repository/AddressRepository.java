package com.example.backend.repository;

import com.example.backend.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findByLocation(String location);
}
