package com.example.todo.dao.dto.build;

import com.example.todo.dao.dto.request.TaskCreateRequest;
import com.example.todo.dao.dto.response.TaskResponse;
import com.example.todo.dao.entity.Task;

public class TaskMapper {

    public static Task toEntity(TaskCreateRequest request) {
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setCompleted((short) 0);
        return task;
    }

    public static TaskResponse toResponse(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .completed(task.getCompleted())
                .createdAt(task.getCreatedAt())
                .updateAt(task.getUpdatedAt())
                .build();
    }
}
