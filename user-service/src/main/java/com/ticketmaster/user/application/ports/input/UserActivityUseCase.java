package com.ticketmaster.user.application.ports.input;

import com.ticketmaster.user.domain.model.UserActivity;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface UserActivityUseCase {
    Flux<UserActivity> getUserActivity(UUID userId);
}
