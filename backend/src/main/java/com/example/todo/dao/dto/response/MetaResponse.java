package com.example.todo.dao.dto.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetaResponse {

    private Integer code;
    private Short status;
    private String message;
}
