package com.ticketmaster.event.infrastructure.adapters.input.rest.model.response;

import java.math.BigDecimal;
import java.util.UUID;

public record TicketTypeResponse(
        UUID ticketTypeId,
        String typeName,
        BigDecimal price,
        Integer availableQuantity
) {
}
