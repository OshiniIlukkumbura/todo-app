package com.example.todo.service.impl;

import com.example.todo.dao.dto.request.AuthRequest;
import com.example.todo.dao.dto.request.RefreshRequest;
import com.example.todo.dao.dto.response.AuthResponse;
import com.example.todo.dao.entity.User;
import com.example.todo.dao.repository.UserRepository;
import com.example.todo.exception.InvalidCredentialsException;
import com.example.todo.exception.UserAlreadyExistsException;
import com.example.todo.security.JwtUtil;
import com.example.todo.service.AuthService;
import com.example.todo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public AuthResponse register(AuthRequest request) {
        try {
            log.debug("Register called");
            User user = userService.registerUser(request.getUsername(), request.getPassword());
            String accessToken = jwtUtil.generateAccessToken(user.getUsername());
            String refreshToken = jwtUtil.generateRefreshToken(user.getUsername());
            return new AuthResponse(accessToken, refreshToken);
        } catch (UserAlreadyExistsException ex) {
            log.error("User already exists: ", ex);
            throw ex;
        }catch (Exception ex) {
            log.error("Failed to register user: ", ex);
            throw new RuntimeException("Failed to register user", ex);
        }
    }

    @Override
    public AuthResponse login(AuthRequest request) {
        try {
            log.debug("Login called");
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (AuthenticationException e) {
            throw new InvalidCredentialsException("Invalid username or password");
        }

        String accessToken = jwtUtil.generateAccessToken(request.getUsername());
        String refreshToken = jwtUtil.generateRefreshToken(request.getUsername());
        return new AuthResponse(accessToken, refreshToken);
    }

    @Override
    public AuthResponse refresh(RefreshRequest request) {
        log.debug("Refresh called");
        String refreshToken = request.getRefreshToken();
        if (!jwtUtil.validateToken(refreshToken)) {
            throw new InvalidCredentialsException("Invalid or expired refresh token");
        }
        String username = jwtUtil.extractUsername(refreshToken);
        String newAccessToken = jwtUtil.generateAccessToken(username);
        String newRefreshToken = jwtUtil.generateRefreshToken(username);
        return new AuthResponse(newAccessToken, newRefreshToken);
    }
}
