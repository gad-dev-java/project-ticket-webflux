package com.ticketmaster.event.application.service;

import com.ticketmaster.event.application.ports.dto.EventDetailDto;
import com.ticketmaster.event.application.ports.dto.TicketTypeDto;
import com.ticketmaster.event.application.ports.input.GetEventUseCase;
import com.ticketmaster.event.application.ports.output.EventPersistencePort;
import com.ticketmaster.event.application.ports.output.TicketTypePersistencePort;
import com.ticketmaster.event.domain.exception.EventNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetEventService implements GetEventUseCase {
    private final EventPersistencePort eventPersistencePort;
    private final TicketTypePersistencePort ticketTypePersistencePort;

    @Override
    public Mono<EventDetailDto> getEventDetail(UUID eventId) {
        return eventPersistencePort.findEventById(eventId)
                .switchIfEmpty(Mono.error(new EventNotFoundException("Event with id " + eventId + " not found")))
                .flatMap(eventFound -> ticketTypePersistencePort.findByEventId(eventFound.getEventId())
                        .map(ticket -> TicketTypeDto.builder()
                                .ticketTypeId(ticket.getTicketTypeId())
                                .typeName(ticket.getTypeName())
                                .price(ticket.getPrice())
                                .availableQuantity(ticket.getAvailableQuantity())
                                .build())
                        .collectList()
                        .map(ticketList -> EventDetailDto.builder()
                                .eventId(eventFound.getEventId())
                                .name(eventFound.getName())
                                .description(eventFound.getDescription())
                                .eventDate(eventFound.getEventDate())
                                .venue(eventFound.getVenue())
                                .city(eventFound.getCity())
                                .status(eventFound.getStatus())
                                .imageUrl(eventFound.getImageUrl())
                                .tickets(ticketList)
                                .build()));
    }

    @Override
    public Flux<EventDetailDto> getAllEvents() {
        return eventPersistencePort.findAll()
                .flatMap(event -> getEventDetail(event.getEventId()));
    }
}
