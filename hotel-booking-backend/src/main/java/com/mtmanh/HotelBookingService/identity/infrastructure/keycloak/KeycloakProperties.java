package com.mtmanh.HotelBookingService.identity.infrastructure.keycloak;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "keycloak")
@Data
public class KeycloakProperties {
    private String authServerUrl;
    private String clientId;
    private String clientSecret;
    private String realm;
    private String adminUsername;
    private String adminPassword;

    public String getTokenUrl() {
        return String.format("%s/realms/%s/protocol/openid-connect/token", authServerUrl, realm);
    }
}
