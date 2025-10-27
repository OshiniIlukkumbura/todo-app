package com.example.todo.service.impl;

import com.example.todo.dao.dto.response.AppResponse;
import com.example.todo.dao.dto.response.PagingResponse;
import com.example.todo.dao.dto.request.TaskCreateRequest;
import com.example.todo.dao.dto.response.TaskResponse;
import com.example.todo.dao.dto.build.TaskMapper;
import com.example.todo.dao.entity.Task;
import com.example.todo.dao.entity.User;
import com.example.todo.dao.repository.TaskRepository;
import com.example.todo.dao.repository.UserRepository;
import com.example.todo.exception.TaskNotFoundException;
import com.example.todo.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    private User getCurrentUser() {
        log.debug("Get Current User called");
        // get the authentication object stored in Spring Security's context for this request
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            // Extract the username from UserDetails
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Logged-in user not found"));
    }

    @Override
    public AppResponse<List<TaskResponse>> getIncompleteTasks(int pageNumber, int pageSize) {

        try {
            log.debug("Get incomplete tasks called");
            User currentUser = getCurrentUser();
            PageRequest pageable = PageRequest.of(pageNumber, pageSize);
            Page<Task> taskPage = taskRepository.findIncompleteTasksByUser(currentUser.getId(), pageable);
            log.debug("taskPage total count : {}", taskPage.getTotalElements());
            List<TaskResponse> responseList = taskPage.getContent()
                    .stream().map(TaskMapper::toResponse)
                    .collect(Collectors.toList());

            PagingResponse paging = PagingResponse.builder()
                    .pageNumber(taskPage.getNumber() + 1)
                    .pageSize(taskPage.getSize())
                    .totalRecords(taskPage.getTotalElements())
                    .build();

            return AppResponse.successWithPaging(responseList, "Incomplete tasks fetched successfully", paging);

        } catch (Exception ex) {
            log.error("Failed to fetch incomplete tasks", ex);
            throw new RuntimeException("Failed to fetch incomplete tasks", ex);
        }

    }

    @Override
    public AppResponse<TaskResponse> createTask(TaskCreateRequest request) {
        try {
            log.debug("Create Task called");
            log.debug("Creating task: {}", request.getTitle());
            User currentUser = getCurrentUser();
            Task task = TaskMapper.toEntity(request);
            task.setUser(currentUser); // assign task to the logged in user
            Task saved = taskRepository.save(task);
            return AppResponse.success(TaskMapper.toResponse(saved), "Task created successfully");
        } catch (Exception ex) {
            log.error("Failed to create task: {}", request.getTitle(), ex);
            throw new RuntimeException("Failed to create task", ex);
        }

    }

    @Override
    public AppResponse<TaskResponse> updateTaskStatus(Long taskId, short completed) {
        try {
            log.debug("Update Task Status called");
            log.debug("Updating task: {} into completed status {}", taskId, completed);
            Task task = taskRepository.findById(taskId)
                    .orElseThrow(() -> new TaskNotFoundException("Task with ID " + taskId + " not found"));

            task.setCompleted(completed);
            Task updated = taskRepository.save(task);

            log.debug("Updated task status: {} -> {}", taskId, completed);
            return AppResponse.success(TaskMapper.toResponse(updated), "Task status updated successfully");
        } catch (TaskNotFoundException ex) {
            log.error("Task not found: {}", taskId, ex);
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to update task status: {}", taskId, ex);
            throw new RuntimeException("Failed to update task status", ex);
        }
    }


}
