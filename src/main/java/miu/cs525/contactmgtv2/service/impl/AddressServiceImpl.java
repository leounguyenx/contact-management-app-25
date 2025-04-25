package miu.cs525.contactmgtv2.service.impl;

import lombok.RequiredArgsConstructor;
import miu.cs525.contactmgtv2.dto.request.AddressRequestDto;
import miu.cs525.contactmgtv2.dto.response.AddressResponseDto;
import miu.cs525.contactmgtv2.exception.ResourceNotFoundException;
import miu.cs525.contactmgtv2.model.Address;
import miu.cs525.contactmgtv2.repository.AddressRepository;
import miu.cs525.contactmgtv2.service.AddressService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    @Override
    public AddressResponseDto createAddress(AddressRequestDto addressRequestDto) {
        Address address = new Address(addressRequestDto.street(), addressRequestDto.city(), addressRequestDto.state(), addressRequestDto.zip());
        Address saved = addressRepository.save(address);
        return toDto(saved);
    }

    @Override
    public AddressResponseDto updateAddress(Long addressId, AddressRequestDto addressRequestDto) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with ID: " + addressId));
        address.setStreet(addressRequestDto.street());
        address.setCity(addressRequestDto.city());
        address.setState(addressRequestDto.state());
        address.setZip(addressRequestDto.zip());
        Address updated = addressRepository.save(address);
        return toDto(updated);
    }
    private AddressResponseDto toDto(Address address) {
        return new AddressResponseDto(
                address.getStreet(),
                address.getCity(),
                address.getState(),
                address.getZip()
        );
    }
}
