package miu.cs525.contactmgtv2.service.impl;

import lombok.RequiredArgsConstructor;
import miu.cs525.contactmgtv2.dto.request.UserRequestDto;
import miu.cs525.contactmgtv2.dto.request.UserRequestUpdateDto;
import miu.cs525.contactmgtv2.dto.response.AddressResponseDto;
import miu.cs525.contactmgtv2.dto.response.UserResponseDto;
import miu.cs525.contactmgtv2.exception.ResourceNotFoundException;
import miu.cs525.contactmgtv2.model.Address;
import miu.cs525.contactmgtv2.model.User;
import miu.cs525.contactmgtv2.repository.AddressRepository;
import miu.cs525.contactmgtv2.repository.UserRepository;
import miu.cs525.contactmgtv2.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        // Map Address DTO to Address entity
        Address address = new Address();
        address.setStreet(userRequestDto.addressRequestDto().street());
        address.setCity(userRequestDto.addressRequestDto().city());
        address.setState(userRequestDto.addressRequestDto().state());
        address.setZip(userRequestDto.addressRequestDto().zip());
        // Save address to get managed entity
        addressRepository.save(address);

        // Create new User
        User user = new User();
        user.setFirstName(userRequestDto.firstName());
        user.setLastName(userRequestDto.lastName());
        user.setEmail(userRequestDto.email());
        user.setPassword(userRequestDto.password());
        user.setPhone(userRequestDto.phone());
        user.setAddress(address);
        user.setRole(userRequestDto.role());

        User savedUser = userRepository.save(user);

        // Map to UserResponseDto
        AddressResponseDto addressResponseDto = new AddressResponseDto(
                address.getStreet(),
                address.getCity(),
                address.getState(),
                address.getZip()
        );

        return new UserResponseDto(
                savedUser.getFirstName(),
                savedUser.getLastName(),
                savedUser.getEmail(),
                savedUser.getPhone(),
                addressResponseDto,
                savedUser.getRole()
        );
    }

    @Override
    public UserResponseDto updateUser(Long userId, UserRequestUpdateDto userRequestUpdateDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        // Update basic user fields
        user.setFirstName(userRequestUpdateDto.firstName());
        user.setLastName(userRequestUpdateDto.lastName());
        user.setEmail(userRequestUpdateDto.email());
        user.setPassword(userRequestUpdateDto.password());
        user.setPhone(userRequestUpdateDto.phone());

        // Update or create new address
        Address address = user.getAddress();
        if (address == null) {
            address = new Address();
        }

        address.setStreet(userRequestUpdateDto.addressRequestDto().street());
        address.setCity(userRequestUpdateDto.addressRequestDto().city());
        address.setState(userRequestUpdateDto.addressRequestDto().state());
        address.setZip(userRequestUpdateDto.addressRequestDto().zip());

        addressRepository.save(address);
        user.setAddress(address);

        User updatedUser = userRepository.save(user);

        // Build response DTO
        AddressResponseDto addressResponseDto = new AddressResponseDto(
                address.getStreet(),
                address.getCity(),
                address.getState(),
                address.getZip()
        );

        return new UserResponseDto(
                updatedUser.getFirstName(),
                updatedUser.getLastName(),
                updatedUser.getEmail(),
                updatedUser.getPhone(),
                addressResponseDto,
                updatedUser.getRole()
        );
    }

    @Override
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with ID: " + userId);
        }
        userRepository.deleteById(userId);
    }

    @Override
    public Page<UserResponseDto> getAllUsers(int page, int pageSize, String sortDirection) {
        // Default to ascending order if not specified
        // Sort by firstName
        Sort sort = sortDirection.equalsIgnoreCase("desc") ? Sort.by("firstName").descending() : Sort.by("firstName").ascending();
        Pageable pageable = PageRequest.of(page, pageSize, sort);

        Page<User> usersPage = userRepository.findAll(pageable);

        return usersPage.map(user -> {
            Address address = user.getAddress();
            AddressResponseDto addressResponseDto = new AddressResponseDto(
                    address.getStreet(),
                    address.getCity(),
                    address.getState(),
                    address.getZip()
            );

            return new UserResponseDto(
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getPhone(),
                    addressResponseDto,
                    user.getRole()
            );
        });
    }

    @Override
    public UserResponseDto getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        Address address = user.getAddress();
        AddressResponseDto addressResponseDto = new AddressResponseDto(
                address.getStreet(),
                address.getCity(),
                address.getState(),
                address.getZip()
        );

        return new UserResponseDto(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhone(),
                addressResponseDto,
                user.getRole()
        );
    }
}
