package com.ticketmaster.user.infrastructure.adapters.output.persistence.repository;

import com.ticketmaster.user.infrastructure.adapters.output.persistence.entities.UserActivityEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface UserActivityRepository extends R2dbcRepository<UserActivityEntity, UUID> {
    Flux<UserActivityEntity> findByUserIdOrderByCreatedAtDesc(UUID userId);
}
