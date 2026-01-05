package com.ticketmaster.user.infrastructure.adapters.output.persistence;

import com.ticketmaster.user.application.ports.output.PaymentMethodPersistencePort;
import com.ticketmaster.user.domain.model.PaymentMethod;
import com.ticketmaster.user.infrastructure.adapters.output.persistence.mapper.PaymentMethodMapper;
import com.ticketmaster.user.infrastructure.adapters.output.persistence.repository.PaymentMethodRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentMethodPersistenceAdapter implements PaymentMethodPersistencePort {
    private final PaymentMethodRepository paymentMethodRepository;
    private final PaymentMethodMapper paymentMethodMapper;

    @Override
    public Mono<PaymentMethod> save(PaymentMethod paymentMethod) {
        return paymentMethodRepository.save(paymentMethodMapper.toEntity(paymentMethod))
                .map(paymentMethodMapper::toDomain);
    }

    @Override
    public Flux<PaymentMethod> findByUserId(UUID userId) {
        return paymentMethodRepository.findByUserId(userId)
                .map(paymentMethodMapper::toDomain);
    }

    @Override
    public Mono<Void> deleteById(UUID paymentMethodId) {
        return paymentMethodRepository.deleteById(paymentMethodId);
    }

    @Override
    public Mono<Void> removeDefaultStatusFromAll(UUID userId) {
        return paymentMethodRepository.unmarkAllDefaults(userId);
    }
}
