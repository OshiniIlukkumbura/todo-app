package com.example.todo.dao.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskCreateRequest {

    @NotBlank(message = "Title is required")
    private String title;
    private String description;

}
