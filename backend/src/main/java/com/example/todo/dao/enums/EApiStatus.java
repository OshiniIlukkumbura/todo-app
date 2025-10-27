package com.example.todo.dao.enums;

import lombok.Getter;

@Getter
public enum EApiStatus {

    SUCCESS(200, "SUCCESS", (short)1),
    CREATED(201, "CREATED", (short)1),
    BAD_REQUEST(400, "BAD REQUEST", (short)0),
    UNAUTHORIZED(401, "UNAUTHORIZED", (short)0),
    FORBIDDEN(403, "FORBIDDEN", (short)0),
    NOT_FOUND(404, "NOT FOUND", (short)0),
    CONFLICT(409, "CONFLICT", (short)0),
    INTERNAL_ERROR(500, "INTERNAL SERVER ERROR", (short) 0);

    private final int code;
    private final String message;
    private final short status;

    EApiStatus(int code, String message, short status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

}

