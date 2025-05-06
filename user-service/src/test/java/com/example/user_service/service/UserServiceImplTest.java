package com.example.user_service.service;

import com.example.user_service.dto.requestDto.CreateUserRequest;
import com.example.user_service.dto.responseDto.UserResponse;
import com.example.user_service.kafka.KafkaProducer;
import com.example.user_service.mapper.UserMapper;
import com.example.user_service.model.User;
import com.example.user_service.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private KafkaProducer kafkaProducer;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testCreateUser_success() {
        // Arrange
        CreateUserRequest request = new CreateUserRequest();
        request.setUserId("testUserId");
        request.setEmail("testEmail");
        request.setFirstName("testName");
        request.setLastName("testLastName");

        User userEntity = new User();
        userEntity.setUserId("testUserEntityId");
        userEntity.setEmail("testUserEntityEmail");
        userEntity.setFirstName("testUserEntityFirstName");
        userEntity.setLastName("testUserEntityLastName");

        UserResponse response = new UserResponse();
        response.setId(1L);
        response.setUserId("testResponseUserId");
        response.setEmail("testResponseEmail");
        response.setFirstName("testResponseFirstName");
        response.setLastName("testResponseLastName");

        // Act
        when(userMapper.toEntity(request)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(userMapper.toResponse(userEntity)).thenReturn(response);

        UserResponse result = userService.createUser(request);

        // Assert
        assertNotNull(result);
        assertEquals(response, result);
        assertEquals(1L, result.getId());
        assertEquals("testResponseUserId", result.getUserId());
        assertEquals("testResponseEmail", result.getEmail());
        assertEquals("testResponseFirstName", result.getFirstName());
        assertEquals("testResponseLastName", result.getLastName());

        verify(userMapper).toEntity(request);
        verify(userRepository).save(userEntity);
    }
}
