package com.mtmanh.HotelBookingService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("public/test")
    public String publicEndpoint() {
        return "public";
    }

    @GetMapping("/secured/test")
    public String securedEndpoint(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return "Secured - User: " + authentication.getName();
        }

        return "Secured - OAuth2 User: " + principal.getName() +
                ", Attributes: " + principal.getAttributes();
    }
}
