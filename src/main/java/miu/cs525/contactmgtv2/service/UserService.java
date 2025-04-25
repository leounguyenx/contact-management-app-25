package miu.cs525.contactmgtv2.service;
import miu.cs525.contactmgtv2.dto.request.UserRequestDto;
import miu.cs525.contactmgtv2.dto.request.UserRequestUpdateDto;
import miu.cs525.contactmgtv2.dto.response.UserResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    // Add a new user
    UserResponseDto createUser(UserRequestDto userRequestDto);

    // Update user info
    UserResponseDto updateUser(Long userId, UserRequestUpdateDto userRequestUpdateDto);

    // Delete a user
    void deleteUser(Long userId);

    // Get all users
    Page<UserResponseDto> getAllUsers(int page, int pageSize, String sortDirection);

    // Get user by id
    UserResponseDto getUserById(Long userId);
}
