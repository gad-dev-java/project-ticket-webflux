package com.ticketmaster.event.application.ports.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record TicketTypeDto(
        UUID ticketTypeId,
        String typeName,
        BigDecimal price,
        Integer availableQuantity
) {
}
