package miu.cs525.contactmgtv2.service;

import miu.cs525.contactmgtv2.dto.request.AddressRequestDto;
import miu.cs525.contactmgtv2.dto.request.ContactRequestDto;
import miu.cs525.contactmgtv2.dto.response.AddressResponseDto;
import miu.cs525.contactmgtv2.dto.response.ContactResponseDto;
import miu.cs525.contactmgtv2.exception.ResourceNotFoundException;
import miu.cs525.contactmgtv2.model.Address;
import miu.cs525.contactmgtv2.model.Contact;
import miu.cs525.contactmgtv2.model.User;
import miu.cs525.contactmgtv2.repository.AddressRepository;
import miu.cs525.contactmgtv2.repository.ContactRepository;
import miu.cs525.contactmgtv2.repository.UserRepository;
import miu.cs525.contactmgtv2.service.impl.ContactServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ContactServiceTest {

    @Mock
    private ContactRepository contactRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private ContactServiceImpl contactService;

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
            "123-456-7890",
            "Test note",
            "work",
            new AddressRequestDto("123 Main St", "New York", "NY", "10001"),
            1L
        );

        User user = new User();
        user.setUserId(1L);

        Address address = new Address();
        address.setStreet("123 Main St");
        address.setCity("New York");
        address.setState("NY");
        address.setZip("10001");

        Contact contact = new Contact();
        contact.setName("John Doe");
        contact.setEmail("john@example.com");
        contact.setPhone("123-456-7890");
        contact.setNote("Test note");
        contact.setTag("work");
        contact.setAddress(address);
        contact.setUser(user);

        Contact savedContact = new Contact();
        savedContact.setContactId(1L);
        savedContact.setName("John Doe");
        savedContact.setEmail("john@example.com");
        savedContact.setPhone("123-456-7890");
        savedContact.setNote("Test note");
        savedContact.setTag("work");
        savedContact.setAddress(address);
        savedContact.setUser(user);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(addressRepository.save(any(Address.class))).thenReturn(address);
        when(contactRepository.save(any(Contact.class))).thenReturn(savedContact);

        // Act
        ContactResponseDto responseDto = contactService.createContact(requestDto);

        // Assert
        assertNotNull(responseDto);
        assertEquals(1L, responseDto.contactId());
        assertEquals("John Doe", responseDto.name());
        assertEquals("john@example.com", responseDto.email());
        assertEquals("123-456-7890", responseDto.phone());
        assertEquals("Test note", responseDto.note());
        assertEquals("work", responseDto.tag());
        assertNotNull(responseDto.addressResponseDto());
        assertEquals("123 Main St", responseDto.addressResponseDto().street());
        assertEquals("New York", responseDto.addressResponseDto().city());
        assertEquals("NY", responseDto.addressResponseDto().state());
        assertEquals("10001", responseDto.addressResponseDto().zip());

        verify(userRepository, times(1)).findById(1L);
        verify(addressRepository, times(1)).save(any(Address.class));
        verify(contactRepository, times(1)).save(any(Contact.class));
    }

    @Test
    void getContactById_ShouldReturnContact() {
        // Arrange
        Long contactId = 1L;
        User user = new User();
        user.setUserId(1L);

        Address address = new Address();
        address.setStreet("123 Main St");
        address.setCity("New York");
        address.setState("NY");
        address.setZip("10001");

        Contact contact = new Contact();
        contact.setContactId(contactId);
        contact.setName("John Doe");
        contact.setEmail("john@example.com");
        contact.setPhone("123-456-7890");
        contact.setNote("Test note");
        contact.setTag("work");
        contact.setAddress(address);
        contact.setUser(user);

        when(contactRepository.findById(contactId)).thenReturn(Optional.of(contact));

        // Act
        ContactResponseDto responseDto = contactService.getContactById(contactId);

        // Assert
        assertNotNull(responseDto);
        assertEquals(contactId, responseDto.contactId());
        assertEquals("John Doe", responseDto.name());
        assertEquals("john@example.com", responseDto.email());
        assertEquals("123-456-7890", responseDto.phone());
        assertEquals("Test note", responseDto.note());
        assertEquals("work", responseDto.tag());
        assertNotNull(responseDto.addressResponseDto());
        assertEquals("123 Main St", responseDto.addressResponseDto().street());
        assertEquals("New York", responseDto.addressResponseDto().city());
        assertEquals("NY", responseDto.addressResponseDto().state());
        assertEquals("10001", responseDto.addressResponseDto().zip());

        verify(contactRepository, times(1)).findById(contactId);
    }

    @Test
    void updateContact_ShouldReturnUpdatedContact() {
        // Arrange
        Long contactId = 1L;
        ContactRequestDto requestDto = new ContactRequestDto(
            "John Doe Updated",
            "john.updated@example.com",
            "987-654-3210",
            "Updated note",
            "personal",
            new AddressRequestDto("456 Oak St", "Boston", "MA", "02108"),
            1L
        );

        User user = new User();
        user.setUserId(1L);

        Address existingAddress = new Address();
        existingAddress.setStreet("123 Main St");
        existingAddress.setCity("New York");
        existingAddress.setState("NY");
        existingAddress.setZip("10001");

        Address updatedAddress = new Address();
        updatedAddress.setStreet("456 Oak St");
        updatedAddress.setCity("Boston");
        updatedAddress.setState("MA");
        updatedAddress.setZip("02108");

        Contact existingContact = new Contact();
        existingContact.setContactId(contactId);
        existingContact.setName("John Doe");
        existingContact.setEmail("john@example.com");
        existingContact.setPhone("123-456-7890");
        existingContact.setNote("Test note");
        existingContact.setTag("work");
        existingContact.setAddress(existingAddress);
        existingContact.setUser(user);

        Contact updatedContact = new Contact();
        updatedContact.setContactId(contactId);
        updatedContact.setName("John Doe Updated");
        updatedContact.setEmail("john.updated@example.com");
        updatedContact.setPhone("987-654-3210");
        updatedContact.setNote("Updated note");
        updatedContact.setTag("personal");
        updatedContact.setAddress(updatedAddress);
        updatedContact.setUser(user);

        when(contactRepository.findById(contactId)).thenReturn(Optional.of(existingContact));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(addressRepository.save(any(Address.class))).thenReturn(updatedAddress);
        when(contactRepository.save(any(Contact.class))).thenReturn(updatedContact);

        // Act
        ContactResponseDto responseDto = contactService.updateContact(contactId, requestDto);

        // Assert
        assertNotNull(responseDto);
        assertEquals(contactId, responseDto.contactId());
        assertEquals("John Doe Updated", responseDto.name());
        assertEquals("john.updated@example.com", responseDto.email());
        assertEquals("987-654-3210", responseDto.phone());
        assertEquals("Updated note", responseDto.note());
        assertEquals("personal", responseDto.tag());
        assertNotNull(responseDto.addressResponseDto());
        assertEquals("456 Oak St", responseDto.addressResponseDto().street());
        assertEquals("Boston", responseDto.addressResponseDto().city());
        assertEquals("MA", responseDto.addressResponseDto().state());
        assertEquals("02108", responseDto.addressResponseDto().zip());

        verify(contactRepository, times(1)).findById(contactId);
        verify(userRepository, times(1)).findById(1L);
        verify(addressRepository, times(1)).save(any(Address.class));
        verify(contactRepository, times(1)).save(any(Contact.class));
    }

    @Test
    void deleteContact_ShouldDeleteContact() {
        // Arrange
        Long contactId = 1L;
        when(contactRepository.existsById(contactId)).thenReturn(true);

        // Act
        contactService.deleteContact(contactId);

        // Assert
        verify(contactRepository, times(1)).existsById(contactId);
        verify(contactRepository, times(1)).deleteById(contactId);
    }

    @Test
    void getAllContactsByUser_ShouldReturnPaginatedContacts() {
        // Arrange
        Long userId = 1L;
        int page = 0;
        int pageSize = 10;
        String sortDirection = "ASC";

        User user = new User();
        user.setUserId(userId);

        Address address = new Address();
        address.setStreet("123 Main St");
        address.setCity("New York");
        address.setState("NY");
        address.setZip("10001");

        Contact contact = new Contact();
        contact.setContactId(1L);
        contact.setName("John Doe");
        contact.setEmail("john@example.com");
        contact.setPhone("123-456-7890");
        contact.setNote("Test note");
        contact.setTag("work");
        contact.setAddress(address);
        contact.setUser(user);

        Page<Contact> contactPage = new PageImpl<>(Arrays.asList(contact));
        when(contactRepository.findAllByUserUserId(userId, PageRequest.of(page, pageSize, Sort.Direction.fromString(sortDirection), "name")))
            .thenReturn(contactPage);

        // Act
        Page<ContactResponseDto> responsePage = contactService.getAllContactsByUser(userId, page, pageSize, sortDirection);

        // Assert
        assertNotNull(responsePage);
        assertEquals(1, responsePage.getTotalElements());
        ContactResponseDto responseDto = responsePage.getContent().get(0);
        assertEquals(1L, responseDto.contactId());
        assertEquals("John Doe", responseDto.name());
        assertEquals("john@example.com", responseDto.email());
        assertEquals("123-456-7890", responseDto.phone());
        assertEquals("Test note", responseDto.note());
        assertEquals("work", responseDto.tag());
        assertNotNull(responseDto.addressResponseDto());
        assertEquals("123 Main St", responseDto.addressResponseDto().street());
        assertEquals("New York", responseDto.addressResponseDto().city());
        assertEquals("NY", responseDto.addressResponseDto().state());
        assertEquals("10001", responseDto.addressResponseDto().zip());

        verify(contactRepository, times(1))
            .findAllByUserUserId(userId, PageRequest.of(page, pageSize, Sort.Direction.fromString(sortDirection), "name"));
    }

    @Test
    void getContactById_WhenContactNotFound_ShouldThrowResourceNotFoundException() {
        // Arrange
        Long contactId = 1L;
        when(contactRepository.findById(contactId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> contactService.getContactById(contactId));
        verify(contactRepository, times(1)).findById(contactId);
    }

    @Test
    void deleteContact_WhenContactNotFound_ShouldThrowResourceNotFoundException() {
        // Arrange
        Long contactId = 1L;
        when(contactRepository.existsById(contactId)).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> contactService.deleteContact(contactId));
        verify(contactRepository, times(1)).existsById(contactId);
        verify(contactRepository, never()).deleteById(contactId);
    }
} 