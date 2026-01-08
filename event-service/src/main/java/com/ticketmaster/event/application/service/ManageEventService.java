package com.ticketmaster.event.application.service;

import com.ticketmaster.event.application.ports.dto.CreateEventCommand;
import com.ticketmaster.event.application.ports.dto.EventDto;
import com.ticketmaster.event.application.ports.input.ManageEventUseCase;
import com.ticketmaster.event.application.ports.output.EventPersistencePort;
import com.ticketmaster.event.application.ports.output.TicketTypePersistencePort;
import com.ticketmaster.event.domain.enums.EventStatus;
import com.ticketmaster.event.domain.model.Event;
import com.ticketmaster.event.domain.model.TicketType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManageEventService implements ManageEventUseCase {
    private final EventPersistencePort eventPersistencePort;
    private final TicketTypePersistencePort ticketTypePersistencePort;

    @Override
    @Transactional
    public Mono<EventDto> createEvent(CreateEventCommand command) {
        Event newEvent = Event.builder()
                .name(command.name())
                .description(command.description())
                .categoryId(command.categoryId())
                .eventDate(command.eventDate())
                .venue(command.venue())
                .address(command.address())
                .city(command.city())
                .country(command.country())
                .status(EventStatus.ACTIVE)
                .imageUrl(command.imageUrl())
                .build();
        return eventPersistencePort.saveEvent(newEvent)
                .flatMap(savedEvent -> {
                    log.info("Saved event service: {}", savedEvent);
                    List<TicketType> tickets = command.ticketTypes().stream()
                            .map(ticket -> TicketType.builder()
                                    .eventId(savedEvent.getEventId())
                                    .typeName(ticket.typeName())
                                    .price(ticket.price())
                                    .totalQuantity(ticket.totalQuantity())
                                    .availableQuantity(ticket.totalQuantity())
                                    .createdAt(LocalDateTime.now())
                                    .build())
                            .toList();

                    return ticketTypePersistencePort.saveTicketTypes(tickets)
                            .doOnNext(savedTicketType -> log.info("Ticket Type saved successfully"))
                            .doOnError(error -> log.error("Error while saving ticket type: {}", error.getMessage()))
                            .collectList()
                            .thenReturn(savedEvent);
                })
                .doOnNext(savedEvent -> log.info("Event saved: {}", savedEvent))
                .doOnError(error -> log.error("Error while saving event: {}", error.getMessage()))
                .map(this::toDto);
    }

    private EventDto toDto(Event event) {
        return EventDto.builder()
                .eventId(event.getEventId())
                .name(event.getName())
                .description(event.getDescription())
                .categoryId(event.getCategoryId())
                .eventDate(event.getEventDate())
                .venue(event.getVenue())
                .address(event.getAddress())
                .city(event.getCity())
                .country(event.getCountry())
                .status(event.getStatus())
                .imageUrl(event.getImageUrl())
                .createdAt(event.getCreatedAt())
                .build();
    }
}
