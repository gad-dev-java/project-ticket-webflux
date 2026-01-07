package com.ticketmaster.notification.application.ports.output;

import reactor.core.publisher.Mono;

public interface EmailPort {
    Mono<Void> sendEmail(String to, String subject, String content);
}
