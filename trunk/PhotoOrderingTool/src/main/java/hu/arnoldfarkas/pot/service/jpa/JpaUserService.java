package hu.arnoldfarkas.pot.service.jpa;

import hu.arnoldfarkas.pot.domain.User;
import hu.arnoldfarkas.pot.repository.UserRepository;
import hu.arnoldfarkas.pot.service.EmailService;
import hu.arnoldfarkas.pot.service.UserService;
import java.util.List;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JpaUserService implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private EmailService mailService;

    @Override
    public User findByName(String username) {
        return repository.findByEmail(username);
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

    @Override
    @Transactional
    public boolean changeAdminRole(long userId) {
        User loggedInUser = findLoggedInUser();
        User user = repository.findOne(userId);
        if (loggedInUser.getId().equals(user.getId())) {
            return user.isAdmin();
        }
        user.setAdmin(!user.isAdmin());
        repository.save(user);
        return user.isAdmin();
    }

    @Transactional
    @Override
    public void generateAndSendPassword(long userId) {
        String password = generatePassword();
        changePassword(userId, password);
        sendPasswordToUser(userId, password);
    }

    private String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(12);
    }

    private void sendPasswordToUser(long userId, String password) {
        User user = repository.findOne(userId);
        
        String mailTitle = "Photo Ordering Tool - Password";
        String mailBody = mailService.generateBody(user, password);
        
        mailService.send(user.getEmail(), mailTitle, mailBody);
    }

    private void changePassword(long userId, String password) {
        User user = repository.findOne(userId);
        user.setPassword(password);
        repository.save(user);
    }
}
