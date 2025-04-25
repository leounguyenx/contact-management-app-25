package miu.cs525.contactmgtv2.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "contact")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long contactId;
    @Column(nullable = false)
    private String name;
    private String email;
    @Column(nullable = false)
    private String phone;
    private String note;
    private String tag;

    // A contact has one address
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", nullable = true)
    Address address;

    // Many contacts belong to a user
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Full constructor
    public Contact(String name, String email, String phone, String note, String tag, Address address) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.note = note;
        this.tag = tag;
        this.address = address;
    }
}
