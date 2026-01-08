package com.ticketmaster.event.infrastructure.adapters.input.rest.model.request;

import java.math.BigDecimal;

public record CreateTicketTypeRequest(
        String typeName,
        BigDecimal price,
        Integer totalQuantity
) {
}
