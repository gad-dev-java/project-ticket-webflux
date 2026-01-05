package com.ticketmaster.user.application.ports.input;

import com.ticketmaster.user.application.ports.input.dto.RegisterLogActivityCommand;
import com.ticketmaster.user.domain.model.UserActivity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserActivityUseCase {
    Mono<Void> logActivity(RegisterLogActivityCommand command);
    Flux<UserActivity> getUserActivity(UUID userId);
}
