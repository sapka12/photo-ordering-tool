package hu.arnoldfarkas.pot.service;

import hu.arnoldfarkas.pot.domain.User;
import java.util.List;

public interface UserService {

    User findByName(String username);

    User findLoggedInUser();
    
    List<User> findAll();
}
