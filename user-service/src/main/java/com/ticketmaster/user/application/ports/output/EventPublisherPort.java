package com.ticketmaster.user.application.ports.output;

import com.ticketmaster.user.domain.event.DomainEvent;
import com.ticketmaster.user.domain.event.UserCreatedEvent;
import reactor.core.publisher.Mono;

public interface EventPublisherPort {
    Mono<Void> publish(DomainEvent event);
}
