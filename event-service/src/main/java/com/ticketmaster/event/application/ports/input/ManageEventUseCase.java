package com.ticketmaster.event.application.ports.input;

import com.ticketmaster.event.application.ports.dto.CreateEventCommand;
import com.ticketmaster.event.application.ports.dto.EventDto;
import reactor.core.publisher.Mono;

public interface ManageEventUseCase {
    Mono<EventDto> createEvent(CreateEventCommand command);
}
