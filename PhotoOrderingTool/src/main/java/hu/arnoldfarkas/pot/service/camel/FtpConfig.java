package hu.arnoldfarkas.pot.service.camel;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FtpConfig {

    @Value("#{ftpProperties['ftp.type']}")
    private String type;
    @Value("#{ftpProperties['ftp.host']}")
    private String host;
    @Value("#{ftpProperties['ftp.port']}")
    private int port;
    @Value("#{ftpProperties['ftp.username']}")
    private String username;
    @Value("#{ftpProperties['ftp.password']}")
    private String password;
    @Value("#{ftpProperties['ftp.path']}")
    private String path;

    public String getHost() {
        return host;
    }

    public String getPath() {
        return path;
    }

    public int getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getType() {
        return type;
    }
}
