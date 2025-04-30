package com.example.transaction_service.service;

import com.example.transaction_service.dto.requestDto.*;
import com.example.transaction_service.dto.responseDto.*;
import java.util.List;

public interface UserService {
    UserResponse createUser(CreateUserRequest request);
    UserResponse updateUser(Long id, UpdateUserRequest request);
    UserResponse getUserById(Long id);
    List<UserResponse> getAllUsers();
    DeleteUserResponse deleteUserById(Long id);
}
