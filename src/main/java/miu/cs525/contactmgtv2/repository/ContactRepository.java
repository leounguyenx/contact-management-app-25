package miu.cs525.contactmgtv2.repository;

import miu.cs525.contactmgtv2.model.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    Page<Contact> findAllByUserUserId(Long userId, Pageable pageable);

}
