package hu.arnoldfarkas.pot.service.impl;

import java.util.Properties;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

@Component
public class SmtpMailSender extends JavaMailSenderImpl implements MailSender, InitializingBean {

    @Value("#{smtp['host']}")
    private String host;
    @Value("#{smtp['port']}")
    private int port;
    @Value("#{smtp['username']}")
    private String username;
    @Value("#{smtp['password']}")
    private String password;
    @Value("#{smtp['auth']}")
    private String auth;
    @Value("#{smtp['starttlsenable']}")
    private String starttlsEnable;
    @Value("#{smtp['ssltrust']}")
    private String sslTrust;

    @Override
    public void afterPropertiesSet() throws Exception {
        setHost(host);
        setPort(port);
        setUsername(username);
        setPassword(password);
        setJavaMailProperties(getProperties());
    }

    private Properties getProperties() {
        Properties p = new Properties();
        p.setProperty("mail.smtp.auth", auth);
        p.setProperty("mail.smtp.starttls.enable", starttlsEnable);
        p.setProperty("mail.smtp.ssl.trust", sslTrust);
        return p;
    }

}
