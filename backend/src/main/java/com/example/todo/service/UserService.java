package com.example.todo.service;

import com.example.todo.dao.entity.User;

public interface UserService {
    User registerUser(String username, String password);

    User findByUsername(String username);
}
