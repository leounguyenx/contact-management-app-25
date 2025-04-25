package miu.cs525.contactmgtv2.controller;

import miu.cs525.contactmgtv2.dto.request.AddressRequestDto;
import miu.cs525.contactmgtv2.dto.request.UserRequestDto;
import miu.cs525.contactmgtv2.dto.request.UserRequestUpdateDto;
import miu.cs525.contactmgtv2.dto.response.AddressResponseDto;
import miu.cs525.contactmgtv2.dto.response.UserResponseDto;
import miu.cs525.contactmgtv2.model.Role;
import miu.cs525.contactmgtv2.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_ShouldReturnCreatedUser() {
        // Arrange
        UserRequestDto requestDto = new UserRequestDto(
            "John",
            "Doe",
            "test@example.com",
            "password123",
            "1234567890",
            new AddressRequestDto("123 Main St", "New York", "NY", "10001"),
            Role.USER
        );
        UserResponseDto responseDto = new UserResponseDto(
            "John",
            "Doe",
            "test@example.com",
            "1234567890",
            new AddressResponseDto("123 Main St", "New York", "NY", "10001"),
            Role.USER
        );
        when(userService.createUser(requestDto)).thenReturn(responseDto);

        // Act
        ResponseEntity<UserResponseDto> response = userController.createUser(requestDto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
        verify(userService, times(1)).createUser(requestDto);
    }

    @Test
    void getUserById_ShouldReturnUser() {
        // Arrange
        Long userId = 1L;
        UserResponseDto responseDto = new UserResponseDto(
            "John",
            "Doe",
            "test@example.com",
            "1234567890",
            new AddressResponseDto("123 Main St", "New York", "NY", "10001"),
            Role.USER
        );
        when(userService.getUserById(userId)).thenReturn(responseDto);

        // Act
        ResponseEntity<UserResponseDto> response = userController.getUserById(userId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
        verify(userService, times(1)).getUserById(userId);
    }

    @Test
    void updateUser_ShouldReturnUpdatedUser() {
        // Arrange
        Long userId = 1L;
        UserRequestUpdateDto requestDto = new UserRequestUpdateDto(
            "John",
            "Doe",
            "updated@example.com",
            "newpassword123",
            "1234567890",
            new AddressRequestDto("123 Main St", "New York", "NY", "10001")
        );
        UserResponseDto responseDto = new UserResponseDto(
            "John",
            "Doe",
            "updated@example.com",
            "1234567890",
            new AddressResponseDto("123 Main St", "New York", "NY", "10001"),
            Role.USER
        );
        when(userService.updateUser(userId, requestDto)).thenReturn(responseDto);

        // Act
        ResponseEntity<UserResponseDto> response = userController.updateUser(userId, requestDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
        verify(userService, times(1)).updateUser(userId, requestDto);
    }

    @Test
    void deleteUser_ShouldReturnNoContent() {
        // Arrange
        Long userId = 1L;
        doNothing().when(userService).deleteUser(userId);

        // Act
        ResponseEntity<Void> response = userController.deleteUser(userId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userService, times(1)).deleteUser(userId);
    }
} 