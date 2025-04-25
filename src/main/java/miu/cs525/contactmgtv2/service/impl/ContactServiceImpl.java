package miu.cs525.contactmgtv2.service.impl;

import lombok.RequiredArgsConstructor;
import miu.cs525.contactmgtv2.dto.request.ContactRequestDto;
import miu.cs525.contactmgtv2.dto.response.AddressResponseDto;
import miu.cs525.contactmgtv2.dto.response.ContactResponseDto;
import miu.cs525.contactmgtv2.model.Address;
import miu.cs525.contactmgtv2.model.Contact;
import miu.cs525.contactmgtv2.model.User;
import miu.cs525.contactmgtv2.repository.AddressRepository;
import miu.cs525.contactmgtv2.repository.ContactRepository;
import miu.cs525.contactmgtv2.repository.UserRepository;
import miu.cs525.contactmgtv2.service.ContactService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ContactServiceImpl implements ContactService {
    private final ContactRepository contactRepository;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    @Override
    public ContactResponseDto createContact(ContactRequestDto contactRequestDto) {
        Address address = new Address();
        address.setStreet(contactRequestDto.addressRequestDto().street());
        address.setCity(contactRequestDto.addressRequestDto().city());
        address.setState(contactRequestDto.addressRequestDto().state());
        address.setZip(contactRequestDto.addressRequestDto().zip());
        addressRepository.save(address);

        Contact contact = new Contact(
                contactRequestDto.name(),
                contactRequestDto.email(),
                contactRequestDto.phone(),
                contactRequestDto.note(),
                contactRequestDto.tag(),
                address
        );

        User user = userRepository.findById(contactRequestDto.userId()).orElseThrow(() -> new RuntimeException("User not found"));
        contact.setUser(user);

        Contact saved = contactRepository.save(contact);

        AddressResponseDto addressDto = new AddressResponseDto(
                saved.getAddress().getStreet(),
                saved.getAddress().getCity(),
                saved.getAddress().getState(),
                saved.getAddress().getZip()
        );

        return new ContactResponseDto(
                saved.getContactId(),
                saved.getName(),
                saved.getEmail(),
                saved.getPhone(),
                saved.getNote(),
                saved.getTag(),
                addressDto
        );
    }

    @Override
    public ContactResponseDto updateContact(Long contactId, ContactRequestDto contactRequestDto) {
        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new RuntimeException("Contact not found"));

        contact.setName(contactRequestDto.name());
        contact.setEmail(contactRequestDto.email());
        contact.setPhone(contactRequestDto.phone());
        contact.setNote(contactRequestDto.note());
        contact.setTag(contactRequestDto.tag());

        Address address = contact.getAddress();
        address.setStreet(contactRequestDto.addressRequestDto().street());
        address.setCity(contactRequestDto.addressRequestDto().city());
        address.setState(contactRequestDto.addressRequestDto().state());
        address.setZip(contactRequestDto.addressRequestDto().zip());
        addressRepository.save(address);

        contactRepository.save(contact);

        AddressResponseDto addressDto = new AddressResponseDto(
                address.getStreet(),
                address.getCity(),
                address.getState(),
                address.getZip()
        );

        return new ContactResponseDto(
                contact.getContactId(),
                contact.getName(),
                contact.getEmail(),
                contact.getPhone(),
                contact.getNote(),
                contact.getTag(),
                addressDto
        );
    }

    @Override
    public void deleteContact(Long contactId) {
        if (!contactRepository.existsById(contactId)) {
            throw new RuntimeException("Contact not found");
        }
        contactRepository.deleteById(contactId);
    }

    @Override
    public Page<ContactResponseDto> getAllContactsByUser(Long userId, int page, int pageSize, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase("desc")
                ? Sort.by("name").descending()
                : Sort.by("name").ascending();

        Pageable pageable = PageRequest.of(page, pageSize, sort);
        Page<Contact> contactPage = contactRepository.findAllByUserUserId(userId, pageable);

        return contactPage.map(contact -> {
            Address address = contact.getAddress();
            AddressResponseDto addressDto = new AddressResponseDto(
                    address.getStreet(),
                    address.getCity(),
                    address.getState(),
                    address.getZip()
            );
            return new ContactResponseDto(
                    contact.getContactId(),
                    contact.getName(),
                    contact.getEmail(),
                    contact.getPhone(),
                    contact.getNote(),
                    contact.getTag(),
                    addressDto
            );
        });
    }

    @Override
    public ContactResponseDto getContactById(Long contactId) {
        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new RuntimeException("Contact not found"));
        Address address = contact.getAddress();
        AddressResponseDto addressDto = new AddressResponseDto(
                address.getStreet(),
                address.getCity(),
                address.getState(),
                address.getZip()
        );

        return new ContactResponseDto(
                contact.getContactId(),
                contact.getName(),
                contact.getEmail(),
                contact.getPhone(),
                contact.getNote(),
                contact.getTag(),
                addressDto
        );
    }
}
