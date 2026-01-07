package com.ticketmaster.notification.domain.event;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserCreatedEvent(
         UUID userId,
         String email,
         String firstName,
         String lastName,
         LocalDateTime occurredAt
) {
}
