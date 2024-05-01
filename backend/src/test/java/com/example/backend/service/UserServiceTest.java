package com.example.backend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.backend.dto.RegisterUserDto;
import com.example.backend.entities.Role;
import com.example.backend.entities.RoleEnum;
import com.example.backend.entities.User;
import com.example.backend.repository.RoleRepository;
import com.example.backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    public void testAllUsers() {
        // Setup
        User user1 = new User().setName("John Doe");
        User user2 = new User().setName("Jane Doe");
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        // Execution
        List<User> users = userService.allUsers();

        // Verification
        assertNotNull(users);
        assertEquals(2, users.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testCreateAdministratorSuccess() {
        // Setup
        RegisterUserDto dto = new RegisterUserDto();
        dto.setName("Admin User");
        dto.setEmail("admin@example.com");
        dto.setphoneNumber("1234567890");
        dto.setPassword("securePassword");

        Role adminRole = new Role();
        adminRole.setName(RoleEnum.ADMIN);

        when(roleRepository.findByName(RoleEnum.ADMIN)).thenReturn(Optional.of(adminRole));
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(new User());

        // Execution
        User result = userService.createAdministrator(dto);

        // Verification
        assertNotNull(result);
        verify(roleRepository, times(1)).findByName(RoleEnum.ADMIN);
        verify(passwordEncoder, times(1)).encode("securePassword");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testCreateAdministratorRoleNotFound() {
        // Setup
        RegisterUserDto dto = new RegisterUserDto();
        dto.setName("Admin User");
        dto.setEmail("admin@example.com");
        dto.setphoneNumber("1234567890");
        dto.setPassword("securePassword");

        when(roleRepository.findByName(RoleEnum.ADMIN)).thenReturn(Optional.empty());

        // Execution
        User result = userService.createAdministrator(dto);

        // Verification
        assertNull(result);
        verify(roleRepository, times(1)).findByName(RoleEnum.ADMIN);
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(User.class));
    }
}
