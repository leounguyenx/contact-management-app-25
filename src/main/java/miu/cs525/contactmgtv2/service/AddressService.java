package miu.cs525.contactmgtv2.service;

import miu.cs525.contactmgtv2.dto.request.AddressRequestDto;
import miu.cs525.contactmgtv2.dto.response.AddressResponseDto;

public interface AddressService {

    // Add new address
    AddressResponseDto createAddress(AddressRequestDto addressRequestDto);

    // Update address info
    AddressResponseDto updateAddress(Long addressId, AddressRequestDto addressRequestDto);

}
