package miu.cs525.contactmgtv2.controller;

import lombok.RequiredArgsConstructor;
import miu.cs525.contactmgtv2.dto.request.ContactRequestDto;
import miu.cs525.contactmgtv2.dto.response.ContactResponseDto;
import miu.cs525.contactmgtv2.service.ContactService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/contacts")
public class ContactController {

    private final ContactService contactService;

    // Create a new contact
    @PostMapping
    public ResponseEntity<ContactResponseDto> createContact(@RequestBody ContactRequestDto dto) {
        return new ResponseEntity<>(contactService.createContact(dto), HttpStatus.CREATED);
    }

    // Get contact by id
    @GetMapping("/{id}")
    public ResponseEntity<ContactResponseDto> getContactById(@PathVariable Long id) {
        return ResponseEntity.ok(contactService.getContactById(id));
    }

    // Update a contact info by contactId
    @PutMapping("/{id}")
    public ResponseEntity<ContactResponseDto> updateContactInfo(@PathVariable Long id, @RequestBody ContactRequestDto dto) {
        return ResponseEntity.ok(contactService.updateContact(id, dto));
    }

    // Delete a contact
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        contactService.deleteContact(id);
        return ResponseEntity.noContent().build();
    }

    // Get all contacts of a user => UserController
}
