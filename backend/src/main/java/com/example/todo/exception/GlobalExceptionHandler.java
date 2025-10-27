package com.example.todo.exception;

import com.example.todo.dao.dto.response.AppResponse;
import com.example.todo.dao.enums.EApiStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public AppResponse<Object> handleTaskNotFound(TaskNotFoundException ex) {
        return AppResponse.failure(EApiStatus.NOT_FOUND, "TASK_NOT_FOUND", ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public AppResponse<Object> handleValidationException(MethodArgumentNotValidException ex) {
        String msg = ex.getBindingResult().getFieldErrors()
                .stream().map(e -> e.getField() + ": " + e.getDefaultMessage())
                .findFirst().orElse(ex.getMessage());

        return AppResponse.failure(EApiStatus.BAD_REQUEST, "VALIDATION_ERROR", msg);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public AppResponse<Object> handleGenericException(Exception ex) {
        return AppResponse.failure(EApiStatus.INTERNAL_ERROR, "INTERNAL_ERROR", ex.getMessage());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public AppResponse<Object> handleUserExists(UserAlreadyExistsException ex) {
        return AppResponse.failure(EApiStatus.CONFLICT, "USER_ALREADY_EXISTS", ex.getMessage());
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public AppResponse<Object> handleInvalidCredentials(InvalidCredentialsException ex) {
        return AppResponse.failure(EApiStatus.UNAUTHORIZED, "AUTH_FAILED", ex.getMessage());
    }


}
