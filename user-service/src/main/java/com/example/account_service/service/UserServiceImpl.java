package com.example.account_service.service;

import com.example.account_service.dto.requestDto.CreateUserRequest;
import com.example.account_service.dto.requestDto.UpdateUserRequest;
import com.example.account_service.dto.responseDto.DeleteUserResponse;
import com.example.account_service.dto.responseDto.UserResponse;
import com.example.account_service.exception.ResourceNotFound;
import com.example.account_service.kafka.KafkaProducer;
import com.example.account_service.mapper.UserMapper;
import com.example.account_service.model.User;
import com.example.account_service.repository.UserRepository;
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
    private final KafkaProducer kafkaProducer;

    @Override
    public UserResponse createUser(CreateUserRequest request) {

        User user = userMapper.toEntity(request);
        User savedUser = userRepository.save(user);

        UserResponse resp = userMapper.toResponse(savedUser);
        kafkaProducer.publishUserCreatedEvent(savedUser);
        return resp;

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
