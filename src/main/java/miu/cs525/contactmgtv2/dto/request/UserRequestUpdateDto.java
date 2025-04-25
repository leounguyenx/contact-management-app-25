package miu.cs525.contactmgtv2.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UserRequestUpdateDto(
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotBlank String email,
        @NotBlank String password,
        String phone,
        AddressRequestDto addressRequestDto
) {}
