package com.example.todo.controller;


import com.example.todo.dao.dto.request.AuthRequest;
import com.example.todo.dao.dto.request.RefreshRequest;
import com.example.todo.dao.dto.response.AuthResponse;
import com.example.todo.dao.dto.response.AppResponse;
import com.example.todo.service.AuthService;
import com.example.todo.uri.AppURI;
import com.example.todo.util.SecurityConstants;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AppURI.AUTH)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(AppURI.LOGIN)
    @Operation(summary = "Login user")
    public AppResponse<Void> login(@RequestBody AuthRequest request, HttpServletResponse response) {
        AuthResponse authResponse = authService.login(request);
        response.setHeader(SecurityConstants.AUTH_HEADER, SecurityConstants.BEARER_PREFIX + authResponse.getAccessToken());
        response.setHeader(SecurityConstants.REFRESH_HEADER, authResponse.getRefreshToken());
        return AppResponse.success(null, "Login successful");
    }

    @PostMapping(AppURI.REGISTER)
    @Operation(summary = "Register user")
    public AppResponse<Void> register(@RequestBody AuthRequest request, HttpServletResponse response) {
        AuthResponse authResponse = authService.register(request);
        response.setHeader(SecurityConstants.AUTH_HEADER, SecurityConstants.BEARER_PREFIX + authResponse.getAccessToken());
        response.setHeader(SecurityConstants.REFRESH_HEADER, authResponse.getRefreshToken());
        return AppResponse.success(null, "User registered successfully");
    }

    @PostMapping(AppURI.REFRESH)
    @Operation(summary = "Refresh token")
    public AppResponse<Void> refresh(@RequestBody RefreshRequest request, HttpServletResponse response) {
        AuthResponse authResponse = authService.refresh(request);
        response.setHeader(SecurityConstants.AUTH_HEADER, SecurityConstants.BEARER_PREFIX + authResponse.getAccessToken());
        response.setHeader(SecurityConstants.REFRESH_HEADER, authResponse.getRefreshToken());
        return AppResponse.success(null, "Token refreshed successfully");
    }
}
