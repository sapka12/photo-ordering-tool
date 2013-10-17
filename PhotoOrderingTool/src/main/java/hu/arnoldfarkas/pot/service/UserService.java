package hu.arnoldfarkas.pot.service;

import hu.arnoldfarkas.pot.domain.User;

public interface UserService {

    //User login(String username, String password);
    User findByName(String username);
}
