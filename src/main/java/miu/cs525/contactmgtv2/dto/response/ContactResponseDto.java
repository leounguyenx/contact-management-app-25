package miu.cs525.contactmgtv2.dto.response;

public record ContactResponseDto(
        Long contactId,
        String name,
        String email,
        String phone,
        String note,
        String tag,
        AddressResponseDto addressResponseDto
) {}
