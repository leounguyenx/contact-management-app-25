package miu.cs525.contactmgtv2.service;

import miu.cs525.contactmgtv2.dto.request.ContactRequestDto;
import miu.cs525.contactmgtv2.dto.response.ContactResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ContactService {
    // Add new contact
    ContactResponseDto createContact(ContactRequestDto contactRequestDto);

    // Update contact info
    ContactResponseDto updateContact(Long contactId, ContactRequestDto contactRequestDto);

    // Delete a contact
    void deleteContact(Long contactId);

    // Get all contacts of a user
    Page<ContactResponseDto> getAllContactsByUser(Long userId, int page, int pageSize, String sortDirection);

    // Get contact by id
    ContactResponseDto getContactById(Long contactId);
}
