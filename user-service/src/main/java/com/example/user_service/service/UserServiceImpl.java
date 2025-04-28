package com.example.user_service.service;

import com.example.user_service.dto.requestDto.CreateUserRequest;
import com.example.user_service.dto.requestDto.UpdateUserRequest;
import com.example.user_service.dto.responseDto.DeleteUserResponse;
import com.example.user_service.dto.responseDto.UserResponse;
import com.example.user_service.exception.ResourceNotFound;
import com.example.user_service.mapper.UserMapper;
import com.example.user_service.model.User;
import com.example.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponse createUser(CreateUserRequest request) {

        User user = userMapper.toEntity(request);
        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);

    }

    @Override
    public UserResponse updateUser(Long id, UpdateUserRequest request) {
                User existingUser = userRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFound("User not found with id: " + id));

                userMapper.updateEntityFromDto(request, existingUser);
                User updatedUser = userRepository.save(existingUser);
                return userMapper.toResponse(updatedUser);
    }

    @Override
    public UserResponse getUserById(Long id) {
                User user = userRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFound("User not found with id: " + id));
        return userMapper.toResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public DeleteUserResponse deleteUserById(Long id) {
                User user = userRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFound("User not found with id: " + id));
                                userRepository.delete(user);
        return DeleteUserResponse.builder()
                .success(true)
                .message("User deleted successfully")
                .build();
    }
}
