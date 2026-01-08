package com.ticketmaster.event.domain.model;

import com.ticketmaster.event.domain.enums.EventStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@ToString
@Builder
@Getter
public class Event {
    private UUID eventId;
    private String name;
    private String description;
    private UUID categoryId;
    private LocalDateTime eventDate;
    private String venue;
    private String address;
    private String city;
    private String country;
    private EventStatus status;
    private String imageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
