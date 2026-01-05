package com.ticketmaster.user.infrastructure.adapters.input.rest.model.response;

import java.util.UUID;

public record GenreResponse(
        UUID genreId,
        String name
) {
}
