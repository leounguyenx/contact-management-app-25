package miu.cs525.contactmgtv2.repository;

import miu.cs525.contactmgtv2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UserRepository extends JpaRepository<User, Long> {
}
