package com.example.todo.service.impl;

import com.example.todo.dao.dto.build.TaskMapper;
import com.example.todo.dao.dto.request.TaskCreateRequest;
import com.example.todo.dao.dto.response.AppResponse;
import com.example.todo.dao.dto.response.TaskResponse;
import com.example.todo.dao.entity.Task;
import com.example.todo.dao.entity.User;
import com.example.todo.dao.enums.EApiStatus;
import com.example.todo.dao.repository.TaskRepository;
import com.example.todo.dao.repository.UserRepository;
import com.example.todo.exception.TaskNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TaskServiceImplTest {

    // create a mock version of TaskRepository.
    @Mock
    private TaskRepository taskRepository;

    // create a mock version of UserRepository.
    @Mock
    private UserRepository userRepository;

    // create an instance of TaskServiceImpl and injects the mocked TaskRepository into it.
    @InjectMocks
    private TaskServiceImpl taskService;

    private User mockUser;

    @BeforeEach     // runs before every test method.
    void setUp() {
        MockitoAnnotations.openMocks(this);      // initializes the mock objects

        // Create a mock logged-in user
        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("testuser");

        // Mock SecurityContext to provide the logged-in user
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(mockUser.getUsername())
                .password("password")
                .authorities(Collections.emptyList())
                .build();
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        // Mock userRepository to return the current user
        when(userRepository.findByUsername(mockUser.getUsername())).thenReturn(Optional.of(mockUser));
    }

    @Test
    void getIncompleteTasks_ShouldReturnPaginatedResults() {

        int pageNumber = 0;
        int pageSize = 5;

        Task task = new Task();
        task.setId(1L);
        task.setTitle("Incomplete Todo Task");
        task.setCompleted((short)0);

        // mocked Page result created manually for testing  pagination
        Page<Task> page = new PageImpl<>(Collections.singletonList(task));

        // when this method called then return this result instead of executing any real logic
        when(taskRepository.findIncompleteTasksByUser(eq(mockUser.getId()),any(PageRequest.class))).thenReturn(page);

        AppResponse<List<TaskResponse>> response = taskService.getIncompleteTasks(pageNumber, pageSize);


        assertNotNull(response);
        assertEquals(1, Integer.valueOf(response.getMeta().getStatus()));
        assertEquals("Incomplete tasks fetched successfully", response.getMeta().getMessage());
        assertEquals(1, response.getData().size());
        assertEquals("Incomplete Todo Task", response.getData().get(0).getTitle());
        assertEquals(EApiStatus.SUCCESS.getStatus(), response.getMeta().getStatus());
        // to verify the repository was actually called correctly
        verify(taskRepository, times(1)).findIncompleteTasksByUser(eq(mockUser.getId()),any(PageRequest.class));

    }

    @Test
    void getIncompleteTasks_ShouldReturnEmptyList() {
        int pageNumber = 0;
        int pageSize = 5;

        Page<Task> page = new PageImpl<>(Collections.emptyList());
        when(taskRepository.findIncompleteTasksByUser(eq(mockUser.getId()),any(PageRequest.class))).thenReturn(page);

        AppResponse<List<TaskResponse>> response = taskService.getIncompleteTasks(pageNumber, pageSize);
        assertNotNull(response);
        assertEquals(EApiStatus.SUCCESS.getStatus(), response.getMeta().getStatus());
        assertTrue(response.getData().isEmpty());
        verify(taskRepository).findIncompleteTasksByUser(eq(mockUser.getId()),any(PageRequest.class));


    }

    @Test
    void createTask_ShouldReturnSuccessResponse() {
        TaskCreateRequest request = new TaskCreateRequest();
        request.setTitle("Task 1");
        request.setDescription("Task 1 Description");

        Task taskEntity = TaskMapper.toEntity(request);
        taskEntity.setId(1L);
        taskEntity.setUser(mockUser);

        when(taskRepository.save(any(Task.class))).thenReturn(taskEntity);
        AppResponse<TaskResponse> response = taskService.createTask(request);

        assertNotNull(response);
        assertEquals(EApiStatus.SUCCESS.getStatus(), response.getMeta().getStatus());
        assertEquals("Task created successfully", response.getMeta().getMessage());
        assertEquals("Task 1", response.getData().getTitle());
        assertEquals("Task 1 Description", response.getData().getDescription());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void updateTaskStatus_ShouldUpdateSuccessfully() {

        Long taskId = 1L;
        short completed = 1;
        Task existingTask = new Task();
        existingTask.setId(taskId);
        existingTask.setTitle("Sample Task");
        existingTask.setCompleted((short) 0);

        // return the given data when try to find existing record
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenReturn(existingTask);

        AppResponse<TaskResponse> response = taskService.updateTaskStatus(taskId, completed);

        assertNotNull(response);
        assertEquals("Task status updated successfully", response.getMeta().getMessage());
        assertEquals(completed, response.getData().getCompleted());
        assertEquals(EApiStatus.SUCCESS.getStatus(), response.getMeta().getStatus());
        verify(taskRepository).findById(taskId);
        verify(taskRepository).save(existingTask);


    }

    @Test
    void updateTaskStatus_ShouldThrowException() {
        Long taskId = 999L;
        short completed = 1;

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.updateTaskStatus(taskId, completed));
        verify(taskRepository, never()).save(any(Task.class));
    }

}