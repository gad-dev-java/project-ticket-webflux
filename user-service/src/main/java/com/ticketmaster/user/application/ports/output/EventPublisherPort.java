package com.ticketmaster.user.application.ports.output;

import com.ticketmaster.user.domain.event.UserCreatedEvent;
import reactor.core.publisher.Mono;

public interface EventPublisherPort {
    Mono<Void> publishUserCreated(UserCreatedEvent event);
}
