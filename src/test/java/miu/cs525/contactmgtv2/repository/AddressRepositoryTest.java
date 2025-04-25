package miu.cs525.contactmgtv2.repository;

import miu.cs525.contactmgtv2.model.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AddressRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AddressRepository addressRepository;

    private Address address;

    @BeforeEach
    void setUp() {
        // Create and persist a test address
        address = new Address();
        address.setStreet("123 Main St");
        address.setCity("New York");
        address.setState("NY");
        address.setZip("10001");
        address = entityManager.persist(address);

        entityManager.flush();
    }

    @Test
    void findById_ShouldReturnAddress() {
        // Act
        Optional<Address> foundAddress = addressRepository.findById(address.getAddressId());

        // Assert
        assertTrue(foundAddress.isPresent());
        assertEquals(address.getAddressId(), foundAddress.get().getAddressId());
        assertEquals("123 Main St", foundAddress.get().getStreet());
        assertEquals("New York", foundAddress.get().getCity());
        assertEquals("NY", foundAddress.get().getState());
        assertEquals("10001", foundAddress.get().getZip());
    }

    @Test
    void findById_WhenAddressNotFound_ShouldReturnEmptyOptional() {
        // Act
        Optional<Address> foundAddress = addressRepository.findById(999L);

        // Assert
        assertFalse(foundAddress.isPresent());
    }

    @Test
    void saveAddress_ShouldPersistAddress() {
        // Arrange
        Address newAddress = new Address();
        newAddress.setStreet("456 Oak Ave");
        newAddress.setCity("Boston");
        newAddress.setState("MA");
        newAddress.setZip("02108");

        // Act
        Address savedAddress = addressRepository.save(newAddress);
        entityManager.flush();
        entityManager.clear();

        // Assert
        assertNotNull(savedAddress.getAddressId());
        Optional<Address> foundAddress = addressRepository.findById(savedAddress.getAddressId());
        assertTrue(foundAddress.isPresent());
        assertEquals("456 Oak Ave", foundAddress.get().getStreet());
        assertEquals("Boston", foundAddress.get().getCity());
        assertEquals("MA", foundAddress.get().getState());
        assertEquals("02108", foundAddress.get().getZip());
    }

    @Test
    void updateAddress_ShouldUpdateAddress() {
        // Arrange
        address.setStreet("789 Pine St");
        address.setCity("Chicago");
        address.setState("IL");
        address.setZip("60601");

        // Act
        Address updatedAddress = addressRepository.save(address);
        entityManager.flush();
        entityManager.clear();

        // Assert
        Optional<Address> foundAddress = addressRepository.findById(address.getAddressId());
        assertTrue(foundAddress.isPresent());
        assertEquals("789 Pine St", foundAddress.get().getStreet());
        assertEquals("Chicago", foundAddress.get().getCity());
        assertEquals("IL", foundAddress.get().getState());
        assertEquals("60601", foundAddress.get().getZip());
    }

    @Test
    void deleteAddress_ShouldRemoveAddress() {
        // Act
        addressRepository.delete(address);
        entityManager.flush();
        entityManager.clear();

        // Assert
        Optional<Address> foundAddress = addressRepository.findById(address.getAddressId());
        assertFalse(foundAddress.isPresent());
    }

    @Test
    void findAll_ShouldReturnAllAddresses() {
        // Arrange
        Address secondAddress = new Address();
        secondAddress.setStreet("456 Oak Ave");
        secondAddress.setCity("Boston");
        secondAddress.setState("MA");
        secondAddress.setZip("02108");
        entityManager.persist(secondAddress);
        entityManager.flush();
        entityManager.clear();

        // Act
        Iterable<Address> addresses = addressRepository.findAll();

        // Assert
        assertNotNull(addresses);
        int count = 0;
        for (Address addr : addresses) {
            count++;
            assertNotNull(addr.getAddressId());
            assertNotNull(addr.getStreet());
            assertNotNull(addr.getCity());
            assertNotNull(addr.getState());
            assertNotNull(addr.getZip());
        }
        assertEquals(2, count);
    }
} 