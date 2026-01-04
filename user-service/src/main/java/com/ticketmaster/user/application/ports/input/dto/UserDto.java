package com.ticketmaster.user.application.ports.input.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record UserDto(
        UUID userId,
        String firstName,
        String lastName,
        LocalDateTime createdAt
) {
}
