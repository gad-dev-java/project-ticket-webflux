package com.ticketmaster.user.application.ports.input;

import com.ticketmaster.user.application.ports.input.dto.CreatePaymentMethodCommand;
import com.ticketmaster.user.application.ports.input.dto.PaymentMethodDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface PaymentMethodUseCase {
    Mono<PaymentMethodDto> addPaymentMethod(UUID userId, CreatePaymentMethodCommand command);
    Flux<PaymentMethodDto> getUserPaymentMethods(UUID userId);
    Mono<Void> removePaymentMethod(UUID userId, UUID paymentMethodId);
}
