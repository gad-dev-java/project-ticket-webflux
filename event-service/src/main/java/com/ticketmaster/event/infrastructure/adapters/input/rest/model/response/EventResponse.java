package com.ticketmaster.event.infrastructure.adapters.input.rest.model.response;

import com.ticketmaster.event.domain.enums.EventStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record EventResponse(
        UUID eventId,
        String name,
        String description,
        UUID categoryId,
        LocalDateTime eventDate,
        String venue,
        String address,
        String city,
        String country,
        EventStatus status,
        String imageUrl,
        LocalDateTime createdAt
) {
}
