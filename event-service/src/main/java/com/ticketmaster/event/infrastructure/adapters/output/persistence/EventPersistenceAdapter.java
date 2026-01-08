package com.ticketmaster.event.infrastructure.adapters.output.persistence;

import com.ticketmaster.event.application.ports.output.EventPersistencePort;
import com.ticketmaster.event.domain.model.Event;
import com.ticketmaster.event.infrastructure.adapters.output.persistence.mapper.EventMapper;
import com.ticketmaster.event.infrastructure.adapters.output.persistence.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventPersistenceAdapter implements EventPersistencePort {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Override
    public Mono<Event> saveEvent(Event event) {
        log.info("Event to save: {}", event);
        return eventRepository.save(eventMapper.toEntity(event))
                .map(eventMapper::toDomain)
                .doOnNext(savedEvent -> log.info("Saved event: {}", savedEvent));
    }

    @Override
    public Mono<Event> findEventById(UUID eventId) {
        return eventRepository.findById(eventId)
                .map(eventMapper::toDomain);
    }

    @Override
    public Flux<Event> findAll() {
        return eventRepository.findAll()
                .map(eventMapper::toDomain);
    }

}
