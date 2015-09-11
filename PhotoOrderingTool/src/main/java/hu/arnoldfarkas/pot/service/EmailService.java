package hu.arnoldfarkas.pot.service;

import hu.arnoldfarkas.pot.domain.model.User;

public interface EmailService {

    public String generateBody(User user, String password);

    public void send(String toEmailAddress, String mailTitle, String mailBody);
    
}
