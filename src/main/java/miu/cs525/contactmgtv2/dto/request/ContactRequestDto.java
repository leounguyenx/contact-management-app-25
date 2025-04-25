package miu.cs525.contactmgtv2.dto.request;

public record ContactRequestDto(
        String name,
        String email,
        String phone,
        String note,
        String tag,
        AddressRequestDto addressRequestDto,
        Long userId
) {}
