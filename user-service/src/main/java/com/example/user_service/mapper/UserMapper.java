package com.example.user_service.mapper;


import com.example.user_service.dto.requestDto.CreateUserRequest;
import com.example.user_service.dto.requestDto.UpdateUserRequest;
import com.example.user_service.dto.responseDto.UserResponse;
import com.example.user_service.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {


    public User toEntity(CreateUserRequest request) {
        return User.builder()
                .userId(request.getUserId())
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();
    }


    public UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .userId(user.getUserId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }

    public void updateEntityFromDto(UpdateUserRequest request, User user) {
        user.setUserId(request.getUserId());
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
    }
}
