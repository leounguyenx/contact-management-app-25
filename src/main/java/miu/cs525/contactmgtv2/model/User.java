package miu.cs525.contactmgtv2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long userId;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    private String phone;

    // A user has one address
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", nullable = true)
    Address address;

    // A user has one role
    // Use @Enumerated to save enum value as String to DB
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    Role role;

    // A user has many contacts
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Contact> contacts;


    // Full constructor
    public User(String firstName, String lastName, String email, String password, String phone, Address address, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.role = role;
    }

    // Minimum Constructor
//    public User(String firstName, String lastName, String email, String password, Role role) {
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.email = email;
//        this.password = password;
//        this.role = role;
//    }

}
