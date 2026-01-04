package com.ticketmaster.user.infrastructure.utils.jwt;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.ticketmaster.user.infrastructure.utils.jwt.JwtClaims.*;

@Component
public class JwtExtractor {
    public UUID extractUserId(Jwt jwt) {
        return UUID.fromString(jwt.getClaimAsString(JWT_CLAIM_SUB));
    }

    public String extractUsername(Jwt jwt) {
        return jwt.getClaimAsString(JWT_CLAIM_USERNAME);
    }

    public String extractEmail(Jwt jwt) {
        return jwt.getClaimAsString(JWT_CLAIM_EMAIL);
    }

    public String extractFirstName(Jwt jwt) {
        String givenName = jwt.getClaimAsString(JWT_CLAIM_FIRSTNAME);
        return givenName != null ? givenName : "";
    }

    public String extractLastName(Jwt jwt) {
        String familyName = jwt.getClaimAsString(JWT_CLAIM_LASTNAME);
        return familyName != null ? familyName : "";
    }

    public String extractPhone(Jwt jwt) {
        return jwt.getClaimAsString(JWT_CLAIM_PHONE);
    }
}
