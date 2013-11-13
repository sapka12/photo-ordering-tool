package hu.arnoldfarkas.pot.service.impl;

import hu.arnoldfarkas.pot.domain.User;
import hu.arnoldfarkas.pot.service.EmailService;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

@Service
public class SmtpEmailService implements EmailService {

    private static final String FROM_EMAIL = "farkasarnold1981@gmail.com";

    @Autowired
    private JavaMailSenderImpl mailSender;

    @Autowired
    private HttpServletRequest request;

    @Override
    public String generateBody(User user, String password) {
        StringBuilder builder = new StringBuilder();
        builder.append(hTag(aTag("Photo Ordering Tool", getAppUrl()), 3));
        builder.append(user.getEmail());
        builder.append(" / ");
        builder.append(password);
        return builder.toString();
    }

    @Override
    public void send(String toEmailAddress, String mailTitle, String mailBody) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);
        try {
            messageHelper.setFrom(FROM_EMAIL);
            messageHelper.setTo(toEmailAddress);
            messageHelper.setSubject(mailTitle);
            messageHelper.setText(body(mailBody), true);
        } catch (MessagingException ex) {
            throw new RuntimeException(ex);
        }
        mailSender.send(message);
    }

    private String getHostUrl() {
        String name = request.getServerName();
        int port = request.getServerPort();
        return name + (port == 80 ? "" : (":" + port));
    }

    private String hTag(String title, int num) {
        StringBuilder sb = new StringBuilder();
        sb.append("<h");
        sb.append(num);
        sb.append(">");
        sb.append(title);
        sb.append("</h");
        sb.append(num);
        sb.append(">");
        return sb.toString();
    }

    private String aTag(String title, String url) {
        StringBuilder sb = new StringBuilder();
        sb.append("<a href=\"");
        sb.append(url);
        sb.append("\">");
        sb.append(title);
        sb.append("</a>");
        return sb.toString();
    }

    private String body(String mailBody) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html><body>");
        sb.append(mailBody);
        sb.append("</body></html>");
        return sb.toString();
    }

    private String getAppUrl() {
        return "http://" + getHostUrl() + getBaseUrl();
    }

    private String getBaseUrl() {
        return request.getContextPath();
    }

}
