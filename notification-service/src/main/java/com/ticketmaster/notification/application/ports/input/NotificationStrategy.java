package com.ticketmaster.notification.application.ports.input;

import reactor.core.publisher.Mono;

public interface NotificationStrategy<T> {
    Class<T> getEventType();

    Mono<Void> handle(T event);
}
