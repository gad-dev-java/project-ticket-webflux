package com.ticketmaster.user.infrastructure.adapters.output.persistence.repository;

import com.ticketmaster.user.infrastructure.adapters.output.persistence.entities.PaymentMethodEntity;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface PaymentMethodRepository extends R2dbcRepository<PaymentMethodEntity, UUID> {

    @Modifying
    @Query("""
             UPDATE payment_methods
             SET is_default = false
             WHERE user_id = :userId
            """)
    Mono<Void> unmarkAllDefaults(UUID userId);

    Flux<PaymentMethodEntity> findByUserId(UUID userId);
}
