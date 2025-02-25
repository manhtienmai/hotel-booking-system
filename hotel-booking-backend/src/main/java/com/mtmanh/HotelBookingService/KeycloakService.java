package com.mtmanh.HotelBookingService;

import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class KeycloakService {
    private final KeycloakProperties keycloakProperties;
    private Keycloak keycloakAdmin;

    @PostConstruct
    public void initKeycloak() {
        keycloakAdmin = KeycloakBuilder.builder()
                .serverUrl(keycloakProperties.getAuthServerUrl())
                .realm(keycloakProperties.getRealm())
                .clientId(keycloakProperties.getClientId())
                .clientSecret(keycloakProperties.getClientSecret())
                .grantType("client_credentials")
                .build();
    }

    public Response createUser(UserRepresentation user) {
        return keycloakAdmin
                .realm(keycloakProperties.getRealm())
                .users()
                .create(user);
    }
//
//    public TokenResponse getToken(String username, String password) {
//        RestTemplate restTemplate = new RestTemplate();
//
//        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
//        map.add("grant_type", "password");
//        map.add("username", username);
//        map.add("password", password);
//        map.add("client_id", keycloakProperties.getClientId());
//        map.add("client_secret", keycloakProperties.getClientSecret());
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
//
//        try {
//            ResponseEntity<TokenResponse> response = restTemplate.postForEntity(
//                    keycloakProperties.getTokenUrl(), request, TokenResponse.class);
//            return response.getBody();
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to get token: " + e.getMessage());
//        }
//    }
//
//    public void logout(String token) {
//        RestTemplate restTemplate = new RestTemplate();
//
//        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
//        map.add("client_id", keycloakProperties.getClientId());
//        map.add("client_secret", keycloakProperties.getClientSecret());
//        map.add("refresh_token", token);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
//
//        restTemplate.postForEntity(
//                keycloakProperties.getAuthServerUrl() + "/realms/" +
//                        keycloakProperties.getRealm() + "/protocol/openid-connect/logout",
//                request,
//                Void.class
//        );
//    }


    private HttpEntity<MultiValueMap<String, String>> createTokenRequest(Map<String, String> parameters) {
        MultiValueMap<String, String> formParams = new LinkedMultiValueMap<>();
        formParams.add("client_id", keycloakProperties.getClientId());
        formParams.add("client_secret", keycloakProperties.getClientSecret());

        // Add all additional parameters
        parameters.forEach(formParams::add);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        return new HttpEntity<>(formParams, headers);
    }

    public TokenResponse getToken(String username, String password) {
        Map<String, String> params = new HashMap<>();
        params.put("grant_type", "password");
        params.put("username", username);
        params.put("password", password);

        try {
            ResponseEntity<TokenResponse> response = new RestTemplate().postForEntity(
                    keycloakProperties.getTokenUrl(),
                    createTokenRequest(params),
                    TokenResponse.class
            );
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get token: " + e.getMessage());
        }
    }

    public void logout(String token) {
        Map<String, String> params = new HashMap<>();
        params.put("refresh_token", token);

        String logoutUrl = keycloakProperties.getAuthServerUrl() +
                "/realms/" + keycloakProperties.getRealm() +
                "/protocol/openid-connect/logout";

        new RestTemplate().postForEntity(
                logoutUrl,
                createTokenRequest(params),
                Void.class
        );
    }

    public UserRepresentation getUser(String userId) {
        return keycloakAdmin
                .realm(keycloakProperties.getRealm())
                .users()
                .get(userId)
                .toRepresentation();
    }

    public void updateUser(String userId, UserRepresentation userRepresentation) {
        keycloakAdmin
                .realm(keycloakProperties.getRealm())
                .users()
                .get(userId)
                .update(userRepresentation);
    }

    public void assignRole(String userId, String roleName) {
        RoleRepresentation role = keycloakAdmin
                .realm(keycloakProperties.getRealm())
                .roles()
                .get(roleName)
                .toRepresentation();

        if (role != null) {
            keycloakAdmin.realm(keycloakProperties.getRealm())
                    .users().get(userId)
                    .roles().realmLevel()
                    .add(Collections.singletonList(role));
        } else {
            throw new RuntimeException("Role not found: " + roleName);
        }

    }
}
