package com.ticketmaster.user.application.ports.output;

import com.ticketmaster.user.domain.model.PaymentMethod;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface PaymentMethodPersistencePort {
    Mono<PaymentMethod> save(PaymentMethod paymentMethod);
    Flux<PaymentMethod> findByUserId(UUID userId);
    Mono<Void> deleteById(UUID paymentMethodId);
    Mono<Void> removeDefaultStatusFromAll(UUID userId);
}
