package com.ticketmaster.event.application.ports.input;

import com.ticketmaster.event.application.ports.dto.EventDetailDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface GetEventUseCase {
    Mono<EventDetailDto> getEventDetail(UUID eventId);
    Flux<EventDetailDto> getAllEvents();
}
