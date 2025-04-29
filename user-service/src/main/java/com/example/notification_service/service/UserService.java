package com.example.notification_service.service;

import com.example.notification_service.dto.requestDto.*;
import com.example.notification_service.dto.responseDto.*;
import java.util.List;

public interface UserService {
    UserResponse createUser(CreateUserRequest request);
    UserResponse updateUser(Long id, UpdateUserRequest request);
    UserResponse getUserById(Long id);
    List<UserResponse> getAllUsers();
    DeleteUserResponse deleteUserById(Long id);
}
