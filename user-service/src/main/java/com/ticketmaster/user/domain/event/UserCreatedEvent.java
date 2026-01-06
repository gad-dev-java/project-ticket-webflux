package com.ticketmaster.user.domain.event;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record UserCreatedEvent(
        UUID userId,
        String email,
        String firstName,
        String lastName,
        LocalDateTime occurredAt
) {
}
