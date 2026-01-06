package com.ticketmaster.user.infrastructure.adapters.input.rest.model.response;

import java.util.UUID;

public record UserSummaryResponse(
        UUID userId,
        String firstName,
        String lastName,
        String email
) {
}
