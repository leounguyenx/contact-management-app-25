package miu.cs525.contactmgtv2.controller;

import miu.cs525.contactmgtv2.dto.request.AddressRequestDto;
import miu.cs525.contactmgtv2.dto.request.ContactRequestDto;
import miu.cs525.contactmgtv2.dto.response.AddressResponseDto;
import miu.cs525.contactmgtv2.dto.response.ContactResponseDto;
import miu.cs525.contactmgtv2.service.ContactService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ContactControllerTest {

    @Mock
    private ContactService contactService;

    @InjectMocks
    private ContactController contactController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createContact_ShouldReturnCreatedContact() {
        // Arrange
        ContactRequestDto requestDto = new ContactRequestDto(
            "John Doe",
            "john@example.com",
            "1234567890",
            "Test note",
            "work",
            new AddressRequestDto("123 Main St", "New York", "NY", "10001"),
            1L
        );
        ContactResponseDto responseDto = new ContactResponseDto(
            1L,
            "John Doe",
            "john@example.com",
            "1234567890",
            "Test note",
            "work",
            new AddressResponseDto("123 Main St", "New York", "NY", "10001")
        );
        when(contactService.createContact(requestDto)).thenReturn(responseDto);

        // Act
        ResponseEntity<ContactResponseDto> response = contactController.createContact(requestDto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
        verify(contactService, times(1)).createContact(requestDto);
    }

    @Test
    void getContactById_ShouldReturnContact() {
        // Arrange
        Long contactId = 1L;
        ContactResponseDto responseDto = new ContactResponseDto(
            contactId,
            "John Doe",
            "john@example.com",
            "1234567890",
            "Test note",
            "work",
            new AddressResponseDto("123 Main St", "New York", "NY", "10001")
        );
        when(contactService.getContactById(contactId)).thenReturn(responseDto);

        // Act
        ResponseEntity<ContactResponseDto> response = contactController.getContactById(contactId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
        verify(contactService, times(1)).getContactById(contactId);
    }

    @Test
    void updateContactInfo_ShouldReturnUpdatedContact() {
        // Arrange
        Long contactId = 1L;
        ContactRequestDto requestDto = new ContactRequestDto(
            "John Doe",
            "john@example.com",
            "1234567890",
            "Test note",
            "work",
            new AddressRequestDto("123 Main St", "New York", "NY", "10001"),
            1L
        );
        ContactResponseDto responseDto = new ContactResponseDto(
            contactId,
            "John Doe",
            "john@example.com",
            "1234567890",
            "Test note",
            "work",
            new AddressResponseDto("123 Main St", "New York", "NY", "10001")
        );
        when(contactService.updateContact(contactId, requestDto)).thenReturn(responseDto);

        // Act
        ResponseEntity<ContactResponseDto> response = contactController.updateContactInfo(contactId, requestDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
        verify(contactService, times(1)).updateContact(contactId, requestDto);
    }

    @Test
    void deleteContact_ShouldReturnNoContent() {
        // Arrange
        Long contactId = 1L;
        doNothing().when(contactService).deleteContact(contactId);

        // Act
        ResponseEntity<Void> response = contactController.deleteContact(contactId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(contactService, times(1)).deleteContact(contactId);
    }
} 