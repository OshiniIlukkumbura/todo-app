package com.example.todo.service.impl;

import com.example.todo.dao.dto.request.AuthRequest;
import com.example.todo.dao.dto.request.RefreshRequest;
import com.example.todo.dao.dto.response.AuthResponse;
import com.example.todo.dao.entity.User;
import com.example.todo.dao.repository.UserRepository;
import com.example.todo.exception.InvalidCredentialsException;
import com.example.todo.security.JwtUtil;
import com.example.todo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class AuthServiceImplTest {


    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthServiceImpl authService;

    private AuthRequest request;

    @BeforeEach
    void setup() {
        request = new AuthRequest("john", "password123");
    }

    @Test
    void register_ShouldSaveUserAndReturnToken() {

        User savedUser = User.builder().id(1L).username("john").password("encoded").build();

        when(userService.registerUser("john", "password123")).thenReturn(savedUser);
        when(jwtUtil.generateAccessToken("john")).thenReturn("access-token-123");
        when(jwtUtil.generateRefreshToken("john")).thenReturn("refresh-token-123");

        AuthResponse response = authService.register(request);

        assertNotNull(response);
        assertEquals("access-token-123", response.getAccessToken());
        assertEquals("refresh-token-123", response.getRefreshToken());

        verify(userService).registerUser("john", "password123");
        verify(jwtUtil).generateAccessToken("john");
        verify(jwtUtil).generateRefreshToken("john");
    }

    @Test
    void login_ShouldReturnToken_WhenCredentialsAreValid() {

        when(jwtUtil.generateAccessToken("john")).thenReturn("access-token-abc");
        when(jwtUtil.generateRefreshToken("john")).thenReturn("refresh-token-abc");

        AuthResponse response = authService.login(request);

        assertEquals("access-token-abc", response.getAccessToken());
        assertEquals("refresh-token-abc", response.getRefreshToken());

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtil).generateAccessToken("john");
        verify(jwtUtil).generateRefreshToken("john");
    }

    @Test
    void login_ShouldThrowInvalidCredentialsException() {

        doThrow(new org.springframework.security.core.AuthenticationException("Bad credentials") {})
                .when(authenticationManager)
                .authenticate(any(UsernamePasswordAuthenticationToken.class));

        InvalidCredentialsException ex = assertThrows(
                InvalidCredentialsException.class,
                () -> authService.login(request)
        );
        assertEquals("Invalid username or password", ex.getMessage());
        verify(jwtUtil, never()).generateAccessToken(anyString());
        verify(jwtUtil, never()).generateRefreshToken(anyString());
    }

    @Test
    void refresh_ShouldReturnNewTokens_WhenRefreshValid() {
        RefreshRequest refreshRequest = new RefreshRequest();
        refreshRequest.setRefreshToken("valid-refresh-token");

        when(jwtUtil.validateToken("valid-refresh-token")).thenReturn(true);
        when(jwtUtil.extractUsername("valid-refresh-token")).thenReturn("john");
        when(jwtUtil.generateAccessToken("john")).thenReturn("new-access-token");
        when(jwtUtil.generateRefreshToken("john")).thenReturn("new-refresh-token");

        AuthResponse response = authService.refresh(refreshRequest);

        assertEquals("new-access-token", response.getAccessToken());
        assertEquals("new-refresh-token", response.getRefreshToken());
    }

    @Test
    void refresh_ShouldThrowException_WhenRefreshInvalid() {
        RefreshRequest refreshRequest = new RefreshRequest();
        refreshRequest.setRefreshToken("invalid-token");

        when(jwtUtil.validateToken("invalid-token")).thenReturn(false);

        assertThrows(InvalidCredentialsException.class, () -> authService.refresh(refreshRequest));
    }
}