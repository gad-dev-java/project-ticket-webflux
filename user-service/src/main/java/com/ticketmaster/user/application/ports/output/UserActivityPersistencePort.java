package com.ticketmaster.user.application.ports.output;

import com.ticketmaster.user.domain.model.UserActivity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserActivityPersistencePort {
    Mono<UserActivity> saveActivity(UserActivity activity);
    Flux<UserActivity> findActivitiesByUserId(UUID userId);
}
