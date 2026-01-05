package com.ticketmaster.user.application.ports.input.dto;

import com.ticketmaster.user.domain.enums.ActivityType;
import lombok.Builder;

import java.util.UUID;

@Builder
public record RegisterLogActivityCommand(
        UUID userId,
        ActivityType activityType,
        String details
) {
}
