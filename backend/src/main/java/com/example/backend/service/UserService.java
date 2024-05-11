package com.example.backend.service;

import com.example.backend.dto.RegisterUserDto;
import com.example.backend.entities.Role;
import com.example.backend.entities.RoleEnum;
import com.example.backend.entities.User;
import com.example.backend.repository.RoleRepository;
import com.example.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository  userRepository, RoleRepository roleRepository,PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //Get all user in the database
    public List<User> allUsers(){
        List<User> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users;
    }

    public User createAdministrator(RegisterUserDto input){
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.ADMIN);

        if (optionalRole.isEmpty()){
            return null;
        }

        var user = new User()
                .setName(input.getName())
                .setEmail(input.getEmail())
                .setphoneNumber(input.getPhoneNumber())
                .setPassword(passwordEncoder.encode(input.getPassword()))
                .setRole(optionalRole.get());
        //System.out.println("I am trying to register a new user");
        return userRepository.save(user);
    }

}
