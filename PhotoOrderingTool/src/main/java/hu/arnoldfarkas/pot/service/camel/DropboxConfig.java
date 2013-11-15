package hu.arnoldfarkas.pot.service.camel;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DropboxConfig {

    @Value("#{dropboxProperties['userid']}")
    private String userId;
    @Value("#{dropboxProperties['accesstoken']}")
    private String accessToken;

    public String getUserId() {
        return userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

}
