package com.ticketmaster.user.application.ports.input.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record UpsertUserCommand(
        UUID userId,
        String username,
        String email,
        String firstName,
        String lastName,
        String phone
) {
}
