package miu.cs525.contactmgtv2.service;

import miu.cs525.contactmgtv2.dto.request.AddressRequestDto;
import miu.cs525.contactmgtv2.dto.response.AddressResponseDto;
import miu.cs525.contactmgtv2.exception.ResourceNotFoundException;
import miu.cs525.contactmgtv2.model.Address;
import miu.cs525.contactmgtv2.repository.AddressRepository;
import miu.cs525.contactmgtv2.service.impl.AddressServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AddressServiceTest {

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private AddressServiceImpl addressService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createAddress_ShouldReturnAddressResponseDto() {
        // Arrange
        AddressRequestDto requestDto = new AddressRequestDto(
            "123 Main St",
            "New York",
            "NY",
            "10001"
        );

        Address address = new Address(
            "123 Main St",
            "New York",
            "NY",
            "10001"
        );
        address.setAddressId(1L);

        when(addressRepository.save(any(Address.class))).thenReturn(address);

        // Act
        AddressResponseDto responseDto = addressService.createAddress(requestDto);

        // Assert
        assertNotNull(responseDto);
        assertEquals("123 Main St", responseDto.street());
        assertEquals("New York", responseDto.city());
        assertEquals("NY", responseDto.state());
        assertEquals("10001", responseDto.zip());

        verify(addressRepository, times(1)).save(any(Address.class));
    }

    @Test
    void updateAddress_ShouldReturnUpdatedAddressResponseDto() {
        // Arrange
        Long addressId = 1L;
        AddressRequestDto requestDto = new AddressRequestDto(
            "456 Oak Ave",
            "Los Angeles",
            "CA",
            "90001"
        );

        Address existingAddress = new Address(
            "123 Main St",
            "New York",
            "NY",
            "10001"
        );
        existingAddress.setAddressId(addressId);

        Address updatedAddress = new Address(
            "456 Oak Ave",
            "Los Angeles",
            "CA",
            "90001"
        );
        updatedAddress.setAddressId(addressId);

        when(addressRepository.findById(addressId)).thenReturn(Optional.of(existingAddress));
        when(addressRepository.save(any(Address.class))).thenReturn(updatedAddress);

        // Act
        AddressResponseDto responseDto = addressService.updateAddress(addressId, requestDto);

        // Assert
        assertNotNull(responseDto);
        assertEquals("456 Oak Ave", responseDto.street());
        assertEquals("Los Angeles", responseDto.city());
        assertEquals("CA", responseDto.state());
        assertEquals("90001", responseDto.zip());

        verify(addressRepository, times(1)).findById(addressId);
        verify(addressRepository, times(1)).save(any(Address.class));
    }

    @Test
    void updateAddress_WhenAddressNotFound_ShouldThrowResourceNotFoundException() {
        // Arrange
        Long addressId = 1L;
        AddressRequestDto requestDto = new AddressRequestDto(
            "456 Oak Ave",
            "Los Angeles",
            "CA",
            "90001"
        );

        when(addressRepository.findById(addressId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            addressService.updateAddress(addressId, requestDto);
        });

        verify(addressRepository, times(1)).findById(addressId);
        verify(addressRepository, never()).save(any(Address.class));
    }
} 