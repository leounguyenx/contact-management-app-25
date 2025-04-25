package miu.cs525.contactmgtv2.controller;

import lombok.RequiredArgsConstructor;
import miu.cs525.contactmgtv2.dto.request.UserRequestDto;
import miu.cs525.contactmgtv2.dto.request.UserRequestUpdateDto;
import miu.cs525.contactmgtv2.dto.response.ContactResponseDto;
import miu.cs525.contactmgtv2.dto.response.UserResponseDto;
import miu.cs525.contactmgtv2.service.ContactService;
import miu.cs525.contactmgtv2.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ContactService contactService;

    // Create a new user
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto userRequestDto) {
        UserResponseDto createdUser = userService.createUser(userRequestDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    // Update existing user
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable("id") Long userId,
            @RequestBody UserRequestUpdateDto userRequestUpdateDto
    ) {
        UserResponseDto response = userService.updateUser(userId, userRequestUpdateDto);
        return ResponseEntity.ok(response);
    }

    // Delete a user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    // Get all users with paging
    @GetMapping
    public Page<UserResponseDto> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "asc") String sort
    ) {
        return userService.getAllUsers(page, size, sort);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable("id") Long userId) {
        UserResponseDto user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{userId}/contacts")
    public ResponseEntity<Page<ContactResponseDto>> getAllContactsByUser(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        Page<ContactResponseDto> contactPage = contactService.getAllContactsByUser(userId, page, pageSize, sortDirection);
        return ResponseEntity.ok(contactPage);
    }
}



