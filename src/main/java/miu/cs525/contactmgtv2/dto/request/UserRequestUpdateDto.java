package miu.cs525.contactmgtv2.dto.request;

import miu.cs525.contactmgtv2.model.Role;

public record UserRequestUpdateDto(
        String firstName,
        String lastName,
        String email,
        String password,
        String phone,
        AddressRequestDto addressRequestDto
) {}
