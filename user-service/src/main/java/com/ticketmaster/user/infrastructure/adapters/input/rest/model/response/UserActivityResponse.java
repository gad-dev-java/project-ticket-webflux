package com.ticketmaster.user.infrastructure.adapters.input.rest.model.response;

import com.ticketmaster.user.domain.enums.ActivityType;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserActivityResponse(
         UUID activityId,
         ActivityType activityType,
         String details,
         LocalDateTime createdAt
) {
}
