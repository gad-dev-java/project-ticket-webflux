package com.ticketmaster.event.application.ports.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record CreateEventCommand(
        String name,
        String description,
        UUID categoryId,
        LocalDateTime eventDate,
        String venue,
        String address,
        String city,
        String country,
        String imageUrl,
        List<CreateTicketTypeCommand> ticketTypes
) {
}
