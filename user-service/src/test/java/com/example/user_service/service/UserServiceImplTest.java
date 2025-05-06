package com.example.user_service.service;

import com.example.user_service.dto.requestDto.CreateUserRequest;
import com.example.user_service.dto.requestDto.UpdateUserRequest;
import com.example.user_service.dto.responseDto.DeleteUserResponse;
import com.example.user_service.dto.responseDto.UserResponse;
import com.example.user_service.enums.ResponseEnum;
import com.example.user_service.exception.ErrorDetails;
import com.example.user_service.kafka.KafkaProducer;
import com.example.user_service.mapper.UserMapper;
import com.example.user_service.model.User;
import com.example.user_service.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    @Test
    void testCreateUser_failure() {
        CreateUserRequest request = new CreateUserRequest();
        request.setEmail("testEmail");
        request.setFirstName("testName");
        request.setLastName("testLastName");

        ErrorDetails errorDetails = new ErrorDetails();

        errorDetails.setErrorCode(ResponseEnum.INTERNAL_SERVER_ERROR);
        errorDetails.setPath("/api/v1/users");
        errorDetails.setMessage("Validation failed for argument [0] in public org.springframework.http.ResponseEntity<com.example.user_service.dto.responseDto.UserResponse> com.example.user_service.controller.UserController.createUser(com.example.user_service.dto.requestDto.CreateUserRequest): [Field error in object 'createUserRequest' on field 'userId': rejected value [null]; codes [NotBlank.createUserRequest.userId,NotBlank.userId,NotBlank.java.lang.String,NotBlank]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [createUserRequest.userId,userId]; arguments []; default message [userId]]; default message [User ID is required]] ");
        errorDetails.setTimestamp(LocalDateTime.now());

        assertEquals(null, userService.createUser(request));

    }


    @Test
    void testGetUser_success() {
        // Arrange
        Long userId = 1L;
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
        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(userMapper.toResponse(userEntity)).thenReturn(response);

        UserResponse result = userService.getUserById(userId);

        // Assert
        assertNotNull(result);
        assertEquals(response, result);
        assertEquals(1L, result.getId());
        assertEquals("testResponseUserId", result.getUserId());
        assertEquals("testResponseEmail", result.getEmail());
        assertEquals("testResponseFirstName", result.getFirstName());
        assertEquals("testResponseLastName", result.getLastName());


    }


    @Test
    void testGetAllUsers_success() {
        // Arrange
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
        when(userRepository.findAll()).thenReturn(List.of(userEntity));
        when(userMapper.toResponse(userEntity)).thenReturn(response);

        List<UserResponse> result = userService.getAllUsers();

        // Assert
        assertNotNull(result);
        assertEquals(List.of(response), result);
        assertEquals(1L, result.get(0).getId());
        assertEquals("testResponseUserId", result.get(0).getUserId());
        assertEquals("testResponseEmail", result.get(0).getEmail());
        assertEquals("testResponseFirstName", result.get(0).getFirstName());
        assertEquals("testResponseLastName", result.get(0).getLastName());

    }


    @Test
    public void testDeleteUser_success() {
        // Arrange
        Long id = 1L;

        // Create a mock user to simulate a user being found
        User mockUser = new User();
        mockUser.setUserId("testUserId");
        mockUser.setEmail("testEmail");
        mockUser.setFirstName("testName");
        mockUser.setLastName("testLastName");

        DeleteUserResponse expectedResponse = new DeleteUserResponse();
        expectedResponse.setSuccess(true);
        expectedResponse.setMessage("User deleted successfully");

        when(userRepository.findById(id)).thenReturn(Optional.of(mockUser));  // Mock findById

        lenient().doNothing().when(userRepository).delete(mockUser);  // Mock delete

        DeleteUserResponse result = userService.deleteUserById(id);

        // Assert
        assertNotNull(result);
        assertEquals(expectedResponse.getSuccess(), result.getSuccess());
        assertEquals(expectedResponse.getMessage(), result.getMessage());

        verify(userRepository, times(1)).delete(mockUser);
    }




}
