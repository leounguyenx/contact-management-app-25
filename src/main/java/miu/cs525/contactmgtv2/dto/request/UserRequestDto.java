package miu.cs525.contactmgtv2.dto.request;

import miu.cs525.contactmgtv2.model.Role;

public record UserRequestDto(
        String firstName,
        String lastName,
        String email,
        String password,
        String phone,
        AddressRequestDto addressRequestDto,
        Role role
) {}
