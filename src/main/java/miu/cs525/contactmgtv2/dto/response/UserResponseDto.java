package miu.cs525.contactmgtv2.dto.response;

import miu.cs525.contactmgtv2.model.Role;

public record UserResponseDto(
        String firstName,
        String lastName,
        String email,
        String phone,
        AddressResponseDto addressResponseDto,
        Role role
) {}
