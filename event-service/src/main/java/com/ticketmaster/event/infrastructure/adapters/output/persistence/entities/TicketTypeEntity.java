package com.ticketmaster.event.infrastructure.adapters.output.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Table("ticket_types")
@NoArgsConstructor
@AllArgsConstructor
public class TicketTypeEntity {
    @Id
    private UUID ticketTypeId;

    @Column(value = "event_id")
    private UUID eventId;

    @Column(value = "type_name")
    private String typeName;

    private BigDecimal price;

    @Column(value = "total_quantity")
    private Integer totalQuantity;

    @Column(value = "available_quantity")
    private Integer availableQuantity;

    @CreatedDate
    @Column(value = "created_at")
    private LocalDateTime createdAt;
}
