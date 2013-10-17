package hu.arnoldfarkas.pot.repository;

import hu.arnoldfarkas.pot.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
