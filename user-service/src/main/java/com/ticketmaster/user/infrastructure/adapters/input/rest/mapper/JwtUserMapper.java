package com.ticketmaster.user.infrastructure.adapters.input.rest.mapper;

import com.ticketmaster.user.application.ports.input.dto.UpsertUserCommand;
import com.ticketmaster.user.infrastructure.utils.jwt.JwtExtractor;
import org.mapstruct.Mapper;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.UUID;

@Mapper(componentModel = "spring", uses = JwtExtractor.class)
public interface JwtUserMapper {
    default UpsertUserCommand toCommand(Jwt jwt) {
        if (jwt == null) {
            return null;
        }

        return UpsertUserCommand.builder()
                .userId(extractUserId(jwt))
                .username(extractUsername(jwt))
                .email(extractEmail(jwt))
                .firstName(extractFirstName(jwt))
                .lastName(extractLastName(jwt))
                .phone(extractPhone(jwt))
                .build();
    }

    UUID extractUserId(Jwt jwt);
    String extractUsername(Jwt jwt);
    String extractEmail(Jwt jwt);
    String extractFirstName(Jwt jwt);
    String extractLastName(Jwt jwt);
    String extractPhone(Jwt jwt);
}
