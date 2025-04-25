package miu.cs525.contactmgtv2.dto.request;
import jakarta.validation.constraints.NotBlank;
public record AddressRequestDto(
        @NotBlank String street,
        @NotBlank String city,
        @NotBlank String state,
        @NotBlank String zip
) {
}
