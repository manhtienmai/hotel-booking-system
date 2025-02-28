package com.mtmanh.HotelBookingService.identity.application.service;

import com.mtmanh.HotelBookingService.identity.infrastructure.keycloak.KeycloakProperties;
import com.mtmanh.HotelBookingService.identity.application.dto.response.TokenResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final KeycloakProperties keycloakProperties;

    public String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public TokenResponse refreshToken(String refreshToken) {
        // implementation for token refresh for using keycloak:v
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "refresh_token");
        map.add("refresh_token", refreshToken);
        map.add("client_id", keycloakProperties.getClientId());
        map.add("client_secret", keycloakProperties.getClientSecret());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<TokenResponse> response = restTemplate.postForEntity(
                keycloakProperties.getTokenUrl(),
                request,
                TokenResponse.class
        );

        return response.getBody();
    }
}
