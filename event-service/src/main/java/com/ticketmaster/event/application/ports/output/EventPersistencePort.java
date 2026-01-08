package com.ticketmaster.event.application.ports.output;

import com.ticketmaster.event.domain.model.Event;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface EventPersistencePort {
    Mono<Event> saveEvent(Event event);
    Mono<Event> findEventById(UUID eventId);
    Flux<Event> findAll();
}
