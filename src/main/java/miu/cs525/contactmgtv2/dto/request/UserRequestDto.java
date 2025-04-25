package miu.cs525.contactmgtv2.dto.request;

import jakarta.validation.constraints.NotBlank;
import miu.cs525.contactmgtv2.model.Role;

public record UserRequestDto(
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotBlank String email,
        @NotBlank String password,
        String phone,
        AddressRequestDto addressRequestDto,
        @NotBlank Role role
) {}
