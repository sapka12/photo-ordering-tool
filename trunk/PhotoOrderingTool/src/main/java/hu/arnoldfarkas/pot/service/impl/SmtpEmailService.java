package hu.arnoldfarkas.pot.service.impl;

import hu.arnoldfarkas.pot.domain.User;
import hu.arnoldfarkas.pot.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;

@Service
public class SmtpEmailService implements EmailService {

    private static final String FROM_EMAIL = "farkasarnold1981@gmail.com";

    @Autowired
    private MailSender mailSender;

    @Override
    public String generateBody(User user, String password) {
        StringBuilder builder = new StringBuilder();
        builder.append(user.getEmail());
        builder.append(" / ");
        builder.append(password);
        return builder.toString();
    }

    @Override
    public void send(String toEmailAddress, String mailTitle, String mailBody) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(FROM_EMAIL);
        simpleMailMessage.setTo(toEmailAddress);
        simpleMailMessage.setSubject(mailTitle);
        simpleMailMessage.setText(mailBody);
        mailSender.send(simpleMailMessage);
    }

}
