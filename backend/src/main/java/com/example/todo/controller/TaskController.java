package com.example.todo.controller;

import com.example.todo.dao.dto.response.AppResponse;
import com.example.todo.dao.dto.request.TaskCreateRequest;
import com.example.todo.dao.dto.response.TaskResponse;
import com.example.todo.service.TaskService;
import com.example.todo.uri.AppURI;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(AppURI.TASK)
@CrossOrigin(origins = "*") // for frontend access
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping(AppURI.INCOMPLETE_LIST)
    @Operation(summary = "Get incomplete task list in descending order")
    public AppResponse<List<TaskResponse>> getIncompleteTasks(
            @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "5") Integer pageSize
    ) {
        return taskService.getIncompleteTasks(pageNumber, pageSize);
    }

    @PostMapping
    @Operation(summary = "Create a new task")
    public AppResponse<TaskResponse> createTask(@RequestBody @Valid TaskCreateRequest request) {
        return taskService.createTask(request);
    }

    @PatchMapping(AppURI.COMPLETE)
    @Operation(summary = "Update the completed status of a task")
    public AppResponse<TaskResponse> updateTaskStatus(
            @PathVariable("id") Long id,
            @RequestParam("completed") short completed
    ) {
        return taskService.updateTaskStatus(id, completed);
    }



}
