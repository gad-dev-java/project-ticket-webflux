package com.ticketmaster.event.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
public class TicketType {
    private UUID ticketTypeId;
    private UUID eventId;
    private String typeName;
    private BigDecimal price;
    private Integer totalQuantity;
    private Integer availableQuantity;
    private LocalDateTime createdAt;
}
