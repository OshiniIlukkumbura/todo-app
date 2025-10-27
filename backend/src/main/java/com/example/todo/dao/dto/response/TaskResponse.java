package com.example.todo.dao.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private Short completed;
    @JsonProperty("created_at")
    private Date createdAt;
    @JsonProperty("updated_at")
    private Date updateAt;
}
