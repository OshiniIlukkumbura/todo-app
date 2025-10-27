package com.example.todo.uri;

public final class AppURI {
    private AppURI() {} // prevent instantiation

    // Base Paths =====
    public static final String BASE_API = "/api";
    public static final String VERSION_V1 = BASE_API + "/v1";

    // Resource Paths =====
    public static final String AUTH = VERSION_V1 + "/auth";
    public static final String TASK = VERSION_V1 + "/task";

    // Auth Endpoints =====
    public static final String REGISTER = "/register";
    public static final String LOGIN = "/login";
    public static final String REFRESH = "/refresh";

    // Task Endpoints =====
    public static final String INCOMPLETE_LIST = "/incomplete-list";
    public static final String COMPLETE = "/{id}/complete";
}
