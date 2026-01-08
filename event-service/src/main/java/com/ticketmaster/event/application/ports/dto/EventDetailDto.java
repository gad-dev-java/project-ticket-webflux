package com.ticketmaster.event.application.ports.dto;

import com.ticketmaster.event.domain.enums.EventStatus;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record EventDetailDto(
        UUID eventId,
        String name,
        String description,
        LocalDateTime eventDate,
        String venue,
        String city,
        EventStatus status,
        String imageUrl,
        List<TicketTypeDto> tickets
) {
}
