package com.example.backend.bootstrap;

import com.example.backend.dto.RegisterUserDto;
import com.example.backend.entities.Role;
import com.example.backend.entities.RoleEnum;
import com.example.backend.entities.User;
import com.example.backend.repository.RoleRepository;
import com.example.backend.repository.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AdminSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    public AdminSeeder(
            RoleRepository roleRepository,
            UserRepository  userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.createSuperAdministrator();
    }

    private void createSuperAdministrator() {
        RegisterUserDto userDto = new RegisterUserDto();
        userDto.setName("Super Admin").setEmail("super.admin@email.com").setPassword("123456").setphoneNumber("08155683218");

        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.SUPER_ADMIN);
        Optional<User> optionalUser = userRepository.findByEmail(userDto.getEmail());

        if (optionalRole.isEmpty() || optionalUser.isPresent()) {
            return;
        }

        var user = new User()
                .setName(userDto.getName())
                .setEmail(userDto.getEmail())
                .setphoneNumber(userDto.getPhoneNumber())
                .setPassword(passwordEncoder.encode(userDto.getPassword()))
                .setRole(optionalRole.get());

        userRepository.save(user);
    }
}
