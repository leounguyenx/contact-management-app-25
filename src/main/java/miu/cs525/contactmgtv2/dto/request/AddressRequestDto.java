package miu.cs525.contactmgtv2.dto.request;

public record AddressRequestDto(
        String street,
        String city,
        String state,
        String zip
) {
}
