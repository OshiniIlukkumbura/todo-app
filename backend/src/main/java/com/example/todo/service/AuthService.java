package com.example.todo.service;

import com.example.todo.dao.dto.request.AuthRequest;
import com.example.todo.dao.dto.request.RefreshRequest;
import com.example.todo.dao.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse register(AuthRequest request);

    AuthResponse login(AuthRequest request);

    AuthResponse refresh(RefreshRequest request);
}
