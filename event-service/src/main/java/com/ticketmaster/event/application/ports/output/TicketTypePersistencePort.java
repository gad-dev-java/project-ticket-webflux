package com.ticketmaster.event.application.ports.output;

import com.ticketmaster.event.domain.model.TicketType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

public interface TicketTypePersistencePort {
    Flux<TicketType> saveTicketTypes(List<TicketType> ticketTypes);
    Flux<TicketType> findByEventId(UUID eventId);
}
