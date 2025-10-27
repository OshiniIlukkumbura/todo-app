package com.example.todo.service;

import com.example.todo.dao.dto.response.AppResponse;
import com.example.todo.dao.dto.request.TaskCreateRequest;
import com.example.todo.dao.dto.response.TaskResponse;

import java.util.List;

public interface TaskService {
    AppResponse<TaskResponse> createTask(TaskCreateRequest request);
    AppResponse<TaskResponse> updateTaskStatus(Long taskId, short completed);
    AppResponse<List<TaskResponse>> getIncompleteTasks(int pageNumber, int pageSize);

}
