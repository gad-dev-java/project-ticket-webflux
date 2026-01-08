package com.ticketmaster.event.application.ports.output;

import com.ticketmaster.event.domain.model.Event;
import reactor.core.publisher.Mono;

public interface EventPersistencePort {
    Mono<Event> saveEvent(Event event);
}
