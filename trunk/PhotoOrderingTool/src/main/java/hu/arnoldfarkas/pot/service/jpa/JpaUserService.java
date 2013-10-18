package hu.arnoldfarkas.pot.service.jpa;

import hu.arnoldfarkas.pot.domain.User;
import hu.arnoldfarkas.pot.repository.UserRepository;
import hu.arnoldfarkas.pot.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class JpaUserService implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public User findByName(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public User findLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return findByName(auth.getName());
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }
}
