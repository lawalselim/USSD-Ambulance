package com.example.backend.repository;

import com.example.backend.entities.Role;
import com.example.backend.entities.RoleEnum;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
    Optional<Role>findByName(RoleEnum  name);
}
