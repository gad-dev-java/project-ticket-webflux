package com.ticketmaster.user.infrastructure.adapters.input.rest.mapper;

import com.ticketmaster.user.application.ports.input.dto.UpsertUserCommand;
import com.ticketmaster.user.infrastructure.utils.jwt.JwtExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class JwtUserMapper {
    private final JwtExtractor jwtExtractor;

    public UpsertUserCommand toCommand(Jwt jwt) {
        if (jwt == null) {
            return null;
        }

        return UpsertUserCommand.builder()
                .userId(jwtExtractor.extractUserId(jwt))
                .username(jwtExtractor.extractUsername(jwt))
                .email(jwtExtractor.extractEmail(jwt))
                .firstName(jwtExtractor.extractFirstName(jwt))
                .lastName(jwtExtractor.extractLastName(jwt))
                .phone(jwtExtractor.extractPhone(jwt))
                .build();
    }
}
