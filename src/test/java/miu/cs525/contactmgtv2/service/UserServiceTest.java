package miu.cs525.contactmgtv2.service;

import miu.cs525.contactmgtv2.dto.request.AddressRequestDto;
import miu.cs525.contactmgtv2.dto.request.UserRequestDto;
import miu.cs525.contactmgtv2.dto.request.UserRequestUpdateDto;
import miu.cs525.contactmgtv2.dto.response.AddressResponseDto;
import miu.cs525.contactmgtv2.dto.response.UserResponseDto;
import miu.cs525.contactmgtv2.exception.ResourceNotFoundException;
import miu.cs525.contactmgtv2.model.Address;
import miu.cs525.contactmgtv2.model.Role;
import miu.cs525.contactmgtv2.model.User;
import miu.cs525.contactmgtv2.repository.AddressRepository;
import miu.cs525.contactmgtv2.repository.UserRepository;
import miu.cs525.contactmgtv2.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_ShouldReturnUserResponseDto() {
        // Arrange
        UserRequestDto requestDto = new UserRequestDto(
            "John",
            "Doe",
            "john@example.com",
            "password123",
            "123-456-7890",
            new AddressRequestDto("123 Main St", "New York", "NY", "10001"),
            Role.USER
        );

        Address address = new Address();
        address.setStreet("123 Main St");
        address.setCity("New York");
        address.setState("NY");
        address.setZip("10001");

        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john@example.com");
        user.setPassword("password123");
        user.setPhone("123-456-7890");
        user.setAddress(address);
        user.setRole(Role.USER);

        when(addressRepository.save(any(Address.class))).thenReturn(address);
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        UserResponseDto responseDto = userService.createUser(requestDto);

        // Assert
        assertNotNull(responseDto);
        assertEquals("John", responseDto.firstName());
        assertEquals("Doe", responseDto.lastName());
        assertEquals("john@example.com", responseDto.email());
        assertEquals("123-456-7890", responseDto.phone());
        assertEquals(Role.USER, responseDto.role());
        assertNotNull(responseDto.addressResponseDto());
        assertEquals("123 Main St", responseDto.addressResponseDto().street());
        assertEquals("New York", responseDto.addressResponseDto().city());
        assertEquals("NY", responseDto.addressResponseDto().state());
        assertEquals("10001", responseDto.addressResponseDto().zip());

        verify(addressRepository, times(1)).save(any(Address.class));
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void getUserById_ShouldReturnUserResponseDto() {
        // Arrange
        Long userId = 1L;
        Address address = new Address();
        address.setStreet("123 Main St");
        address.setCity("New York");
        address.setState("NY");
        address.setZip("10001");

        User user = new User();
        user.setUserId(userId);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john@example.com");
        user.setPassword("password123");
        user.setPhone("123-456-7890");
        user.setAddress(address);
        user.setRole(Role.USER);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        UserResponseDto responseDto = userService.getUserById(userId);

        // Assert
        assertNotNull(responseDto);
        assertEquals("John", responseDto.firstName());
        assertEquals("Doe", responseDto.lastName());
        assertEquals("john@example.com", responseDto.email());
        assertEquals("123-456-7890", responseDto.phone());
        assertEquals(Role.USER, responseDto.role());
        assertNotNull(responseDto.addressResponseDto());
        assertEquals("123 Main St", responseDto.addressResponseDto().street());
        assertEquals("New York", responseDto.addressResponseDto().city());
        assertEquals("NY", responseDto.addressResponseDto().state());
        assertEquals("10001", responseDto.addressResponseDto().zip());

        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void updateUser_ShouldReturnUpdatedUserResponseDto() {
        // Arrange
        Long userId = 1L;
        UserRequestUpdateDto requestDto = new UserRequestUpdateDto(
            "Jane",
            "Smith",
            "jane@example.com",
            "newpassword123",
            "987-654-3210",
            new AddressRequestDto("456 Oak Ave", "Los Angeles", "CA", "90001")
        );

        Address address = new Address();
        address.setStreet("456 Oak Ave");
        address.setCity("Los Angeles");
        address.setState("CA");
        address.setZip("90001");

        User existingUser = new User();
        existingUser.setUserId(userId);
        existingUser.setFirstName("John");
        existingUser.setLastName("Doe");
        existingUser.setEmail("john@example.com");
        existingUser.setPassword("password123");
        existingUser.setPhone("123-456-7890");
        existingUser.setAddress(address);
        existingUser.setRole(Role.USER);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(addressRepository.save(any(Address.class))).thenReturn(address);
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        // Act
        UserResponseDto responseDto = userService.updateUser(userId, requestDto);

        // Assert
        assertNotNull(responseDto);
        assertEquals("Jane", responseDto.firstName());
        assertEquals("Smith", responseDto.lastName());
        assertEquals("jane@example.com", responseDto.email());
        assertEquals("987-654-3210", responseDto.phone());
        assertEquals(Role.USER, responseDto.role());
        assertNotNull(responseDto.addressResponseDto());
        assertEquals("456 Oak Ave", responseDto.addressResponseDto().street());
        assertEquals("Los Angeles", responseDto.addressResponseDto().city());
        assertEquals("CA", responseDto.addressResponseDto().state());
        assertEquals("90001", responseDto.addressResponseDto().zip());

        verify(userRepository, times(1)).findById(userId);
        verify(addressRepository, times(1)).save(any(Address.class));
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void deleteUser_ShouldDeleteUser() {
        // Arrange
        Long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(true);

        // Act
        userService.deleteUser(userId);

        // Assert
        verify(userRepository, times(1)).existsById(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void getAllUsers_ShouldReturnPaginatedUsers() {
        // Arrange
        int page = 0;
        int pageSize = 10;
        String sortDirection = "ASC";

        Address address = new Address();
        address.setStreet("123 Main St");
        address.setCity("New York");
        address.setState("NY");
        address.setZip("10001");

        User user = new User();
        user.setUserId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john@example.com");
        user.setPassword("password123");
        user.setPhone("123-456-7890");
        user.setAddress(address);
        user.setRole(Role.USER);

        Page<User> userPage = new PageImpl<>(Arrays.asList(user));
        when(userRepository.findAll(PageRequest.of(page, pageSize, Sort.by("firstName").ascending())))
            .thenReturn(userPage);

        // Act
        Page<UserResponseDto> responsePage = userService.getAllUsers(page, pageSize, sortDirection);

        // Assert
        assertNotNull(responsePage);
        assertEquals(1, responsePage.getTotalElements());
        UserResponseDto responseDto = responsePage.getContent().get(0);
        assertEquals("John", responseDto.firstName());
        assertEquals("Doe", responseDto.lastName());
        assertEquals("john@example.com", responseDto.email());
        assertEquals("123-456-7890", responseDto.phone());
        assertEquals(Role.USER, responseDto.role());
        assertNotNull(responseDto.addressResponseDto());
        assertEquals("123 Main St", responseDto.addressResponseDto().street());
        assertEquals("New York", responseDto.addressResponseDto().city());
        assertEquals("NY", responseDto.addressResponseDto().state());
        assertEquals("10001", responseDto.addressResponseDto().zip());

        verify(userRepository, times(1)).findAll(PageRequest.of(page, pageSize, Sort.by("firstName").ascending()));
    }
} 