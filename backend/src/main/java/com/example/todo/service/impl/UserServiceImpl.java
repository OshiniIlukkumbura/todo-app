package com.example.todo.service.impl;

import com.example.todo.dao.entity.User;
import com.example.todo.dao.repository.UserRepository;
import com.example.todo.exception.UserAlreadyExistsException;
import com.example.todo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(String username, String password) {
        try {
            log.info("Registering new user: {}", username);

            if (userRepository.existsByUsername(username)) {
                throw new UserAlreadyExistsException("User with username '" + username + "' already exists");
            }

            User user = User.builder()
                    .username(username)
                    .password(passwordEncoder.encode(password))
                    .build();

            return userRepository.save(user);
        } catch (UserAlreadyExistsException ex) {
            log.warn("User already exists: {}", username, ex);
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to register user: {}", username, ex);
            throw new RuntimeException("Failed to register user", ex);
        }
    }


    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
}
