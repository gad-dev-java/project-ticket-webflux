package com.ticketmaster.user.infrastructure.adapters.input.rest.model.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponse(
        UUID userId,
        String username,
        String email,
        String firstName,
        String lastName,
        String phone,
        LocalDateTime createdAt
) {
}
