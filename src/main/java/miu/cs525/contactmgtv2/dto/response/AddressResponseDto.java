package miu.cs525.contactmgtv2.dto.response;

public record AddressResponseDto(
        String street,
        String city,
        String state,
        String zip
) {
}
