package com.example.todo.util;

public final class SecurityConstants {

    // private constructor to prevent instantiation
    private SecurityConstants() {
    }

    public static final String AUTH_HEADER = "Authorization";
    public static final String REFRESH_HEADER = "X-Refresh-Token";
    public static final String BEARER_PREFIX = "Bearer ";
}