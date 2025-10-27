package com.example.todo.service.impl;

import com.example.todo.dao.entity.User;
import com.example.todo.dao.repository.UserRepository;
import com.example.todo.exception.UserAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setup() {
        user = User.builder()
                .id(1L)
                .username("john")
                .password("encoded")
                .build();
    }


    @Test
    void registerUser_ShouldSaveUser_WhenUsernameNotExists() {

        when(userRepository.existsByUsername("john")).thenReturn(false);
        when(passwordEncoder.encode("password")).thenReturn("encoded");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.registerUser("john", "password");

        assertNotNull(result);
        assertEquals("john", result.getUsername());
        assertEquals("encoded", result.getPassword());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void registerUser_ShouldThrowException_WhenUserAlreadyExists() {
        when(userRepository.existsByUsername("john")).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, () ->
                userService.registerUser("john", "password")
        );
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void findByUsername_ShouldReturnUser_WhenExists() {

        when(userRepository.findByUsername("john")).thenReturn(Optional.of(user));

        User result = userService.findByUsername("john");

        assertNotNull(result);
        assertEquals("john", result.getUsername());
    }

    @Test
    void findByUsername_ShouldReturnNull_WhenNotFound() {

        when(userRepository.findByUsername("john")).thenReturn(Optional.empty());
        User result = userService.findByUsername("john");

        assertNull(result);
    }
}