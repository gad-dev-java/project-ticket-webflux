package com.ticketmaster.event.infrastructure.adapters.output.persistence.repository;

import com.ticketmaster.event.infrastructure.adapters.output.persistence.entities.TicketTypeEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TicketTypeRepository extends R2dbcRepository<TicketTypeEntity, UUID> {
}
