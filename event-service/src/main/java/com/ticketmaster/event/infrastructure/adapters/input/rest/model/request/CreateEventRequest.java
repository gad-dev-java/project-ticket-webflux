package com.ticketmaster.event.infrastructure.adapters.input.rest.model.request;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record CreateEventRequest(
        String name,
        String description,
        UUID categoryId,
        LocalDateTime eventDate,
        String venue,
        String address,
        String city,
        String country,
        String imageUrl,
        List<CreateTicketTypeRequest> ticketTypes
) {
}
