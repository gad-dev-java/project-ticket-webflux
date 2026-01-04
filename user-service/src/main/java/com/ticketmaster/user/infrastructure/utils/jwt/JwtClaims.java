package com.ticketmaster.user.infrastructure.utils.jwt;

public final class JwtClaims {
    private JwtClaims() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static final String JWT_CLAIM_SUB = "sub";
    public static final String JWT_CLAIM_USERNAME = "preferred_username";
    public static final String JWT_CLAIM_EMAIL = "email";
    public static final String JWT_CLAIM_FIRSTNAME = "given_name";
    public static final String JWT_CLAIM_LASTNAME = "family_name";
    public static final String JWT_CLAIM_PHONE = "phone_number";
}
