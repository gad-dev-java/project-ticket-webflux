package com.ticketmaster.event.infrastructure.adapters.input.rest.model.response;

import com.ticketmaster.event.domain.enums.EventStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record EventDetailResponse(
        UUID eventId,
        String name,
        String description,
        LocalDateTime eventDate,
        String venue,
        String city,
        EventStatus status,
        String imageUrl,
        List<TicketTypeResponse> tickets
) {
}
