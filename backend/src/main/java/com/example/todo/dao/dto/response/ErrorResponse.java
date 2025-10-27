package com.example.todo.dao.dto.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    private String code;
    private String message;
}
