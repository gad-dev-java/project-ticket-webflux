package com.ticketmaster.event.application.ports.output;

import com.ticketmaster.event.domain.model.TicketType;
import reactor.core.publisher.Flux;

import java.util.List;

public interface TicketTypePersistencePort {
    Flux<TicketType> saveTicketTypes(List<TicketType> ticketTypes);
}
