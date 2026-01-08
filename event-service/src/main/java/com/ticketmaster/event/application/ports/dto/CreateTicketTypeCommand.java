package com.ticketmaster.event.application.ports.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CreateTicketTypeCommand(
        String typeName,
        BigDecimal price,
        Integer totalQuantity
) {
}
