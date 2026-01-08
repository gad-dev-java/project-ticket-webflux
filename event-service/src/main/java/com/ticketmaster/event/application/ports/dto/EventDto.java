package com.ticketmaster.event.application.ports.dto;

import com.ticketmaster.event.domain.enums.EventStatus;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record EventDto(
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
