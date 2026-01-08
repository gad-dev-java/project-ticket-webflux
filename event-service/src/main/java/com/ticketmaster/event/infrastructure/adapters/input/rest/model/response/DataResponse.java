package com.ticketmaster.event.infrastructure.adapters.input.rest.model.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record DataResponse<T>(
        int status,
        String message,
        T data,
        LocalDateTime timestamp
) {
}
